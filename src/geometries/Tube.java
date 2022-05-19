package geometries;

import primitives.*;

import java.util.List;

/**
 * Tube class represents a geometry object which is the set of points
 *that are all at the same distance from the axis
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
        Double3 pos = ray.getP0().getXyz();
        Vector dir = ray.getDir();
        Vector center = this.axisRay.getDir();

        double a = (dir.getXyz().getD1() * dir.getXyz().getD1()) + (dir.getXyz().getD3() * dir.getXyz().getD3());
        double b = 2*(dir.getXyz().getD1()*(pos.getD1()-center.getXyz().getD1()) + dir.getXyz().getD3()*(pos.getD3()-center.getXyz().getD3()));
        double c = (pos.getD1() - center.getXyz().getD1()) * (pos.getD1() - center.getXyz().getD1()) +
                (pos.getD3() - center.getXyz().getD3()) * (pos.getD3() - center.getXyz().getD3()) - (radius*radius);

        double delta = b*b - 4*(a*c);
        if(Math.abs(delta) < 0.001) return null;
        if(delta < 0.0) return null;

        double t1 = (-b - Math.sqrt(delta))/(2*a);
        double t2 = (-b + Math.sqrt(delta))/(2*a);
        double t;

        if (t1>t2) t = t2;
        else t = t1;

        return List.of(new GeoPoint(this, ray.getPoint(t)));

    }

    /**
     * This method is used for finding the intersections points of ray with tube
     * @param ray- the ray imposed on the geometric shape
     * @param maxDistance - the max distance between the intersection point to the ray
     * @return List of GeoPoint
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        Double3 pos = ray.getP0().getXyz();
        Vector dir = ray.getDir();
        Vector center = this.axisRay.getDir();

        double a = (dir.getXyz().getD1() * dir.getXyz().getD1()) + (dir.getXyz().getD3() * dir.getXyz().getD3());
        double b = 2*(dir.getXyz().getD1()*(pos.getD1()-center.getXyz().getD1()) + dir.getXyz().getD3()*(pos.getD3()-center.getXyz().getD3()));
        double c = (pos.getD1() - center.getXyz().getD1()) * (pos.getD1() - center.getXyz().getD1()) +
                (pos.getD3() - center.getXyz().getD3()) * (pos.getD3() - center.getXyz().getD3()) - (radius*radius);

        double delta = b*b - 4*(a*c);
        if(Math.abs(delta) < 0.001) return null;
        if(delta < 0.0) return null;

        double t1 = (-b - Math.sqrt(delta))/(2*a);
        double t2 = (-b + Math.sqrt(delta))/(2*a);
        double t;

        if (t1>t2) t = t2;
        else t = t1;

        if(ray.getPoint(t).distance(ray.getP0()) <= maxDistance)
            return List.of(new GeoPoint(this, ray.getPoint(t)));

        return null;
    }
}
