package lighting;

import primitives.*;

/**
 * PointLight class represents an omnidirectional light source (like a bulb)
 */
public class PointLight extends Light implements LightSource{

    /**
     * The location of the point light
     */
    private Point position;
    /**
     * Fading coefficient of the point light, depending on distance.
     */
    private double kC;
    /**
     * Fading coefficient of the point light, depending on distance.
     */
    private double kL;
    /**
     *Fading coefficient of the point light, depending on distance.
     */
    private double kQ;

    /**
     * PointLight parameters constructor
     * @param intensity - the intensity of the light in the source
     * @param position - the location of the point light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    /**
     * This method is used for updating the fading coefficient - kC - of the point light
     * @param kC - the fading coefficient for updating
     * @return PointLight
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * This method is used for updating the fading coefficient - kL - of the point light
     * @param kL - the fading coefficient for updating
     * @return PointLight
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * This method is used for updating the fading coefficient - kQ - of the point light
     * @param kQ - the fading coefficient for updating
     * @return PointLight
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p){
        double d = position.distance(p);
        double temp = kC+kL*d+kQ*d*d;
        return getIntensity().reduce(temp);
    }
    @Override
    public Vector getL(Point p){
        return p.subtract(position);
    }
}
