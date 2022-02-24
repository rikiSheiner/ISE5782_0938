package geometries;

import primitives.*;

/**
 * Sphere class represents a geometrical object which is the set of points
 * that are all at the same distance from a given point in 3D space.
 * @author Rivka Sheiner
 */
public class Sphere implements Geometry{
    /**
     * the center point of the sphere
     */
    private Point center;
    /**
    *the distance between the center and the points on the sphere
     */
    private double radius;

    /**
     * Sphere parameters constructor
     * @param center the center point of the sphere
     * @param radius the distance between the center to the points on the sphere
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * This method returns the center of the sphere
     * @return Point - the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * This method returns the radius of the sphere
     * @return double - the radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
