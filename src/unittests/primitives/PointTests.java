package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;

/**
 * Unit tests for primitives.Point class
 * @author Rivka Sheiner
 */
public class PointTests {

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Point p = new Point(1,2,3);
        Vector v = new Vector(2,3,1);
        assertEquals(new Point(3,5,4),p.add(v),"add() wrong result of adding");
    }

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,2,1);
        Point p2 = new Point(4,1,2);
        assertEquals(new Vector(3,-1,1),p2.subtract(p1),"subtract() wrong result of subtracting");
    }

    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,2,1);
        Point p2 = new Point(4,1,2);
        assertEquals(11,p1.distanceSquared(p2),"distanceSquared() wrong result of distance squared");
    }

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,2,1);
        Point p2 = new Point(4,1,2);
        assertEquals(Math.sqrt(11),p1.distance(p2),"distance() wrong result of distance");
    }
}