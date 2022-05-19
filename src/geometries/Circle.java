package geometries;

import primitives.*;

import java.util.List;

/**
 * Circle class represents a circle in 3D Cartesian coordinate
 * @author Rivka Sheiner
 */
public class Circle extends Plane{

    /**
     *the maximal distance between the center and the points in the circle
     */
    private double radius;

    /**
     * the minimal distance between the center and the points in the circle
     */
    private double range;

    /**
     * Circle parameters constructor
     * @param q0 the point of reference of the plane
     * @param normal the normal vector to the plane
     * @param radius the maximal distance between the center and the points in the circle
     */
    public Circle(Point q0, Vector normal, double radius) throws IllegalArgumentException{
        super(q0, normal);

        if(radius <= 0)
            throw new IllegalArgumentException("Radius of the circle must be positive number");

        this.radius = radius;
        this.range = 0;
    }

    /**
     * Circle parameters constructor
     * @param q0 the point of reference of the plane
     * @param normal the normal vector to the plane
     * @param radius the maximal distance between the center and the points in the circle
     * @param range the minimal distance between the center and the points in the circle
     */
    public Circle(Point q0, Vector normal, double radius, double range) throws IllegalArgumentException{
        super(q0, normal);

        if(radius <= 0)
            throw new IllegalArgumentException("Radius of the circle must be positive number");
        if(range < 0 || range > radius)
            throw new IllegalArgumentException("Range of the circle must be not negative and smaller than the radius");

        this.radius = radius;
        this.range = range;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray);
        if(intersections == null)
            return null;

        Double3 p = intersections.get(0).point.getXyz();

        double distance = Math.pow(p.getD1()-this.q0.getXyz().getD1(),2)
        + Math.pow(p.getD2()-this.q0.getXyz().getD2(),2)
        + Math.pow(p.getD3()-this.q0.getXyz().getD3(),2);

        if(range * range <= distance && distance <= radius * radius)
            return List.of(new GeoPoint(this, intersections.get(0).point));

        return null;
    }

    /**
     * This method is used for finding the intersections points of ray with circle
     * @param ray- the ray imposed on the geometric shape
     * @param maxDistance - the max distance between the intersection point to the ray's head
     * @return List of GeoPoint
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){

        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray,maxDistance);
        if(intersections == null)
            return null;

        Double3 p = intersections.get(0).point.getXyz();

        double distance = Math.pow(p.getD1()-this.q0.getXyz().getD1(),2)
        + Math.pow(p.getD2()-this.q0.getXyz().getD2(),2)
        + Math.pow(p.getD3()-this.q0.getXyz().getD3(),2);

        if(range * range <= distance && distance <= radius * radius)
            return List.of(new GeoPoint(this, intersections.get(0).point));

        return null;
    }
}
