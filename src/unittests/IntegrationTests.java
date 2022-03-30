package unittests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import primitives.*;
import renderer.*;
import geometries.*;

import java.util.List;

/**
 * IntegrationTests class is used for testing the intersections points
 * of geometries with rays from the camera view plane.
 */
public class IntegrationTests {

    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     * {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    void testRayIntersectionWithSphere(){

        int counter = 0; // the number of intersection points of the rays from camera's view plane with the sphere

        //TC01: Two intersection points
        Camera camera1 = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere1 = new Sphere(new Point(0,0,-3),1);
        counter = countRayIntersections(sphere1, camera1);
        assertEquals(2,counter,"Two intersection points with sphere");

        //TC02: 18 intersection points
        Camera camera2 = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere2 = new Sphere(new Point(0,0,-2.5),2.5);
        counter = countRayIntersections(sphere2, camera2);
        assertEquals(18,counter,"18 intersection points with sphere");

        //TC03: 10 intersection points
        Camera camera3 = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere3 = new Sphere(new Point(0,0,-2),2);
        counter = countRayIntersections(sphere3, camera3);
        assertEquals(10,counter,"10 intersection points with sphere");

        //TC04: 9 intersection points
        Camera camera4 = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere4 = new Sphere(new Point(0,0,-4),4);
        counter = countRayIntersections(sphere4, camera4);
        assertEquals(9,counter,"9 intersection points with sphere");

        //TC01: Sphere is behind the view plane - zero intersection points
        Camera camera5 = new Camera(new Point(0,0,2), new Vector(0,0,1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere5 = new Sphere(new Point(0,0,1),0.5);
        counter = countRayIntersections(sphere5, camera5);
        assertEquals(0,counter,"0 intersection points with sphere");
    }

    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     * {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    void testRayIntersectionWithPlane() {

        int counter = 0;// the number of intersection points of the rays from camera's view plane with the plane

        //TC01: The plane is opposite to the view plane of the camera (9 intersection points)
        Camera camera2 = new Camera(new Point(0,0,0), new Vector(0,0,1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Plane plane2 = new Plane(new Point(0,0,5), new Point(0,1,5), new Point(1,0,5));
        counter = countRayIntersections(plane2, camera2);
        assertEquals(9,counter,"9 intersection points with plane");

        //TC02: The plane intersect the view plane of the camera (6 intersection points)
        Camera camera3 = new Camera(new Point(0,0,0), new Vector(0,0,1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Plane plane3 = new Plane(new Point(0,1.5,1), new Point(1.5,1.5,1), new Point(0,0,2));
        counter = countRayIntersections(plane3, camera3);
        assertEquals(9,counter,"9 intersection points with plane");

        //TC03: The plane intersect the view plane of the camera (6 intersections points)
        Camera camera4 = new Camera(new Point(0,0,0), new Vector(0,0,1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Plane plane4 = new Plane(new Point(0,0,5), new Point(1.5,0.5,1), new Point(-1.5,0.5,1));
        counter = countRayIntersections(plane4, camera4);
        assertEquals(6,counter,"6 intersection points with plane");

    }

    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     * {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    void testRayIntersectionWithTriangle(){

        int counter = 0;// the number of intersection points of the rays from camera's view plane with the triangle

        //TC01: One intersection point
        Camera camera1 = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Triangle triangle1 = new Triangle(new Point(0,1,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        counter = countRayIntersections(triangle1, camera1);
        assertEquals(1,counter,"1 intersection points with triangle");

        //TC02: Two intersection points
        Camera camera2 = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Triangle triangle2 = new Triangle(new Point(0,20,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        counter = countRayIntersections(triangle2, camera2);
        assertEquals(2,counter,"2 intersection points with triangle");

    }

    /**
     * This method is used for counting the number of intersection points of a camera rays' with geometry
     * @param geo the geometry shape which the rays may intersect with
     * @param camera the camera where the rays come from
     * @return the number of intersection points
     */
    private int countRayIntersections(Geometry geo, Camera camera){
        int counter = 0;
        Ray ray = null;
        List<Point> points = null;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                ray = camera.constructRay(3,3,j,i);
                points = geo.findIntersections(ray);
                if(points != null)
                    counter += points.size();
            }
        }
        return counter;
    }
}
