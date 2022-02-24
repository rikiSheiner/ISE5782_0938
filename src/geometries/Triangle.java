package geometries;

import primitives.*;

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
}
