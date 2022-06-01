package lighting;

import primitives.*;

/**
 * AmbientLight class represents the environmental light of the scene
 * @author Rivka Sheiner
 */
public class AmbientLight extends Light {


    /**
     * AmbientLight parameters constructor
     * @param Ia the intensity of the ambient light in the source
     * @param Ka the factor of attenuation of environmental lighting
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * AmbientLight default constructor
     * initializes the intensity as black color
     */
    public AmbientLight(){
        super(Color.BLACK);
    }

}
