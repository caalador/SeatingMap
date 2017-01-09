package org.percepta.mgrankvi.client.geometry;

import java.io.Serializable;

/**
 * @author Mikael Grankvist - Vaadin }>
 */
public class Point implements Comparable<Point>, Serializable {

    private double x;
    private double y;

    public Point() {
    }

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this(point.getX(), point.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + " , " + y + "]";
    }

    public void move(final double x, final double y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public int compareTo(Point o) {
        int compare = Double.compare(getX(), o.getX());
        if (compare == 0) {
            return Double.compare(getY(), o.getY());
        }
        return compare;
    }

    public Point clonePoint() {
        return new Point(x, y);
    }
}
