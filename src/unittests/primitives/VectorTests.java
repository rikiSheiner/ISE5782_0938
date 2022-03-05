package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static primitives.Util.isZero;
import primitives.*;

/**
 * Unit tests for primitives.Vector class
 * @author Rivka Sheiner
 */
public class VectorTests {

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1,2,1);
        Vector v2 = new Vector(0,3,2);
        assertEquals(new Vector(1,5,3),v1.add(v2),"add() wrong result of sum of vectors");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1,2,1);
        Vector v2 = new Vector(0,3,2);
        assertEquals(new Vector(1,-1,-1),v1.subtract(v2),"subtract() wrong result of difference of vectors");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(2,3,1);
        double a = 3.1;
        assertEquals(new Vector(6.2,9.3,3.1),v1.scale(a),"scale() wrong result of scalar multiplication");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1,2,1);
        Vector v2 = new Vector(0,3,2);
        assertEquals(8,v1.dotProduct(v2),"dotProduct() wrong result of dot product");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals( v1.length() * v2.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue( isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
        assertTrue( isZero(vr.dotProduct(v2)),"crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(4,2,3);
        assertEquals(29,v1.lengthSquared(),"lengthSquared() wrong result of vector's length squared");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(4,2,3);
        assertEquals(Math.sqrt(29),v1.length(),"length() wrong result of vector's length");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(2,1,3);
        double l = Math.sqrt(14);
        assertEquals(new Vector(2d/l,1d/l,3d/l),v1.normalize(),"normalize() the normalized vector is wrong");
    }
}