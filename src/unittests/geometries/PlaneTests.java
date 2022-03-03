package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Unit tests for geometries.Plane class
 * @author Rivka Sheiner
 */
public class PlaneTests {

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
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane plane1 = new Plane(new Point(1,2,-5), new Point(3,1,-5), new Point(2,2,-6));
        Point point1 = new Point(-8,2,4);
        double x = Math.sqrt(6);
        assertEquals(new Vector(1d/x,2d/x,1d/x),plane1.getNormal(point1),"getNormal() wrong result of normal vector to plane");
    }

}