package geometries;

import primitives.Ray;

/**
 * Cylinder class represents a tube with limited height
 * @author Rivka Sheiner
 */
public class Cylinder extends Tube {
    /**
    The height of the cylinder
     */
    private double height;

    /**
     * Cylinder parameters constructor
     * @param axisRay the center ray of the cylinder
     * @param radius the radius of the base of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * This method returns the height of the cylinder
     * @return double - the height
     */
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return super.toString()+" ,height=" + height;
    }
}
