package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Unit tests for geometries.Tube class
 * @author Rivka Sheiner
 */
public class TubeTests {

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
}