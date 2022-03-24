package renderer;
import primitives.*;

import static primitives.Util.isZero;

/**
 * Camera class represents the camera which takes the picture
 * @author Rivka Sheiner
 */
public class Camera {
    /**
     * the location of the camera
     */
    private Point p0;
    /**
     * the vector of the camera towards the view plane
     */
    private Vector vTo;
    /**
     * the vector of the camera upward the view plane
     */
    private Vector vUp;
    /**
     * the vector of the camera towards the right of the view plane
     */
    private Vector vRight;
    /**
     * the height of the camera
     */
    private double height;
    /**
     * the width of the camera
     */
    private double width;
    /**
     * the distance from the camera to the view plane
     */
    private double distance;

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    public Camera(Point location, Vector v_to, Vector v_up) throws IllegalArgumentException{
        this.p0 = location;
        this.vTo = v_to.normalize();
        this.vUp = v_up.normalize();
        if(v_to.dotProduct(v_up) != 0)
            throw new IllegalArgumentException("The direction vectors of the camera are not orthogonal");
        this.vRight = v_to.crossProduct(v_up).normalize();
    }

    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }

    /**
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        Point Pc = p0.add(vTo.scale(distance));

        double Ry = height/nY;
        double Rx = width/nX;

        double Yi = -(i-(nY-1)/2d)*Ry;
        double Xj = (j-(nX-1)/2d)*Rx;

        Point Pij = Pc;

        if(Xj != 0)
            Pij = Pij.add(vRight.scale(Xj));
        if(Yi != 0)
            Pij = Pij.add(vUp.scale(Yi));

        return new Ray(p0,Pij.subtract(p0));
    }
}
