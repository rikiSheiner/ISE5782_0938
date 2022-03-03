package geometries;

import primitives.*;

/**
 * Plane class represents two-dimensional infinite surface in 3D Cartesian coordinate
 * @author Rivka Sheiner
 */
public class Plane implements Geometry {
    /**
     * The reference point of the plane
     */
    private Point q0;
    /**
     * The normal vector to the plane
     */
    private Vector normal;

    /**
     * Plane parameters constructor
     * @param q0 the point of reference of the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        if(normal.length() == 1)
            this.normal = normal;
        else
            this.normal = normal.normalize();
    }

    /**
     * Plane constructor - gets three points on the plane and calculates the normal vector accordingly
     * @param p1 first point on the plane
     * @param p2 second point on the plane
     * @param p3 third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        this.normal = (v1.crossProduct(v2)).normalize();
    }

    /**
     *This method returns the reference point of the plane
     * @return Point - the reference point
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * This method returns the normal vector to the plane
     * @return Vector - the normal
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    @Override
    public Vector getNormal(Point p){
        return this.normal;
    }
}
