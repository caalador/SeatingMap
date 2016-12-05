package org.percepta.mgrankvi.client.geometry;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mikael Grankvist - Vaadin }>
 */
public class Calculations {
    private static Map<Double, Direction> angleDirections = new HashMap<Double, Direction>();

    // Find intersection of RAY & SEGMENT
    public static Intersect getIntersection(Line ray, Line segment) {
        // We need to find the closest intersection between the ray and all the line segments.
        // Any line can be written in parametric form as: Point + Direction * T

        Parametric r = new Parametric(ray);
        Parametric s = new Parametric(segment);

        if (areParallel(r, s)) return null;

//        This gives us 4 equations describing the x & y components of a ray & line segment:
//        Ray X = r_px+r_dx*T1
//        Ray Y = r_py+r_dy*T1
//        Segment X = s_px+s_dx*T2
//        Segment Y = s_py+s_dy*T2

//        If the ray & segment intersect, their X & Y components will be the same:
//        r_px+r_dx*T1 = s_px+s_dx*T2
//        r_py+r_dy*T1 = s_py+s_dy*T2

        // SOLVE FOR T1 & T2
        // r_px+r_dx*T1 = s_px+s_dx*T2 && r_py+r_dy*T1 = s_py+s_dy*T2
        // ==> T1 = (s_px+s_dx*T2-r_px)/r_dx = (s_py+s_dy*T2-r_py)/r_dy
        // ==> s_px*r_dy + s_dx*T2*r_dy - r_px*r_dy = s_py*r_dx + s_dy*T2*r_dx - r_py*r_dx
        // ==> T2 = (r_dx*(s_py-r_py) + r_dy*(r_px-s_px))/(s_dx*r_dy - s_dy*r_dx)
        double t2 = (r.dx * (s.py - r.py) + r.dy * (r.px - s.px)) / (s.dx * r.dy - s.dy * r.dx);
        double t1 = (s.px + s.dx * t2 - r.px) / r.dx;

        if (t1 < 0) return null;
        if (t2 < 0 || t2 > 1) return null;

        // moved calculation and creation of Point object to Intersect as most Intersects are discarded.
//        return new Intersect(new Point(r.px + r.dx * t1, r.py + r.dy * t1), t1);
        return new Intersect(r, t1);
    }

    private static boolean areParallel(Parametric line1, Parametric line2) {
        double r_mag = Math.sqrt(line1.dx * line1.dx + line1.dy * line1.dy);
        double s_mag = Math.sqrt(line2.dx * line2.dx + line2.dy * line2.dy);

        if (line1.dx / r_mag == line2.dx / s_mag && line1.dy / r_mag == line2.dy / s_mag) {
            return true;
        }
        return false;
    }

    public static LinkedList<Intersect> getSightPolygons(double x, double y, List<Line> lines) {
        List<Double> angles = getUniqueAngles(x, y, lines);

        LinkedList<Intersect> intersectList = new LinkedList<Intersect>();
        for (Double angle : angles) {
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
            Line ray = new Line(new Point(x, y), new Point(x + dx, y + dy));

            Intersect closestIntersectForRay = getClosestIntersectForRay(ray, lines);
            if (closestIntersectForRay == null)
                continue;
            closestIntersectForRay.setAngle(angle);
            intersectList.add(closestIntersectForRay);
        }

        Collections.sort(intersectList, new Comparator<Intersect>() {
            @Override
            public int compare(Intersect o1, Intersect o2) {
                return Double.compare(o1.getAngle(), o2.getAngle());
            }
        });
        return intersectList;
    }


    public static List<Double> getUniqueAngles(double x, double y, List<Line> lines) {
        List<Double> angles = new LinkedList<Double>();

        // Get all unique points
        Set<Point> points = new HashSet<Point>();
        for (Line line : lines) {
            points.add(line.start);
            points.add(line.end);
        }

        for (Point point : points) {
            Double angle = Math.atan2(point.getY() - y, point.getX() - x);
            angles.add(angle - 0.00001);
            angles.add(angle);
            angles.add(angle + 0.00001);
        }

        return angles;
    }

    /**
     * Find the closest intersecting segment for given ray.
     *
     * @param ray
     * @return
     */
    public static Intersect getClosestIntersectForRay(Line ray, List<Line> lines) {
        Intersect closest = null;
        // Closest intersection
        for (Line l : lines) {
            Intersect i = getIntersection(ray, l);
            if (i == null) continue;
            if (closest == null || (i.getT1() != null && i.getT1() < closest.getT1())) {
                closest = i;
                closest.line = l;
            }
        }
        return closest;
    }


    private static Direction getAngleDirections(double angle) {
        if(angleDirections.containsKey(angle)) {
            return angleDirections.get(angle);
        }
        Direction d = new Direction();
        d.dx = Math.cos(angle);
        d.dy = Math.sin(angle);

        angleDirections.put(angle, d);

        return d;
    }


    public static double distance(final Point p1, final Point p2) {
        final double x_diff = p2.getX() - p1.getX();
        final double y_diff = p2.getY() - p1.getY();

        return Math.sqrt(x_diff * x_diff + y_diff * y_diff);
    }


    /**
     * Do line segments (l1.start.getX(), l1.start.getY())--(l1.end.getX(),
     * l1.end.getY()) and (l2.start.getX(), l2.start.getY())--(l2.end.getX(),
     * l2.end.getY()) intersect?
     */
    public static boolean lineSegmentsIntersect(final Line l1, final Line l2) {
        final int d1 = computeDirection(l2, l1.start);
        final int d2 = computeDirection(l2, l1.end);
        final int d3 = computeDirection(l1, l2.start);
        final int d4 = computeDirection(l1, l2.end);
        return (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0)))
                || (d1 == 0 && isOnSegment(l2.start.getX(), l2.start.getY(), l2.end.getX(), l2.end.getY(), l1.start.getX(), l1.start.getY()))
                || (d2 == 0 && isOnSegment(l2.start.getX(), l2.start.getY(), l2.end.getX(), l2.end.getY(), l1.end.getX(), l1.end.getY()))
                || (d3 == 0 && isOnSegment(l1.start.getX(), l1.start.getY(), l1.end.getX(), l1.end.getY(), l2.start.getX(), l2.start.getY()))
                || (d4 == 0 && isOnSegment(l1.start.getX(), l1.start.getY(), l1.end.getX(), l1.end.getY(), l2.end.getX(), l2.end.getY()));
    }

    public static int computeDirection(final Line line, final Point point) {
        final int a = (int) ((point.getX() - line.start.getX()) * (line.end.getY() - line.start.getY()));
        final int b = (int) ((line.end.getX() - line.start.getX()) * (point.getY() - line.start.getY()));
        return a < b ? -1 : a > b ? 1 : 0;
    }

    public static boolean isOnSegment(final double xi, final double yi, final double xj, final double yj, final double xk, final double yk) {
        return (xi <= xk || xj <= xk) && (xk <= xi || xk <= xj) && (yi <= yk || yj <= yk) && (yk <= yi || yk <= yj);
    }
}
