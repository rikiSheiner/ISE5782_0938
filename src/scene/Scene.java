package scene;

import lighting.AmbientLight;
import primitives.*;
import geometries.*;

public class Scene {

    /**
     * The name of the scene
     */
    public String name;
    /**
     * The background color of the scene
     */
    public Color background;
    /**
     * The environmental light of the scene
     */
    public AmbientLight ambientLight;
    /**
     * The geometry shapes in the 3D model of the scene
     */
    public Geometries geometries;

    /**
     * Scene parameters constructor
     * initializes the ambient light and the background color to black
     * initializes the 3D model without shapes
     * @param name - String
     */
    public Scene(String name) {
        this.name = name;
        this.background = Color.BLACK;
        this.ambientLight = new AmbientLight();
        this.geometries = new Geometries();
    }

    /**
     * This method is used for updating the background color
     * @param background - Color
     * @return Scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * This method is used for updating the ambient light
     * @param ambientLight - Color
     * @return Scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * This method is used for updating the 3D model of geometries
     * @param geometries - Geometries
     * @return Scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
