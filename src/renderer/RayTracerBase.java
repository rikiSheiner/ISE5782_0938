package renderer;

import primitives.*;
import scene.*;

/**
 * RayTracerBase class responsible for tracing the rays which are
 * sent to the scene.
 * @author Rivka Sheiner
 */
public abstract class RayTracerBase {

    /**
     * The scene to which the rays are sent
     */
    protected Scene scene;

    /**
     * RayTracerBase constructor
     * @param scene - to where the rays are sent
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Function traceRay is used for tracing specific ray which is sent
     * to the scene and calculating her color.
     * @param ray - the ray which is sent to the scene
     * @return Color
     */
    public abstract Color traceRay(Ray ray);
}
