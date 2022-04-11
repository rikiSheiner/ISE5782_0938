package geometries;

import primitives.*;
import java.util.List;

/**
 * Intersectable interface represents the behavior of all geometry objects
 *@author Rivka Sheiner
 */
public abstract class Intersectable {
    /**
     * The method finds the intersection points of the geometry with the ray
     * @param ray - the ray imposed on the geometric shape
     * @return List of Point - the intersection points with the ray
     */
    public List<Point> findIntersections(Ray ray) {
        List<Intersectable.GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * The method finds the intersection geo points of the geometry with the ray
     * @param ray - the ray imposed on the geometric shape
     * @return List of GeoPoint - the intersection geo points with the ray
     */
     protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * The method finds the geometric intersection points of the geometry with the ray
     * @param ray - the ray imposed on the geometric shape
     * @return List of GeoPoint - the intersection geometric points with the ray
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * The method finds the intersection geo points of the geometry with the ray,
     * only the points that are not far away from the ray more than max distance.
     * @param ray - the ray imposed on the geometric shape
     * @param maxDistance - the max distance between the intersection point to the ray
     * @return List of GeoPoint - the intersection geo points with the ray
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * The method helps to find the intersection geo points of the geometry with the ray.
     * @param ray- the ray imposed on the geometric shape
     * @param maxDistance - the max distance between the intersection point to the ray's head
     * @return List of GeoPoint - the intersection geo points with the ray
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


    /**
     * GeoPoint class represents a point on specific geometry body
     */
    public static class GeoPoint{
        /**
         * The geometry body on which the point is
         */
        public Geometry geometry;
        /**
         * The point itself
         */
        public Point point;

        /**
         * GeoPoint constructor based on geometry body and the point on it
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (!(obj instanceof GeoPoint))
                return false;
            GeoPoint other = (GeoPoint) obj;
            return this.point.equals(other.point) && this.geometry.equals(other.geometry);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
