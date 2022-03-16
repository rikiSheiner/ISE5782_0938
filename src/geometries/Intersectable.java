package geometries;

import primitives.*;
import java.util.List;

/**
 * Intersectable interface represents the behavior of all geometry objects
 *@author Rivka Sheiner
 */
public interface Intersectable {
    /**
     * The method finds the intersection points of the geometry with the ray
     * @param ray - the ray imposed on the geometric shape
     * @return List of Point - the intersection points with the ray
     */
    public List<Point> findIntersections(Ray ray);
}
