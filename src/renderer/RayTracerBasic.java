package renderer;

import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import lighting.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * RayTracerBasic class responsible for basic tracing on the rays which
 * are sent to the scene.
 * @author Rivka Sheiner
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * Size of moving of the shadow rays
     */
    private static final double DELTA = 0.1;

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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * Function calcColor is used for calculating the color of specific
     * @param gp - the geometry point
     * @param ray - the ray which intersects the geometric body in the point gp
     * @return Color - the color of this geometry point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(gp.geometry.getEmission())
                .add(calcLocalEffects(gp, ray));
    }

    /**
     * Function calcLocalEffects calculates the contribution of the light
     * sources in the scene to the color of specific intersection point.
     * @param intersection - the intersection point of the ray with the geometric body
     * @param ray - the ray which intersect the geometric body
     * @return Color - the total contribution of all the light sources
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;
        double kd = intersection.geometry.getMaterial().kD.getD1();
        double ks = intersection.geometry.getMaterial().kS.getD1();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(intersection, l, n, lightSource, nv)){
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * Function calcSpecular calculates the specular lighting of the body under the
     * influence of the light source.
     * @param ks - factor of attenuation of the material
     * @param l - vector from the light source to the point on the geometric body
     * @param n - the normal vector to the geometric body in the intersection point
     * @param v - the direction vector of the ray which intersects the geometric body
     * @param nShininess - the size of shininess of the geometric body's material
     * @param lightIntensity - the intensity of the external light source
     * @return Color
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {

        Vector lNormalized = l.normalize();
        Vector nNormalized = n.normalize();

        Vector r = lNormalized.subtract(nNormalized.scale(2*lNormalized.dotProduct(nNormalized)));
        double factor = ks * Math.pow(Math.max(0, -v.normalize().dotProduct(r.normalize())), nShininess);
        return lightIntensity.scale(factor);
    }

    /**
     * Function calcDiffusive calculates the diffuse illumination across the geometric
     * body under the influence of the light source.
     * @param kd - factor of attenuation of the material
     * @param l - vector from the light source to the point on the geometric body
     * @param n - the normal vector to the geometric body in the intersection point
     * @param lightIntensity - the intensity of the external light source
     * @return Color
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = kd * Math.abs(l.normalize().dotProduct(n.normalize()));
        return lightIntensity.scale(factor);
    }

    /**
     * Function unshaded is used for checking if the point is hided from the light source
     * @param gp - the geo point where we check shadow
     * @param l - the vector from the light source to the geometry body
     * @param n - the normal to the geometry body in the intersection point
     * @param light - the light source
     * @param nv - the dot product of n and the dir vector of the ray
     * @return boolean
     */
     private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light, double nv){
         Vector lightDirection = l.scale(-1); // from point to light source

         Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
         Point point = gp.point.add(epsVector);

         Ray lightRay = new Ray(point, lightDirection);
         double lightDistance = light.getDistance(gp.point);

         List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,lightDistance);

        if(intersections == null || intersections.size() == 0)
            return true;

         return false;
    }
}
