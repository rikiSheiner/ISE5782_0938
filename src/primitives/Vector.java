package primitives;

/**
 * Vector class represents a vector that starts in (0,0,0) and ends on specific point
 * @author Rivka Sheiner
 */
public class Vector extends Point{
    /**
     * Vector constructor based on 3 coordinates of the end point of the vector
     * @param x - first coordinate of end point
     * @param y - second coordinate of end point
     * @param z - third coordinate of end point
     * @throws IllegalArgumentException in case of trying to represent the zero vector
     */
    public Vector(double x, double y, double z) throws IllegalArgumentException {
        super(x,y,z);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("zero vector is illegal");
    }

    /**
     * Vector constructor based on point 3D
     * @param double3 end point of the vector
     * @throws IllegalArgumentException in case of trying to represent the zero vector
     */
    Vector(Double3 double3) throws IllegalArgumentException {
        super(double3);
        if(double3.equals(Double3.ZERO))
            throw new IllegalArgumentException("zero vector is illegal");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector))
            return false;
        Vector other = (Vector) obj;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * This method is used for adding of vector to vector.
     * Adding by coordinates.
     * @param v - the vector for adding
     * @return Vector - the sum of the vectors
     */
    public Vector add(Vector v) {
        return new Vector(this.xyz.add(v.xyz));
    }

    /**
     *This method is used for subtracting of vectors.
     * Subtracting by coordinates.
     * @param v - the vector for subtracting
     * @return Vector - the difference between the vectors
     */
    public Vector subtract(Vector v) {
        return new Vector(this.xyz.subtract(v.xyz));
    }

    /**
     *This method is used for vector multiplication in scalar.
     * Each coordinate of the vector is multiplied by the given scalar.
     * @param a - the scalar
     * @return Vector - the result of the multiplication
     */
    public Vector scale(double a) {
        return new Vector(this.xyz.scale(a));
    }

    /**
     *This method is used for conducting a dot product between two vectors.
     * The dot product is calculated by the formula: u*v = u1*v1+u2*v2
     * @param v - the second vector of the dot product
     * @return double - the result of the dot product
     */
    public double dotProduct(Vector v) {
        return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
    }

    /**
     *This method is used for finding vector normal to two vectors.
     * The finding is by the formula of cross product: uxv = (u2*v3-u3*v2,u3*v1-u1*v3,u1*v2-u2*v1)
     * @param v - the second vector of the cross product
     * @return Vector - the normal vector to two vectors
     */
    public Vector crossProduct(Vector v) {
        return new Vector(new Double3(this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2,
                this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3, this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1));
    }

    /**
     *This method is used for finding the length squared of a vector
     * It is calculated by the distance formula.
     * In this case the formula is d^2 = x1*x1 + y1*y1 + z1*z1, because the vector starts in (0,0,0)
     * @return double - the length squared of the vector
     */
    public double lengthSquared() {
        return this.xyz.d1*this.xyz.d1+ this.xyz.d2*this.xyz.d2+this.xyz.d3*this.xyz.d3;
    }

    /**
     *This method is used for finding the length of a vector.
     * It is calculated by another method which calculates the length squared
     * @return double - the length of the vector
     */
    public double length() {

        return Math.sqrt(lengthSquared());
    }

    /**
     *This method is used for normalizing of vector.
     *In order to normalize the vector we have to divide each coordinate of the vector in his length.
     * @return Vector - this vector normalized (length = 1)
     */
    public Vector normalize() {
        double len = this.length();
        if(len != 1)
            return new Vector(new Double3(this.xyz.d1/len,this.xyz.d2/len,this.xyz.d3/len));
        return this;
    }



}