package org.percepta.mgrankvi.client.geometry;

import java.io.Serializable;

/**
 * @author Mikael Grankvist - Vaadin }>
 */
public class Line implements Serializable {

    public Point start;
    public Point end;

    private Line(){}

    public Line(final Point start, final Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start.toString() + "-" + end.toString();
    }

    public String reverseString() {
        return end.toString() + "-" + start.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line) {
            return toString().equals(obj.toString()) && reverseString().equals(((Line) obj).reverseString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Add position as offset to line end points
     * @param position Offset position
     * @return Line with endpoints using given offset
     */
    public Line add(Point position) {
        return new Line(Calculations.combine(start, position), Calculations.combine(end, position));
    }
}
