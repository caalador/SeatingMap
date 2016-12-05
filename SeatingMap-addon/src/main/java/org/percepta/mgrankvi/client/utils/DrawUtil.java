package org.percepta.mgrankvi.client.utils;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author Mikael Grankvist - Vaadin }>
 */
public class DrawUtil {

    public static final double FULL_CIRCLE = 2 * Math.PI;

    public static void drawGrid(Context2d g, int width, int height, String bg, int stepSize) {
        // Background
        g.setFillStyle(bg);
        g.fillRect(0, 0, width + 1, height + 1);

        // Draw the grid on the left side
        g.setStrokeStyle("hsla(210, 0%, 10%, 0.2)");
        g.setLineWidth(1.5);
        g.beginPath();
        for (double x = 0.5; x <= 0.6 + width; x += stepSize) {
            g.moveTo(x, 0);
            g.lineTo(x, height);
        }
        for (double x = 0.5; x <= 0.6 + height; x += stepSize) {
            g.moveTo(0, x);
            g.lineTo(width, x);
        }
        g.stroke();

    }

}
