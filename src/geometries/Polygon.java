package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane plane;
    /**
    * The number of vertices of the polygon
     */
    private int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
        size = vertices.length;
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersectionsPolygon = plane.findGeoIntersections(ray);
        if(intersectionsPolygon == null || intersectionsPolygon.size() == 0)
            return null;

        Point answer = intersectionsPolygon.get(0).point;

        //check if the intersection point is into the polygon
        Vector test_line = answer.subtract(this.vertices.get(0));
        Vector test_axis = this.plane.getNormal().crossProduct(test_line);

        boolean point_is_inside = false;

        Vector test_point = this.vertices.get(1).subtract(answer);
        boolean prev_point_ahead = test_line.dotProduct(test_point) > 0;
        boolean prev_point_above = test_axis.dotProduct(test_point) > 0;

        boolean this_point_ahead = false;
        boolean this_point_above = false;

        int index = 2;
        while(index < this.vertices.size())
        {
            test_point = this.vertices.get(index).subtract(answer);
            this_point_ahead = test_line.dotProduct(test_point) > 0;

            if (prev_point_ahead || this_point_ahead)
            {
                this_point_above = test_axis.dotProduct(test_point) > 0;

                if ((!prev_point_above && this_point_above) || (prev_point_above && !this_point_above))
                {
                    point_is_inside = !point_is_inside;
                }
            }

            prev_point_ahead = this_point_ahead;
            prev_point_above = this_point_above;
            index++;
        }

        if(point_is_inside)
            return intersectionsPolygon;
        return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersectionsPolygon = plane.findGeoIntersections(ray);
        if(intersectionsPolygon == null || intersectionsPolygon.size() == 0)
            return null;

        Point answer = intersectionsPolygon.get(0).point;

        //check if the intersection point is into the polygon
        Vector test_line = answer.subtract(this.vertices.get(0));
        Vector test_axis = this.plane.getNormal().crossProduct(test_line);

        boolean point_is_inside = false;

        Vector test_point = this.vertices.get(1).subtract(answer);
        boolean prev_point_ahead = test_line.dotProduct(test_point) > 0;
        boolean prev_point_above = test_axis.dotProduct(test_point) > 0;

        boolean this_point_ahead = false;
        boolean this_point_above = false;

        int index = 2;
        while(index < this.vertices.size())
        {
            test_point = this.vertices.get(index).subtract(answer);
            this_point_ahead = test_line.dotProduct(test_point) > 0;

            if (prev_point_ahead || this_point_ahead)
            {
                this_point_above = test_axis.dotProduct(test_point) > 0;

                if ((!prev_point_above && this_point_above) || (prev_point_above && !this_point_above))
                {
                    point_is_inside = !point_is_inside;
                }
            }

            prev_point_ahead = this_point_ahead;
            prev_point_above = this_point_above;
            index++;
        }

        if(point_is_inside && intersectionsPolygon.get(0).point.distance(ray.getP0()) <= maxDistance)
            return intersectionsPolygon;
        return null;
    }
}
