package unittests.primitives;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import primitives.*;
import primitives.Vector;

/**
 * Unit tests for primitives.Ray class
 * @author Rivka Sheiner
 */
public class RayTests {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List<primitives.Point>)}.
     */
    @Test
    void testFindClosestPoint(){
        Ray ray = new Ray(new Point(1,0,0), new Vector(1,0,0));
        List<Point> points = new LinkedList<Point>();

        // ============ Equivalence Partitions Tests ==============

        // TC01: The closest point is in the middle of the list
        points.add(new Point(-5,0,0));
        points.add(new Point(2,0,0));
        points.add(new Point(10,0,0));
        assertEquals(new Point(2,0,0), ray.findClosestPoint(points),
                "The closest point is in the middle of the list");

        // =============== Boundary Values Tests ==================

        // TC10: Empty list of points - no closest point
        points.clear();
        assertNull(ray.findClosestPoint(points), "Empty list of points - no closest point");

        // TC11: The closest point is the first point in the list
        points.add(new Point(2,0,0));
        points.add(new Point(-5,0,0));
        points.add(new Point(10,0,0));
        assertEquals(new Point(2,0,0), ray.findClosestPoint(points),
                "The closest point is the first point in the list");

        // TC12: The closest point is the last point in the list
        points.clear();
        points.add(new Point(-5,0,0));
        points.add(new Point(10,0,0));
        points.add(new Point(1.5,0,0));
        assertEquals(new Point(1.5,0,0), ray.findClosestPoint(points),
                "The closest point is the last point in the list");

    }
}
