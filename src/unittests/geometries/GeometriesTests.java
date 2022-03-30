package unittests.geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Geometries geometries = new Geometries(new Plane(new Point(1,0,0), new Vector(0,0,1))
                , new Triangle(new Point(0,0,1), new Point(5,0,1),new Point(0,0,5))
                , new Sphere(new Point(2,0,0), 1));
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects some shapes in the collection
        result = geometries.findIntersections(new Ray(new Point(2,-0.5,0), new Vector(0,0,1)));
        assertEquals(1, result.size(),"Wrong number of points");

        // =============== Boundary Values Tests ==================

        // TC11: Empty geometric shapes collection (0 points)
        Geometries emptyGeometries = new Geometries();
        assertNull(emptyGeometries.findIntersections(new Ray(new Point(1,1,1), new Vector(2,2,2))),
                "Empty collection of geometries - no intersections");

        // TC12: Ray does not intersect any shape in the collection (0 points)
        assertNull(geometries.findIntersections(new Ray(new Point(0,0,10), new Vector(0,0,2))),
                "Ray does not intersect any geometry");

        // TC13: Ray intersects only one shape in the collection (1 point)
        result = geometries.findIntersections(new Ray(new Point(2.1,0.1,0), new Vector(0,1,0)));
        assertEquals(1, result.size(),"Wrong number of points");

        // TC14: Ray intersects each shape in the collection (many points)
        Geometries geometries2 = new Geometries(new Plane(new Point(1,0,0), new Vector(0,1,0))
                , new Triangle(new Point(0.5,0,-1), new Point(2,0,1),new Point(4,0,-1))
                , new Sphere(new Point(2,0,0),0.5));
        result = geometries2.findIntersections(new Ray(new Point(2,-0.25,0), new Vector(0,1,0)));
        assertEquals(3, result.size(), "Ray crosses all geometries");
    }
}
