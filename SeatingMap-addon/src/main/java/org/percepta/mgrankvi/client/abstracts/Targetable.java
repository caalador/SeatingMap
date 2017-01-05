package org.percepta.mgrankvi.client.abstracts;

import org.percepta.mgrankvi.client.geometry.Calculations;
import org.percepta.mgrankvi.client.geometry.Line;
import org.percepta.mgrankvi.client.geometry.Point;
import org.percepta.mgrankvi.client.helpers.Clicked;

/**
 * Created by Mikael on 04/01/17.
 */
public abstract class Targetable extends Item {

    public String id;

    public boolean pointInObject(final double x, final double y) {
        if (x < (position.getX() + extents.getMinX()) || x > (position.getX() + extents.getMaxX()) || y < (position.getY() + extents.getMinY()) || y > (position.getY() + extents.getMaxY())) {
            return false;
        }

        final Point target = new Point(x, y);
        // First point clearly outside shape.
        final Line targetLine = new Line(new Point(position.getX() + extents.getMinX() - 50, position.getY() + extents.getMinY() - 50), target);
        int intercepts = 0;
        for (Line line : getLines()) {
            if ((position.getX() + line.start.getX() > x && position.getX() + line.end.getX() > x) || (position.getY() + line.start.getY() > y && position.getY() + line.end.getY() > y)) {
                continue;
            }
            if (Calculations.lineSegmentsIntersect(line.add(position), targetLine)) {
                intercepts++;
            }
        }
        return intercepts % 2 == 1;
    }

    public abstract Clicked click(double downX, double downY);
}
