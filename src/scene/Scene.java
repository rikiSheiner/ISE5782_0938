package scene;

import lighting.AmbientLight;
import primitives.*;
import geometries.*;
import lighting.*;

import java.util.LinkedList;
import java.util.List;

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
     * List of the light sources in the scene
     */
    public List<LightSource> lights;


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
        this.lights = new LinkedList<>();
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

    /**
     * This method is used for updating the light sources in the scene
     * @param lights - list of light sources
     * @return Scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
