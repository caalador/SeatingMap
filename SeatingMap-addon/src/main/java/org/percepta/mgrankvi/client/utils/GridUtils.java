package org.percepta.mgrankvi.client.utils;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Window;
import org.percepta.mgrankvi.client.geometry.Point;

public class GridUtils {

    public static void paintGrid(final Context2d context, final Point offset, final double gridSize, final Point origo) {
        double x = offset.getX() - gridSize;
        double y = offset.getY() - gridSize;
        context.setStrokeStyle("gray");
        context.beginPath();

        do {
            do {
                context.strokeRect(x, y, 1, 1);
                x += gridSize;
            } while (x < Window.getClientWidth());
            y += gridSize;
            x = offset.getX() - gridSize;
        } while (y < Window.getClientHeight());
        context.closePath();
        context.stroke();

        // Mark grid 0,0
        context.setStrokeStyle("RED");
        context.beginPath();

        context.moveTo(origo.getX(), origo.getY() - 4);
        context.lineTo(origo.getX(), origo.getY() + 4);
        context.moveTo(origo.getX() - 4, origo.getY());
        context.lineTo(origo.getX() + 4, origo.getY());

        context.closePath();
        context.stroke();
    }

    public static void paintZoomInButton(final Context2d context, final Point position, final int size, final String color) {
        final double half = size / 2.0;
        context.setFillStyle(color);
        context.beginPath();

        context.fillRect(position.getX(), position.getY(), size, 2);
        context.fillRect(position.getX(), position.getY(), 2, size);
        context.fillRect(position.getX(), position.getY() + size - 2, size, 2);
        context.fillRect(position.getX() + size - 2, position.getY(), 2, size);

        context.fillRect(position.getX(), position.getY(), half - 2, half - 2);
        context.fillRect(position.getX() + half + 2, position.getY(), half - 2, half - 2);
        context.fillRect(position.getX(), position.getY() + half + 2, half - 2, half - 2);
        context.fillRect(position.getX() + half + 2, position.getY() + half + 2, half - 2, half - 2);

        context.closePath();
        context.fill();
    }

    public static void paintZoomOutButton(final Context2d context, final Point position, final int size, final String color) {
        final double half = size / 2.0;
        context.setFillStyle(color);
        context.beginPath();

        context.fillRect(position.getX(), position.getY(), size, 2);
        context.fillRect(position.getX(), position.getY(), 2, size);
        context.fillRect(position.getX(), position.getY() + size - 2, size, 2);
        context.fillRect(position.getX() + size - 2, position.getY(), 2, size);

        context.fillRect(position.getX(), position.getY(), size, half - 2);
        context.fillRect(position.getX(), position.getY() + half + 2, size, half - 2);

        context.closePath();
        context.fill();
    }

    public static void paintFloorUpButton(final Context2d context, final Point position, final int size, final String color) {
        final double half = size / 2.0;
        context.setFillStyle(color);
        context.setStrokeStyle(color);
        context.beginPath();

        context.strokeRect(position.getX(), position.getY(), size, size);

        context.closePath();
        context.stroke();

        context.beginPath();

        final double third = size / 2.5;
        context.moveTo(position.getX() + 3, position.getY() + third);
        context.lineTo(position.getX() + half, position.getY() + 3);
        context.lineTo(position.getX() + (size - 3), position.getY() + third);
        context.lineTo(position.getX() + (size - 9), position.getY() + third);
        context.lineTo(position.getX() + (size - 9), position.getY() + (size - 3));
        context.lineTo(position.getX() + 9, position.getY() + (size - 3));
        context.lineTo(position.getX() + 9, position.getY() + third);
        context.lineTo(position.getX() + 3, position.getY() + third);

        context.closePath();
        context.fill();
    }

    public static void paintFloorDownButton(final Context2d context, final Point position, final int size, final String color) {
        final double half = size / 2.0;
        context.setFillStyle(color);
        context.setStrokeStyle(color);
        context.beginPath();

        context.strokeRect(position.getX(), position.getY(), size, size);

        context.closePath();
        context.stroke();

        context.beginPath();

        final double third = size / 2.5;
        context.moveTo(position.getX() + 3, position.getY() + (size - third));
        context.lineTo(position.getX() + half, position.getY() + (size - 3));
        context.lineTo(position.getX() + (size - 3), position.getY() + (size - third));
        context.lineTo(position.getX() + (size - 9), position.getY() + (size - third));
        context.lineTo(position.getX() + (size - 9), position.getY() + 3);
        context.lineTo(position.getX() + 9, position.getY() + 3);
        context.lineTo(position.getX() + 9, position.getY() + (size - third));
        context.lineTo(position.getX() + 3, position.getY() + (size - third));
        context.closePath();
        context.fill();
    }

}
