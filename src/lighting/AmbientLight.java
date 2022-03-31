package lighting;

import primitives.*;

/**
 * AmbientLight class represents the environmental light of the scene
 */
public class AmbientLight {

    /**
     * The intensity of the ambient light in the point
     */
    private Color intensity;

    /**
     * AmbientLight parameters constructor
     * @param Ia the intensity of the ambient light in the source
     * @param Ka the reducing coefficient of environmental lighting
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * AmbientLight default constructor
     * initializes the intensity as black color
     */
    public AmbientLight(){
        this.intensity = Color.BLACK;
    }

    /**
     * This method returns the intensity of the ambient light
     * @return Color - the intensity
     */
    public Color getIntensity() {
        return intensity;
    }

}
