package primitives;

import java.util.List;

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
        if(v.length() == 1)
            this.dir = v;
        else
            this.dir = v.normalize();
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
    public Point findClosestPoint(List<Point> points){
        if(points == null || points.size() == 0)
            return null;

        Point closestPoint = points.get(0);
        double minDistance = this.p0.distanceSquared(closestPoint);
        double tempDistance = 0;
        for(int i = 1; i < points.size(); i++){
            tempDistance = this.p0.distanceSquared(points.get(i));
            if(tempDistance < minDistance){
                closestPoint = points.get(i);
                minDistance = tempDistance;
            }
        }
        return closestPoint;
    }

}
