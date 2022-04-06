package lighting;

import primitives.*;

/**
 * DirectionalLight class represents a far away light source (like the sun)
 */
public class DirectionalLight extends Light implements LightSource{

    /**
     * The direction vector of the directional light
     */
    private Vector direction;

    /**
     * DirectionalLight parameters constructor
     * @param intensity - the intensity of the light in the source
     * @param direction - the direction vector from the light source
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p){
        return this.getIntensity();
    }
    @Override
    public Vector getL(Point p){
        return this.direction;
    }
}
