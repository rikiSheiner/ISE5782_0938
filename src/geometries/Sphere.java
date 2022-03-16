package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

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
        return (p.subtract(this.center)).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        Vector u = this.center.subtract(ray.getP0());
        double tm = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared()-tm*tm);
        double th = Math.sqrt(this.radius*this.radius - d*d);
        double t1 = tm + th;
        double t2 = tm - th;
        boolean t1gz = t1 > 0, t2gz = t2 > 0;

        if(!t1gz && !t2gz)
            return null;

        List<Point> points = new LinkedList<>();
        if(t1gz)
            if(ray.getDir().dotProduct(ray.getPoint(t1).subtract(this.center)) != 0)
                points.add(ray.getPoint(t1));
        if(t2gz)
            if(ray.getDir().dotProduct(ray.getPoint(t2).subtract(this.center)) != 0)
                points.add(ray.getPoint(t2));

        if(points.size() > 0 )
            return points;
        return null;


    }
}
