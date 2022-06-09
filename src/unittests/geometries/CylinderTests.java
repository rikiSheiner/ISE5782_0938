package unittests.geometries;

import geometries.Cylinder;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for geometries.Cylinder class
 * @author Rivka Sheiner
 */
public class CylinderTests {
    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(0,0,5),new Vector(0,1,0)),2,10);
        List<Point> result;
        Point p1,p2;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the cylinder (0 points)
        assertNull(cylinder.findIntersections(new Ray(new Point(-100, 0, 0), new Vector(0, 0, 1))),
                "Ray's line out of cylinder");

        // TC02: Ray starts before and crosses the cylinder (2 points)
        p1 = new Point(-2,5,5); p2 = new Point(2,5,5);
        result = cylinder.findIntersections(new Ray(new Point(-10, 5, 5),
                new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getXyz().getD1() > result.get(1).getXyz().getD1())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses cylinder");

        // TC03: Ray starts after the cylinder (0 points)
        assertNull(cylinder.findIntersections(new Ray(new Point(10,0,0), new Vector(1,0,0))),
                "Ray starts after the cylinder");

    }

}
