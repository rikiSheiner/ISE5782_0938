package geometries;

import primitives.*;

/**
 * Geometry interface represents the behavior of all geometry objects
 *@author Rivka Sheiner
 */
public abstract class Geometry extends Intersectable {

    /**
     * The color of the light which is being emitted from the geometry
     */
    public Color emission = Color.BLACK;
    /**
     * The material from which a geometric body is made
     */
    private Material material = new Material();

    /**
     * This method returns the color of the lighting emitted from a geometric body
     * @return Color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * This method is used for updating the emission light of the geometry
     * @param emission
     * @return Geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * This method returns the material from which the geometric is made
     * @return Material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * This method is used for updating the material from which rhe geometry is made
     * @param material
     * @return Geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * This method is used for finding the normal to specific point
     * on the geometry object.
     * @param p the point on the geometry object
     * @return Vector - the normal vector to the object in this point
     */
    public abstract Vector getNormal(Point p);
}
