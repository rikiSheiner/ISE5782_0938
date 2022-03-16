package geometries;

import primitives.*;

/**
 * Geometry interface represents the behavior of all geometry objects
 *@author Rivka Sheiner
 */
public interface Geometry extends Intersectable {
    /**
     * This method is used for finding the normal to specific point
     * on the geometry object.
     * @param p the point on the geometry object
     * @return Vector - the normal vector to the object in this point
     */
    public abstract Vector getNormal(Point p);
}
