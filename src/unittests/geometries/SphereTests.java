package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

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
}