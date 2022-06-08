package geometries;

import primitives.Double3;
import primitives.Ray;
import primitives.Vector;

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

        double r = pos.getD2() + t * dir.getXyz().getD2();

        if ((r >= center.getXyz().getD2()) && (r <= center.getXyz().getD2() + height))
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        else
            return null;
    }

    /**
     * This method is used for finding the intersections points of ray with cylinder
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

        double r = pos.getD2() + t*dir.getXyz().getD2();

        if ((r >= center.getXyz().getD2()) && (r <= center.getXyz().getD2() + height))
            if(ray.getPoint(t).distance(ray.getP0()) <= maxDistance)
                return List.of(new GeoPoint(this, ray.getPoint(t)));

        return null;
    }
}
