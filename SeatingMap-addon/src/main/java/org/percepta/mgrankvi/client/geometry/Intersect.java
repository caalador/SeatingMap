package org.percepta.mgrankvi.client.geometry;

/**
 * @author Mikael Grankvist - Vaadin }>
 */
public class Intersect {

    Point intersectionPoint;
    Parametric rayParameters;
    Double t1, angle;
    public Line line;

    private Intersect() {
    }

    public Intersect(Parametric rayParameters, Double t1) {
        this.rayParameters = rayParameters;
        this.t1 = t1;
    }

    public Intersect(Point intersectionPoint, Double t1) {
        this.intersectionPoint = intersectionPoint;
        this.t1 = t1;
    }

    public Point getIntersectionPoint() {
        if (intersectionPoint == null) {
            intersectionPoint = new Point(rayParameters.px + rayParameters.dx * t1,
                    rayParameters.py + rayParameters.dy * t1);
        }
        return intersectionPoint;
    }

    public Double getT1() {
        return t1;
    }

    public Double getAngle() {
        if (angle == null) angle = 0.0;
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public double getX() {
        return getIntersectionPoint().getX();
    }

    public double getY() {
        return getIntersectionPoint().getY();
    }

    @Override
    public String toString() {
        return intersectionPoint.toString() + " t1:" + t1 + " angle:" + angle;
    }
}
