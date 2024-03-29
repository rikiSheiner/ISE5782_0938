package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * Ray class represents ש line drawn from a certain point to infinity in only one direction
 * @author Rivka Sheiner
 */
public class Ray {
    /**
     * the start point of the ray
     */
    private final Point p0;
    /**
     * the direction vector of the line
     */
    private final Vector dir;

    /**
     * Ray constructor based on vector direction and start point
     * @param p - the start point of the ray
     * @param v - the direction vector of the ray
     */
    public Ray(Point p, Vector v) {
        this.p0 = p;
        this.dir = v.normalize();
    }

    /**
     * Ray constructor based on vector direction and start point that
     * move by delta * n
     * @param p0 - the start point of the calculating
     * @param dir - the direction vector of the ray
     * @param n - the normal vector to the geometry in p0
     * @param delta - the scalar for scaling n
     */
    public Ray(Point p0, Vector dir, Vector n, double delta){
        this.dir = dir.normalize();

        double nv = n.dotProduct(dir);
        this.p0 = p0.add(n.scale(nv > 0 ? delta : -delta));
    }

    /**
     * This method returns the start point of the ray
     * @return Point - start point
     */
    public Point getP0() {
        return p0;
    }

    /**
     * This method returns the direction vector of the ray
     * @return Vector - the direction vector of the ray
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ray))
            return false;
        Ray other = (Ray) obj;
        return this.dir.equals(other.dir) && this.p0.equals(other.p0);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point getPoint(double t){
        return this.p0.add(this.dir.scale(t));
    }

    /**
     * Function findClosestPoint finds the point with the minimal
     * distance from the head of the ray and returns it
     * @param points - List of Point
     * @return Point - the closest point to the head of the ray
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * Function findGeoClosestPoint finds the GeoPoint with the minimal
     * @param intersections - List of Point
     * @return GeoPoint - the closest GeoPoint to the head of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        if(intersections == null || intersections.size() == 0)
            return null;

        GeoPoint closestPoint = intersections.get(0);
        double minDistanceSquared = this.p0.distanceSquared(closestPoint.point);
        double tempDistanceSquared = 0;
        for(int i = 1; i < intersections.size(); i++){
            tempDistanceSquared = this.p0.distanceSquared(intersections.get(i).point);
            if(tempDistanceSquared < minDistanceSquared){
                closestPoint = intersections.get(i);
                minDistanceSquared = tempDistanceSquared;
            }
        }
        return closestPoint;
    }


}
