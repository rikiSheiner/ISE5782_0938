package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Cone class represents a cone with axis (0,0,1) and limited height
 * @author Rivka Sheiner
 */
public class Cone extends Geometry{

    /**
     * the height of the cone (the distance between the top of the cone to the base of the cone)
     */
    private double height;
    /**
     * the radius of the base circle of the cone
     */
    private double radius;
    /**
     * the position of the top of the cone
     */
    private Point center;


    /**
     * Cone parameters constructor
     * @param height - the height of the cone
     * @param radius - the radius of the base of the cone
     * @param center - the position of the top of the cone
     */
    public Cone(double height, double radius, Point center) {
        this.height = height;
        this.radius = radius;
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p) {
        double r = Math.sqrt((p.getXyz().getD1()-center.getXyz().getD1())*(p.getXyz().getD1()-center.getXyz().getD1()) + (p.getXyz().getD3()-center.getXyz().getD3())*(p.getXyz().getD3()-center.getXyz().getD3()));
        Vector n = new Vector (p.getXyz().getD1()-center.getXyz().getD1(), r*(radius/height), p.getXyz().getD3()-center.getXyz().getD3());
        n.normalize();
        return n;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        Double3 pos = ray.getP0().getXyz();
        Vector dir = ray.getDir();

        double A = pos.getD1() - center.getXyz().getD1();
        double B = pos.getD3() - center.getXyz().getD3();
        double D = this.height - pos.getD2() + center.getXyz().getD2();

        double tan = (radius / height) * (radius / height);

        double a = (dir.getXyz().getD1() * dir.getXyz().getD1()) + (dir.getXyz().getD3() * dir.getXyz().getD3()) - (tan*(dir.getXyz().getD2() * dir.getXyz().getD2()));
        double b = (2*A*dir.getXyz().getD1()) + (2*B*dir.getXyz().getD3()) + (2*tan*D*dir.getXyz().getD2());
        double c = (A*A) + (B*B) - (tan*(D*D));

        double delta = b*b - 4*(a*c);
        if(Math.abs(delta) < 0.001) return null;
        if(delta < 0.0) return null;

        double t1 = (-b - Math.sqrt(delta))/(2*a);
        double t2 = (-b + Math.sqrt(delta))/(2*a);
        double t;

        if (t1>t2) t = t2;
        else t = t1;

        double r = pos.getD2() + t*dir.getXyz().getD2();

        if ((r > center.getXyz().getD2()) && (r < center.getXyz().getD2() + height))
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        else
            return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        Double3 pos = ray.getP0().getXyz();
        Vector dir = ray.getDir();

        double A = pos.getD1() - center.getXyz().getD1();
        double B = pos.getD3() - center.getXyz().getD3();
        double D = this.height - pos.getD2() + center.getXyz().getD2();

        double tan = (radius / height) * (radius / height);

        double a = (dir.getXyz().getD1() * dir.getXyz().getD1()) + (dir.getXyz().getD3() * dir.getXyz().getD3()) - (tan*(dir.getXyz().getD2() * dir.getXyz().getD2()));
        double b = (2*A*dir.getXyz().getD1()) + (2*B*dir.getXyz().getD3()) + (2*tan*D*dir.getXyz().getD2());
        double c = (A*A) + (B*B) - (tan*(D*D));

        double delta = b*b - 4*(a*c);
        if(Math.abs(delta) < 0.001) return null;
        if(delta < 0.0) return null;

        double t1 = (-b - Math.sqrt(delta))/(2*a);
        double t2 = (-b + Math.sqrt(delta))/(2*a);
        double t;

        if (t1>t2) t = t2;
        else t = t1;

        double r = pos.getD2() + t*dir.getXyz().getD2();

        if ((r > center.getXyz().getD2()) && (r < center.getXyz().getD2() + height))
            if(ray.getPoint(t).distance(ray.getP0()) <= maxDistance)
                return List.of(new GeoPoint(this, ray.getPoint(t)));
        return null;

    }
}




