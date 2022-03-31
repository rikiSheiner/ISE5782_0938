package renderer;

import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * RayTracerBasic class responsible for basic tracing on the rays which
 * are sent to the scene.
 * @author Rivka Sheiner
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * RayTracerBasic constructor
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Function traceRay is used for tracing specific ray which is sent
     * to the scene and calculating her color.
     * @param ray - the ray which is sent to the scene
     * @return Color
     */
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if(intersections == null || intersections.size() == 0)
            return scene.background;
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Function calcColor is used for calculating the color of specific
     * point in the scene.
     * @param point
     * @return Color - the color of this point
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
