package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Unit tests for geometries.Sphere class
 * @author Rivka Sheiner
 */
public class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s1 = new Sphere(new Point(1,2,1),3);
        Point p1 = new Point(1,2,4);
        assertEquals(new Vector(0,0,1),s1.getNormal(p1),"getNormal() wrong result of normal vector to sphere");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
        List<Point> result;
        Point p1,p2;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getXyz().getD1() > result.get(1).getXyz().getD1())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        p1 = new Point(2,0,0);
        result = sphere.findIntersections(new Ray(new Point(1.1,0,0),
                new Vector(2,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(5,0,0), new Vector(2,0,0))),
                "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        p1 = new Point(0,0,0);
        result = sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(-5,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(2,0,0))),
                "Ray's line starts at sphere and goes out");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point(2,0,0);
        p2 = new Point(0,0,0);
        result = sphere.findIntersections(new Ray(new Point(-1,0,0), new Vector(5,0,0)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(p1,p2),result,"Ray starts before the sphere and goes through the center");

        // TC14: Ray starts at sphere and goes inside (1 points)
        p1 = new Point(2,0,0);
        result = sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(5,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1),result,"Ray starts at sphere and goes inside through the center");

        // TC15: Ray starts inside (1 points)
        p1 = new Point(2,0,0);
        result = sphere.findIntersections(new Ray(new Point(0.5,0,0), new Vector(5,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1),result,"Ray starts inside the sphere and goes through the center");

        // TC16: Ray starts at the center (1 points)
        p1 = new Point(2,0,0);
        result = sphere.findIntersections(new Ray(new Point(1.00001,0,0), new Vector(5,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1),result,"Ray starts at the center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(1,0,0))),
                "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0))),
                "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,0), new Vector(1,0,0))),
                "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,1,0), new Vector(1,0,0))),
                "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2,1,0), new Vector(1,0,0))),
                "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,2,0), new Vector(0,0,1))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

    }

    /**
     * Test method for {@link geometries.Sphere#findGeoIntersections(primitives.Ray,double)}.
     */
    @Test
    void testFindIntersectionsWithDistance() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
        List<Intersectable.GeoPoint> result;
        Point p1;

        // ============ Equivalence Partitions Tests ==============

        //TC01: The point of intersection is more than max distance (0 points)
        result = sphere.findGeoIntersections(new Ray(new Point(1.1,0,0),
                new Vector(2,0,0)),0.1);
        assertNull(result,"Ray does not intersect the sphere in the limited distance");

        //TC02: The point of intersection is less than max distance (1 point)
        p1 = new Point(2,0,0);
        result = sphere.findGeoIntersections(new Ray(new Point(1.1,0,0),
                new Vector(2,0,0)),5);
        assertEquals(p1, result.get(0).point,"Ray intersects the sphere in the limited distance");

    }
}