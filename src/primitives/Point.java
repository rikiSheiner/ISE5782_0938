package primitives;

import static primitives.Util.isZero;

/**
 * Point class represents point with 3 coordinates
 */
public class Point {
    /**
     * the point with 3 coordinates
     */
    final Double3 xyz;
    /**
    * Point constructor based on 3 coordinates.
     * @param x, y, z - the 3 coordinates of the point 3D
    * */
    public Point(double x, double y, double z)
    {
        xyz = new Double3(x,y,z);
    }
    /**
     * Point constructor based on 3 coordinates.
     * @param double3 - the point
     * */
    Point(Double3 double3)
    {
        xyz = double3;
    }
    @Override
    public boolean equals(Object obj)
    {
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
    public String toString()
    {
        return xyz.toString();
    }
    public Point add(Vector v)
    {
        return new Point(xyz.add(v.xyz));
    }
    public Vector subtract(Point other)
    {
        return new Vector(xyz.subtract(other.xyz));
    }
    public double distanceSquared(Point other)
    {
        double distance = (this.xyz.d1-other.xyz.d1)*(this.xyz.d1-other.xyz.d1)
                + (this.xyz.d2-other.xyz.d2)*(this.xyz.d2-other.xyz.d2)
                +(this.xyz.d3-other.xyz.d3)*(this.xyz.d3-other.xyz.d3);
        return distance;
    }
    public double distance(Point other)
    {
        return Math.sqrt(distanceSquared(other));
    }

}
