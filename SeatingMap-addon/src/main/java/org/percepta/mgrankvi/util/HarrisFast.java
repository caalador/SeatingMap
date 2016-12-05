package org.percepta.mgrankvi.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Harris Corner Detector
 * <p/>
 * k = det(A) - k * trace(A)^2
 * <p/>
 * Where A is the second-moment matrix
 * <p/>
 * | Lx²(x+dx,y+dy)    Lx.Ly(x+dx,y+dy) |
 * A =  Sum  |                                    | * Gaussian(dx,dy)
 * dx,dy | Lx.Ly(x+dx,y+dy)  Ly²(x+dx,y+dy)   |
 * <p/>
 * and k = a/(1+a)^2,
 * <p/>
 * where "a" is the mininmum ratio between the two eigenvalues
 * for a point to be considered as a corner.
 *
 * @author Xavier Philippeau
 */
public class HarrisFast {

    Logger log = Logger.getLogger("HarrisFast");

    // corner class
    public class Corner {
        public int x, y; // corner position
        public float h; // harris measure

        public Corner(int x, int y, float h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }
    }

    // corners list
    public List<Corner> corners = new ArrayList<Corner>();

    // image
    private int[][] image;
    int width, height;

    // precomputed values of the derivatives
    private float[][] Lx2, Ly2, Lxy;

    /**
     * Constructor
     */
    public HarrisFast(int[][] image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    /**
     * Gaussian function
     */
    private double gaussian(double x, double y, double sigma) {
        double sigma2 = sigma * sigma;
        double t = (x * x + y * y) / (2 * sigma2);
        double u = 1.0 / (2 * Math.PI * sigma2);
        double e = u * Math.exp(-t);
        return e;
    }

    /**
     * Sobel gradient 3x3
     */
    private float[] sobel(int x, int y) {
        int v00 = 0, v01 = 0, v02 = 0, v10 = 0, v12 = 0, v20 = 0, v21 = 0, v22 = 0;

        int x0 = x - 1, x1 = x, x2 = x + 1;
        int y0 = y - 1, y1 = y, y2 = y + 1;
        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x2 >= width) x2 = width - 1;
        if (y2 >= height) y2 = height - 1;

        v00 = image[x0][y0];
        v10 = image[x1][y0];
        v20 = image[x2][y0];
        v01 = image[x0][y1];
        v21 = image[x2][y1];
        v02 = image[x0][y2];
        v12 = image[x1][y2];
        v22 = image[x2][y2];

        float sx = ((v20 + 2 * v21 + v22) - (v00 + 2 * v01 + v02)) / (4 * 255f);
        float sy = ((v02 + 2 * v12 + v22) - (v00 + 2 * v10 + v20)) / (4 * 255f);
        return new float[]{sx, sy};
    }


    /**
     * Compute the 3 arrays Ix, Iy and Ixy
     */
    private void computeDerivatives(double sigma) {
        this.Lx2 = new float[width][height];
        this.Ly2 = new float[width][height];
        this.Lxy = new float[width][height];

        // gradient values: Gx,Gy
        float[][][] grad = new float[width][height][];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                grad[x][y] = sobel(x, y);

        // precompute the coefficients of the gaussian filter
        int radius = (int) (2 * sigma);
        int window = 1 + 2 * radius;
        float[][] gaussian = new float[window][window];
        for (int j = -radius; j <= radius; j++)
            for (int i = -radius; i <= radius; i++)
                gaussian[i + radius][j + radius] = (float) gaussian(i, j, sigma);

        // Convolve gradient with gaussian filter:
        //
        // Ix2 = (F) * (Gx^2)
        // Iy2 = (F) * (Gy^2)
        // Ixy = (F) * (Gx.Gy)
        //
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        int xk = x + dx;
                        int yk = y + dy;
                        if (xk < 0 || xk >= width) continue;
                        if (yk < 0 || yk >= height) continue;

                        // gaussian weight
                        double gw = gaussian[dx + radius][dy + radius];

                        // convolution
                        this.Lx2[x][y] += gw * grad[xk][yk][0] * grad[xk][yk][0];
                        this.Ly2[x][y] += gw * grad[xk][yk][1] * grad[xk][yk][1];
                        this.Lxy[x][y] += gw * grad[xk][yk][0] * grad[xk][yk][1];
                    }
                }
            }
        }
    }

    /**
     * compute harris measure for a pixel
     */
    private float harrisMeasure(int x, int y, float k) {
        // matrix elements (normalized)
        float m00 = this.Lx2[x][y];
        float m01 = this.Lxy[x][y];
        float m10 = this.Lxy[x][y];
        float m11 = this.Ly2[x][y];

        // Harris corner measure = det(M)-k.trace(M)^2
        return m00 * m11 - m01 * m10 - k * (m00 + m11) * (m00 + m11);
    }

    /**
     * return true if the measure at pixel (x,y) is a local spatial Maxima
     */
    private boolean isSpatialMaxima(float[][] hmap, int x, int y) {
        int n = 8;
        int[] dx = new int[]{-1, 0, 1, 1, 1, 0, -1, -1};
        int[] dy = new int[]{-1, -1, -1, 0, 1, 1, 1, 0};
        double w = hmap[x][y];
        for (int i = 0; i < n; i++) {
            double wk = hmap[x + dx[i]][y + dy[i]];
            if (wk >= w) return false;
        }
        return true;
    }

    /**
     * compute the Harris measure for each pixel of the image
     */
    private float[][] computeHarrisMap(double k) {

        // Harris measure map
        float[][] harrismap = new float[width][height];

        // for each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // compute the harris measure
                double h = harrisMeasure(x, y, (float) k);
                if (h <= 0) continue;
                // log scale
                h = 255 * Math.log(1 + h) / Math.log(1 + 255);
                // store
                harrismap[x][y] = (float) h;
            }
        }

        return harrismap;
    }

    /**
     * Perfom the Harris Corner Detection
     *
     * @param sigma       gaussian filter parameter
     * @param k           parameter of the harris measure formula
     * @param minDistance minimum distance between corners
     * @return the orginal image marked with cross sign at each corner
     */
    public int[][] filter(double sigma, double k, int minDistance) {

        // precompute derivatives
        computeDerivatives(sigma);

        // Harris measure map
        float[][] harrismap = computeHarrisMap(k);

        // for each pixel in the harrismap
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                // thresholding : harris measure > epsilon
                float h = harrismap[x][y];
                if (h <= 1E-3) continue;
                // keep only a local maxima
                if (!isSpatialMaxima(harrismap, x, y)) continue;
                // add the corner to the list
                corners.add(new Corner(x, y, h));
            }
        }

        log.log(Level.FINE, corners.size() + " potential corners found.");

        // remove corners to close to each other (keep the highest measure)
        Iterator<Corner> iter = corners.iterator();
        while (iter.hasNext()) {
            Corner p = iter.next();
            for (Corner n : corners) {
                if (n == p) continue;
                int dist = (int) Math.sqrt((p.x - n.x) * (p.x - n.x) + (p.y - n.y) * (p.y - n.y));
                if (dist > minDistance) continue;
                if (n.h < p.h) continue;
                iter.remove();
                break;
            }
        }

        // output
        int[][] output = new int[width][height];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                output[x][y] = (int) (image[x][y] * 0.75); // original image (darker)

        // for each corner
        for (Corner p : corners) {
            // add the cross sign over the image
            for (int dt = -3; dt <= 3; dt++) {
                if (p.x + dt >= 0 && p.x + dt < width) output[p.x + dt][p.y] = 255;
                if (p.y + dt >= 0 && p.y + dt < height) output[p.x][p.y + dt] = 255;
            }
            log.log(Level.FINE, "corner found at: " + p.x + "," + p.y + " (" + p.h + ")");
        }
        log.log(Level.FINE, corners.size() + " corners found.");

        return output;
    }


}