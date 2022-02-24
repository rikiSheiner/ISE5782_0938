package primitives;

public class Vector extends Point{
    public Vector(double x, double y, double z) throws IllegalArgumentException
    {
        super(x,y,z);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("zero vector is illegal");
    }
    Vector(Double3 double3) throws IllegalArgumentException
    {
        super(double3);
        if(double3.equals(Double3.ZERO))
            throw new IllegalArgumentException("zero vector is illegal");
    }
    @Override
    public boolean equals(Object obj)
    {
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
    public String toString()
    {
        return super.toString();
    }
    public Vector add(Vector v)
    {
        return new Vector(this.xyz.add(v.xyz));
    }
    public Vector subtract(Vector v)
    {
        return new Vector(this.xyz.subtract(v.xyz));
    }
    public Vector scale(double a)
    {
        return new Vector(this.xyz.scale(a));
    }
    public double dotProduct(Vector v)
    {
        //להוסיף בדיקות לגבי כיוון הוקטורים כדרוש
        //𝑢⋅𝑣=𝑢_1*𝑣_1+𝑢_2*𝑣_2+𝑢_3*𝑣_3
        return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
    }
    public Vector crossProduct(Vector v)
    {
        return new Vector(new Double3(this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2,
                this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3, this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1));
    }
    public double lengthSquared()
    {
        return this.xyz.d1*this.xyz.d1+ this.xyz.d2*this.xyz.d2+this.xyz.d3*this.xyz.d3;
    }
    public double length()
    {
        return Math.sqrt(lengthSquared());
    }
    public Vector normalize()
    {
        double len = this.length();
        return new Vector(new Double3(this.xyz.d1/len,this.xyz.d2/len,this.xyz.d3/len));
    }



}