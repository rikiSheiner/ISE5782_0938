package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Square class represents a square in 3D Cartesian coordinate
 * @author Rivka Sheiner
 */
public class Square extends Polygon{

    /**
     * Square parameters constructor
     * @param vertices - list of the vertices of the square
     * @throws IllegalArgumentException
     */
    public Square(Point ... vertices) throws  IllegalArgumentException {
        super(vertices);

        if(vertices.length != 4)
            throw new IllegalArgumentException("Square has to be consist of 4 vertices");
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> points = new LinkedList<>();

        List<GeoPoint> temp = new Triangle(vertices.get(0), vertices.get(2),vertices.get(3)).findGeoIntersectionsHelper(ray);
        if(temp != null){
            for (GeoPoint p: temp) {
                points.add(new GeoPoint(this, p.point));
            }
        }
        temp = new Triangle(vertices.get(0), vertices.get(1),vertices.get(2)).findGeoIntersectionsHelper(ray);
        if(temp != null){
            for (GeoPoint p: temp) {
                points.add(new GeoPoint(this, p.point));
            }
        }

        temp = new Triangle(vertices.get(3), vertices.get(0),vertices.get(1)).findGeoIntersectionsHelper(ray);
        if(temp != null){
            for (GeoPoint p: temp) {
                points.add(new GeoPoint(this, p.point));
            }
        }

        temp = new Triangle(vertices.get(1), vertices.get(2),vertices.get(3)).findGeoIntersectionsHelper(ray);
        if(temp != null){
            for (GeoPoint p: temp) {
                points.add(new GeoPoint(this, p.point));
            }
        }

        if(points.size() > 0) {
            return points;
        }

        return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> points = new LinkedList<>();

        List<GeoPoint> temp = new Triangle(vertices.get(0), vertices.get(2),vertices.get(3)).findGeoIntersectionsHelper(ray, maxDistance);
        if(temp != null){
            for (GeoPoint p: temp) {
                points.add(new GeoPoint(this, p.point));
            }
        }
        temp = new Triangle(vertices.get(0), vertices.get(1),vertices.get(2)).findGeoIntersectionsHelper(ray, maxDistance);
        if(temp != null){
            for (GeoPoint p: temp) {
                points.add(new GeoPoint(this, p.point));
            }
        }

        temp = new Triangle(vertices.get(3), vertices.get(0),vertices.get(1)).findGeoIntersectionsHelper(ray, maxDistance);
        if(temp != null){
            for (GeoPoint p: temp) {
                points.add(new GeoPoint(this, p.point));
            }
        }

        temp = new Triangle(vertices.get(1), vertices.get(2),vertices.get(3)).findGeoIntersectionsHelper(ray, maxDistance);
        if(temp != null){
            for (GeoPoint p: temp) {
                points.add(new GeoPoint(this, p.point));
            }
        }

        if(points.size() > 0) {
            return points;
        }

        return null;
    }

}
