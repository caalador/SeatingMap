package org.percepta.mgrankvi.client.geometry;

public class Parametric {
    // Points
    public double px;
    public double py;

    // Directions
    public double dx;
    public double dy;

    public Parametric(Line line) {
        px = line.start.getX();
        py = line.start.getY();
        dx = line.end.getX() - line.start.getX();
        dy = line.end.getY() - line.start.getY();
        if (dx == 0) dx = 0.0000000001;
    }

    @Override
    public String toString() {
        return "[" + px + "," + py + "]_[" + dx + "," + dy + "]";
    }
}