package primitives;

/**
 * Ray class represents ש line drawn from a certain point to infinity in only one direction
 * @author Rivka Sheiner
 */
public class Ray {
    /**
     * the start point of the ray
     */
    private Point p0;
    /**
     * the direction vector of the line
     */
    private Vector dir;

    /**
     * Ray constructor based on vector direction and start point
     * @param p - the start point of the ray
     * @param v - the direction vector of the ray
     */
    public Ray(Point p, Vector v) {
        this.p0 = p;
        if(v.length() == 1)
            dir = v;
        else
            dir = v.normalize();
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

}
