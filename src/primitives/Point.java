package primitives;

import static primitives.Util.isZero;

/**
 * Point class represents point with 3 coordinates
 * @author Rivka Sheiner
 */
public class Point {
    /**
     * the point with 3 coordinates
     */
    final Double3 xyz;
    /**
     * Zero point(0,0,0)
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
    * Point constructor based on 3 coordinates.
     * @param x, y, z - the 3 coordinates of the point 3D
    * */
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    /**
     * Point constructor based on point with 3 coordinates.
     * @param double3 - the point
     * */
    Point(Double3 double3) {
        xyz = double3;
    }

    /**
     * This method returns the 3D point
     * @return Double3 - point 3D
     */
    public Double3 getXyz() {
        return xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point))
            return false;
        Point other = (Point) obj;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {

        return xyz.toString();
    }

    /**
     *This method is used to add vector to point.
     * Adding by coordinates.
     * @param v - the vector to adding
     * @return Point - the result of the adding
     */
    public Point add(Vector v) {
        return new Point(xyz.add(v.xyz));
    }

    /**
     *This method is used to subtract point from point.
     * Subtracting by coordinates.
     * @param other - the point for subtracting
     * @return Vector - the vector from the point to the other point
     */
    public Vector subtract(Point other) {
        return new Vector(xyz.subtract(other.xyz));
    }

    /**
     *This method is used to calculate the distance squared between two points.
     * Calculating by distance formula: d^2 = (x2-x1)^2+(y2-y1)^2+(z2-z1)^2
     * @param other - the point to calculate the distance between the current point to this
     * @return double - the distance squared between this point to another point
     */
    public double distanceSquared(Point other) {
        double distance = (this.xyz.d1-other.xyz.d1)*(this.xyz.d1-other.xyz.d1)
                + (this.xyz.d2-other.xyz.d2)*(this.xyz.d2-other.xyz.d2)
                +(this.xyz.d3-other.xyz.d3)*(this.xyz.d3-other.xyz.d3);
        return distance;
    }

    /**
     *This method is used to calculate the distance between two points.
     *Calculating by another method which calculates the distance squared
     * @param other - the point to calculate the distance between the current point to this
     * @return double - the distance between this point to another point
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

}
