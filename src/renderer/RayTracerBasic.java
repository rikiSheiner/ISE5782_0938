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
     * The maximum level of recursion for calculating the color of specific point
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * The minimal factor of attenuation for continuation of recursion for calculating
     * the color of specific point
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * The initial factor of attenuation for the recursion of calculating the color
     * of specific point
     */
    private static final double INITIAL_K = 1.0;

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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Function calcColor is a wrap function used for calculating the
     * color of specific point on geometry body.
     * @param gp - the point of intersection
     * @param ray - the ray which intersects the geometric body in the point gp
     * @return Color - the color of specific point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambient.getIntensity());
    }

    /**
     * Function calcColor is used for calculating the color of specific
     * point on geometry body.
     * @param gp - the point of intersection
     * @param ray - the ray which intersects the geometric body in the point gp
     * @param level - the depth of the recursion for calculating the color of the point
     * @param k - the factor of attenuation
     * @return Color
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, double k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Function calcGlobalEffects calculates the contribution of the reflection
     * and refraction rays to the color of specific gp point.
     * @param gp - the intersection point of the ray with the geometric body
     * @param ray - the ray which intersect the geometric body
     * @param level - the depth of the recursion
     * @param k - the factor of attenuation
     * @return Color
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        double kr = material.kR.getD1(), kkr = k * kr;
        Ray reflectedRay = constructReflectedRay(gp.point, ray.getDir(),n);
        if (kkr > MIN_CALC_COLOR_K) {
            color = color.add(calcGlobalEffect(reflectedRay, level, kr, kkr));
        }

        double kt = material.kT.getD1(), kkt = k * kt;
        Ray refractedRay = constructRefractedRay(gp.point, ray.getDir(),n);
        if (kkt > MIN_CALC_COLOR_K) {
            color = color.add(calcGlobalEffect(refractedRay, level, kt, kkt));
        }

        return color;
    }

    /**
     * Function calcGlobalEffect is help function used for calculation of
     * one global effect in the scene.
     * @param ray - the ray which intersect the geometric body
     * @param level - the level of recursion for calculating the color
     * @param kx - the factor of attenuation
     * @param kkx - the factor of attenuation for the next calculating
     * @return Color
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null) ? scene.background : calcColor(gp, ray, level-1, kkx).scale(kx);
    }

    /**
     * Function calcLocalEffects calculates the contribution of the light
     * sources in the scene to the color of specific gp point.
     * @param gp - the intersection point of the ray with the geometric body
     * @param ray - the ray which intersect the geometric body
     * @param k
     * @return Color - the total contribution of all the light sources
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        int nShininess = gp.geometry.getMaterial().nShininess;
        double kd = gp.geometry.getMaterial().kD.getD1();
        double ks = gp.geometry.getMaterial().kS.getD1();

        Color color = Color.BLACK.add(gp.geometry.getEmission());

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(gp, lightSource, l, n);
                if(ktr * k > MIN_CALC_COLOR_K ) {
                    Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);
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
     * Function transparency is used for calculating the level of transparency
     * of the color which passes through specific geometry point.
     * @param geopoint - the geo point where we check transparency
     * @param ls - the light source
     * @param l - the vector from the light source to the geometry body
     * @param n - the normal to the geometry body in the intersection point
     * @return double
     */
    private double transparency(GeoPoint geopoint, LightSource ls, Vector l, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geopoint.point, lightDirection, n, DELTA);
        double lightDistance = ls.getDistance(geopoint.point);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,lightDistance);
        if (intersections == null) return 1.0;

        double ktr = 1.0;
        //loop over intersections and for each intersection which is closer to the
        //point than the light source multiply ktr by 𝒌𝑻 of its geometry
        for(GeoPoint gp : intersections){
            if(alignZero( gp.point.distance(geopoint.point) - lightDistance ) <= 0){
                ktr *= gp.geometry.getMaterial().kT.getD1();
                if(ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }

    /**
     * This method is used for constructing the refracted (transparent) ray
     * from the geometry body at specific point
     * @param p - the intersection point
     * @param v - the dir vector of the ray which intersects the geometry
     * @param n - the normal to the geometry at the intersection point
     * @return Ray - the refracted ray
     */
    private Ray constructRefractedRay(Point p, Vector v, Vector n){
        return new Ray(p, v, n, DELTA);
    }

    /**
     * This method is used for constructing the reflected ray
     * from the geometry body at specific point
     * @param p - the intersection point
     * @param v - the dir vector of the ray which intersects the geometry
     * @param n - the normal to the geometry at the intersection point
     * @return Ray - the reflected ray
     */
    private Ray constructReflectedRay(Point p, Vector v, Vector n){
        //𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ 𝒏
        double vn = v.dotProduct(n);
        if(vn == 0)
            return null;

        Vector temp = n.scale(2 * vn);
        Vector r = v.subtract(temp);
        return new Ray(p, r, n, DELTA);
    }


    /**
     * This method is used for finding the closest intersection point of
     * the ray with the geometry body
     * @param ray - the ray which intersects the geometry body
     * @return GeoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }
}
