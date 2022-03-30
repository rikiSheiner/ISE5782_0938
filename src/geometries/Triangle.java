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

        Point p = points.get(0); // the intersection point with the plane of the triangle

        try{
            //edge 0
            Vector c = new Vector(1,1,1);
            Vector edge0 = this.vertices.get(1).subtract(this.vertices.get(0));
            Vector vp0 = p.subtract(this.vertices.get(0));
            c = edge0.crossProduct(vp0);
            if(this.plane.getNormal().dotProduct(c) < 0) return null; //p is on the right side

            //edge 1
            Vector edge1 = this.vertices.get(2).subtract(this.vertices.get(1));
            Vector vp1 = p.subtract(this.vertices.get(1));
            c = edge1.crossProduct(vp1);
            if(this.plane.getNormal().dotProduct(c) < 0) return null; //p is on the right side

            //edge 2
            Vector edge2 = this.vertices.get(0).subtract(this.vertices.get(2));
            Vector vp2 = p.subtract(this.vertices.get(2));
            c = edge2.crossProduct(vp2);
            if(this.plane.getNormal().dotProduct(c) < 0) return null; //p is on the right side

            return points;
        }
        catch(IllegalArgumentException ie){
            return null;
        }

    }
}
