package unittests.geometries;

import primitives.*;
import geometries.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for geometries.Triangle class
 * @author Rivka Sheiner
 */
public class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(3,0,0), new Point(0,0,-1), new Point(0,0,3));
        List<Point> result;
        Point p1;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray is inside triangle

        // TC02: Ray is outside against edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0,1,0), new Vector(0,0,1))),
                "Ray is outside against edge");
        // TC03: Ray is outside against vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(5,0,0), new Vector(1,0,0))),
                "Ray is outside against vertex");

        // =============== Boundary Values Tests ==================

        // **** Group: The ray begins before the plane of the triangle
        // TC11: Ray begins on edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0,0,1), new Vector(0,1,0))),
                "Ray begins on edge");
        // TC12: Ray begins in vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0,0,3), new Vector(0,0,1))),
                "Ray begins on vertex");
        // TC13: Ray begins on edge's continuation (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0,0,4), new Vector(0,0,1))),
                "Ray begins on edge's continuation");

    }
}
