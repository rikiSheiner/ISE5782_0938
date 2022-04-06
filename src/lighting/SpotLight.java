package lighting;

import primitives.*;

/**
 * SpotLight class represents a light source that shines at 180 degrees
 */
public class SpotLight extends PointLight{

    /**
     * The direction vector of the spotlight
     */
    private Vector direction;

    /**
     * SpotLight parameters constructor
     * @param intensity - the intensity of the spotlight at source
     * @param position - the location of the spotlight
     * @param direction - the direction vector of the spotlight
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p){
        double factor = Math.max(0, direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(factor);
    }
}
