package geometries;

import primitives.*;

/**
 * Tube class represents a geometry object which is the set of points
 *that are all at the same distance from the axis
 * @author Rivka Sheiner
 */
public class Tube implements Geometry{
    /**
     * the straight line in the center of the tube
     */
    protected Ray axisRay;
    /**
    *the distance between the axis ray to each point on the tube
     */
    protected double radius;

    /**
     * Tube parameters constructor
     * @param axisRay the straight line in the center of the tube
     * @param radius the distance between the axis ray to each point on the tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * This method returns the axis ray of the tube
     * @return Ray - the axis ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * This method returns the radius of the tube
     * @return double - radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "axisRay=" + axisRay.toString() +
                ", radius=" + radius;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
