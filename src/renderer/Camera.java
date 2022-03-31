package renderer;
import primitives.*;

import java.util.MissingResourceException;
import java.lang.UnsupportedOperationException;

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
    /**
     * the producer of the picture
     */
    private ImageWriter imageWriter;
    /**
     * the base of the ray tracing for the camera
     */
    private RayTracerBase rayTracer;

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    /**
     * Camera parameter constructor
     * @param location
     * @param v_to
     * @param v_up
     * @throws IllegalArgumentException
     */
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

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * The function ConstructRay is used for creation of ray through
     * specific pixel in the view plane.
     * @param nX - the number of pixels in X axis
     * @param nY - the number of pixels in Y axis
     * @param j - the index on X axis
     * @param i - the index on Y axis
     * @return Ray
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

    /**
     * Function renderImage is used for constructing a rays through each pixel
     * in the view plane and coloring the pixels of the image accordingly.
     * @throws MissingResourceException
     * @throws UnsupportedOperationException
     */
    public void renderImage() throws MissingResourceException, UnsupportedOperationException {
        if(p0 == null || vTo == null || vUp == null || imageWriter == null || rayTracer == null )
            throw new MissingResourceException("Can't render image because of lack of resources", "Camera", "");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for(int i = 0;  i< nY; i++){
            for(int j = 0; j < nX; j++){
                Ray ray = constructRay(nX, nY, j, i);
                imageWriter.writePixel(j, i, rayTracer.traceRay(ray));
            }
        }
    }

    /**
     *Function printGrid is used for coloring the grid of the image
     * @param interval - the width of each square in the grid
     * @param color - the color of the grid lines'
     * @throws MissingResourceException
     */
    public void printGrid(int interval, Color color) throws MissingResourceException{
        if(imageWriter == null)
            throw new MissingResourceException("Can't print grid, because there isn't producer to the picture", "Camera", "lack of imageWriter");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for(int j = 0; j < nX; j++){
                if(i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Function writeToImage is used for producing the picture
     * @throws MissingResourceException
     */
    public void writeToImage() throws MissingResourceException{
        if(imageWriter == null)
            throw new MissingResourceException("Can't write to image, because of lack of producer to the picture", "Camera", "lack of imageWriter");
        this.imageWriter.writeToImage();
    }




}
