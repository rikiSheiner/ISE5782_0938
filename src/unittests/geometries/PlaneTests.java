package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Unit tests for geometries.Plane class
 * @author Rivka Sheiner
 */
public class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#Plane(Point,Point,Point)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct points on plane
        try {
            new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }


        // =============== Boundary Values Tests ==================

        // TC11: First point = second point
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 0, 1)),
                "Constructed a plane with two same points");

        //TC12: Points on the same straight line
        assertThrows(IllegalArgumentException.class,
                ()-> new Plane(new Point(1,2,0),new Point(2,4,0),new Point(3,6,0)),
                "Constructed a plane with points on same straight line");

    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane plane1 = new Plane(new Point(1,2,-5), new Point(3,1,-5), new Point(2,2,-6));
        Point point1 = new Point(-8,2,4);
        double x = Math.sqrt(6);
        assertEquals(new Vector(1d/x,2d/x,1d/x),plane1.getNormal(point1),"getNormal() wrong result of normal vector to plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point(1,0,0), new Vector(0,1,0));
        List<Point> result;
        Point p1;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane (1 point)
        p1 = new Point(2,0,1);
        result = plane.findIntersections(new Ray(new Point(1,-1,0), new Vector(1,1,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses the plane");

        // TC02: Ray does not intersect the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1,1,0), new Vector(1,1,1))),
                "Ray does not cross the plane");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC11: The ray included in the plane


        // TC12: The ray not included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1,1,0), new Vector(1,0,0))),
                "Ray is parallel to the plane, but not included in the plane");

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray starts before the plane (1 point)
        p1 = new Point(1,0,0);
        result = plane.findIntersections(new Ray(new Point(1,-1,0), new Vector(0,1,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray is orthogonal to the plane and starts before plane");

        // TC14: Ray starts in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(2,0,3), new Vector(0,1,0))),
                "Ray is orthogonal to the plane and starts in the plane");

        // TC15: Ray starts after the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1,1,1), new Vector(0,1,0))),
                "Ray starts after the plane");

        // **** Group: Special cases
        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(3,0,0), new Vector(2,1,2))),
                "Ray begins at the plane");

        // TC17: Ray begins in the same point which appears as reference point in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1,0,0),new Vector(1,3,1))),
                "Ray begins at the ref point of the plane");

    }

    /**
     * Test method for {@link geometries.Plane#findGeoIntersections(primitives.Ray,double)}.
     */
    @Test
    void testFindIntersectionsWithDistance() {
        Plane plane = new Plane(new Point(1,0,0), new Vector(0,1,0));
        List<Intersectable.GeoPoint> result;
        Point p1;

        // ============ Equivalence Partitions Tests ==============

        //TC01: The point of intersection is more than max distance (0 points)
        result = plane.findGeoIntersections(new Ray(new Point(1,-1,0), new Vector(1,1,1)),0.1);
        assertNull( result, "Ray does not intersect the plane in the limited distance");

        //TC02: The point of intersection is less than max distance (1 point)
        p1 = new Point(2,0,1);
        result = plane.findGeoIntersections(new Ray(new Point(1,-1,0), new Vector(1,1,1)),5);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p1, result.get(0).point, "Ray intersects the plane in the limited distance");

    }
}