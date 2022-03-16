package geometries;

import primitives.*;

import java.util.List;

/**
 * Triangle class represents a polygon with 3 vertices
 * @author Rivka Sheiner
 */
public class Triangle extends Polygon {
    /**
     * Triangle parameters constructor
     * @param p1 first vertex of the triangle
     * @param p2 second vertex of the triangle
     * @param p3 third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3){
        super(p1,p2,p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        List<Point> points = this.plane.findIntersections(ray);
        if(points == null)
            return null;

        Point p0 = ray.getP0();
        Vector v1 = this.vertices.get(0).subtract(p0);
        Vector v2 = this.vertices.get(1).subtract(p0);
        Vector v3 = this.vertices.get(2).subtract(p0);
        Vector n1 = (v1.crossProduct(v2)).normalize();
        Vector n2 = (v2.crossProduct(v3)).normalize();
        Vector n3 = (v3.crossProduct(v1)).normalize();

        double v1n1 = v1.dotProduct(n1);
        double v1n2 = v1.dotProduct(n2);
        double v1n3 = v1.dotProduct(n3);
        double v2n1 = v2.dotProduct(n1);
        double v2n2 = v2.dotProduct(n2);
        double v2n3 = v2.dotProduct(n3);
        double v3n1 = v3.dotProduct(n1);
        double v3n2 = v3.dotProduct(n2);
        double v3n3 = v3.dotProduct(n3);

        if(v1n1 < 0.0 && v1n2 < 0.0 && v1n3 < 0.0 && v2n1 < 0.0 && v2n2 < 0.0
                && v2n3 < 0.0 && v3n1 < 0.0 && v3n2 < 0.0 && v3n3 < 0.0)
            return points;

        if(v1n1 > 0.0 && v1n2 > 0.0 && v1n3 > 0.0 && v2n1 > 0.0 && v2n2 > 0.0
                && v2n3 > 0.0 && v3n1 > 0.0 && v3n2 > 0.0 && v3n3 > 0.0)
            return points;

        return null;
    }
}
