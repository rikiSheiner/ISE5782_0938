package lighting;

import primitives.*;

/**
 * LightSource class represents a source of light in the scene
 * @author Rivka Sheiner
 */
public interface LightSource {
    /**
     * Function getIntensity returns the intensity of the light source in specific point
     * @param p - the point where the intensity of the light is being measured
     * @return Color - the color that the light source contributes to the point
     */
    public Color getIntensity(Point p);

    /**
     *Function getL is used for calculating the vector from the center of the
     * light source to specific point.
     * @param p - the end point of the vector
     * @return Vector
     */
    public Vector getL(Point p);

    /**
     * This function is used for calculating the distance from the light source to the point
     * @param point - the point on the geometric body
     * @return double
     */
    double getDistance(Point point);
}
