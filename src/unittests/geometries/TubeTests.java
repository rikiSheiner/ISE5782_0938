package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Unit tests for geometries.Tube class
 * @author Rivka Sheiner
 */
public class TubeTests {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
       //============ Equivalence Partitions Tests ==============
        Tube t = new Tube(new Ray(new Point(1,0,1),new Vector(0,2,0)),2);
        Point p = new Point(3,4,1);
        assertEquals(t.getAxisRay().getDir().dotProduct(t.getNormal(p)),0d,"getNormal() wrong normal vector to tube");

        // =============== Boundary Values Tests ==================
        Point p2 = new Point(1,1,2);
        assertEquals(t.getAxisRay().getDir().dotProduct(t.getNormal(p2)),0d,"getNormal() wrong normal vector to tube in the height of the ray's head");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Tube tube = new Tube(new Ray(new Point(0,0,5),new Vector(0,1,0)),2);
        List<Point> result;
        Point p1,p2;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-100, 0, 0), new Vector(0, 0, 1))),
                "Ray's line out of tube");

        // TC02: Ray starts before and crosses the tube (2 points)
        p1 = new Point(-2,0,5); p2 = new Point(2,0,5);
        result = tube.findIntersections(new Ray(new Point(-100, 0, 5),
                new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getXyz().getD1() > result.get(1).getXyz().getD1())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses tube");

        // TC03: Ray starts after the tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(10,0,0), new Vector(1,0,0))),
                "Ray starts after the tube");
    }

}