package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Tube class represents a geometry object which is the set of points
 *  that are all at the same distance from the axis
 * @author Rivka Sheiner
 */
public class Tube extends Geometry{
    /**
     * the straight line in the center of the tube
     */
    protected Ray axisRay;
    /**
    * the distance between the axis ray to each point on the tube
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
        double t = (this.axisRay.getDir()).dotProduct(p.subtract(this.axisRay.getP0()));
        Point o = this.axisRay.getP0().add(this.axisRay.getDir().scale(t));
        return p.subtract(o).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        double delta, a, b, c;
        Ray new_ray;
        Vector c_to_o;
        Vector rot;

        rot = this.axisRay.getDir().normalize();
        new_ray = new Ray(ray.getP0(), ray.getDir().crossProduct(rot));
        c_to_o = ray.getP0().subtract(this.axisRay.getP0());
        a = new_ray.getDir().dotProduct(new_ray.getDir());
        b = 2 * (new_ray.getDir().dotProduct(c_to_o.crossProduct(rot)));
        c = (c_to_o.crossProduct(rot)).dotProduct(c_to_o.crossProduct(rot))-Math.pow(this.radius, 2);
        delta = Math.pow(b, 2) - 4 * c * a;

        if (delta < 0)
            return null;

        double t1 = (-b - Math.sqrt(delta)) / (2 * a);
        double t2 = (-b + Math.sqrt(delta)) / (2 * a);

        if (t2 < 0) return null;

        return List.of(new GeoPoint(this, ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
    }

    /**
     * This method is used for finding the intersections points of ray with tube
     * @param ray- the ray imposed on the geometric shape
     * @param maxDistance - the max distance between the intersection point to the ray
     * @return List of GeoPoint
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        double delta, a, b, c;
        Ray new_ray;
        Vector c_to_o;
        Vector rot;

        rot = this.axisRay.getDir().normalize();
        new_ray = new Ray(ray.getP0(), ray.getDir().crossProduct(rot));
        c_to_o = ray.getP0().subtract(this.axisRay.getP0());
        a = new_ray.getDir().dotProduct(new_ray.getDir());
        b = 2 * (new_ray.getDir().dotProduct(c_to_o.crossProduct(rot)));
        c = (c_to_o.crossProduct(rot)).dotProduct(c_to_o.crossProduct(rot)) - Math.pow(this.radius, 2);
        delta = Math.pow(b, 2) - 4 * c * a;

        if (delta < 0)
            return null;

        double t1 = (-b - Math.sqrt(delta)) / (2 * a);
        double t2 = (-b + Math.sqrt(delta)) / (2 * a);

        if (t2 < 0) return null;

        List<GeoPoint> intersections = new LinkedList<>();
        Point point = ray.getPoint(t1);
        if (point.distance(ray.getP0()) <= maxDistance)
            intersections.add(new GeoPoint(this, point));
        point = ray.getPoint(t2);
        if (point.distance(ray.getP0()) <= maxDistance)
            intersections.add(new GeoPoint(this, point));

        if (intersections.size() > 0)
            return intersections;

        return null;
    }

}
