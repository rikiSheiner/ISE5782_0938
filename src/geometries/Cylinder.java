package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Cylinder class represents a tube with limited height
 * @author Rivka Sheiner
 */
public class Cylinder extends Tube {
    /**
    The height of the cylinder
     */
    private double height;

    /**
     * Cylinder parameters constructor
     * @param axisRay the center ray of the cylinder
     * @param radius the radius of the base of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * This method returns the height of the cylinder
     * @return double - the height
     */
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return super.toString()+" ,height=" + height;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new LinkedList<>();
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

        double max = Math.sqrt(Math.pow(this.height / 2, 2) + Math.pow(this.radius, 2)); //pythagoras theorem

        Point point = ray.getPoint(t1);
        Vector len = point.subtract(this.axisRay.getP0());

        if (len.length() <= max)
            intersections.add(new GeoPoint(this, point));

        point = ray.getPoint(t2);
        len = point.subtract(this.axisRay.getP0());
        if (len.length() <= max)
            intersections.add(new GeoPoint(this, point));

        if(intersections.size() > 0)
            return intersections;

        return null;
    }

    /**
     * This method is used for finding the intersections points of ray with cylinder
     * @param ray- the ray imposed on the geometric shape
     * @param maxDistance - the max distance between the intersection point to the ray
     * @return List of GeoPoint
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = new LinkedList<>();
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

        double max = Math.sqrt(Math.pow(this.height / 2, 2) + Math.pow(this.radius, 2)); //pythagoras theorem

        Point point = ray.getPoint(t1);
        Vector len = point.subtract(this.axisRay.getP0());

        if (len.length() <= max)
            if(point.distance(ray.getP0()) <= maxDistance)
                intersections.add(new GeoPoint(this, point));

        point = ray.getPoint(t2);
        len = point.subtract(this.axisRay.getP0());
        if (len.length() <= max)
            if(point.distance(ray.getP0()) <= maxDistance)
                intersections.add(new GeoPoint(this, point));

        if(intersections.size() > 0)
            return intersections;

        return null;
    }

}
