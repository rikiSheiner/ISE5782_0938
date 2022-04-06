package lighting;

import primitives.*;

/**
 * Light class represents a light in the scene
 */
abstract class Light {

    /**
     * The intensity of the light in the source
     */
    private Color intensity;

    /**
     * Light constructor based on the light's intensity
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * This method returns the color of the light in the source
     * @return Color
     */
    public Color getIntensity() {
        return intensity;
    }
}
