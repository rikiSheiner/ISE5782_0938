package renderer;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
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
    /**
     * the number of rays in the beam of rays which we cast from pixel
     */
    private int numSamples;

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    public int getNumSamples() {
        return numSamples;
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

    public Camera setNumSamples(int numSamples) {
        this.numSamples = numSamples;
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
     * The function constructRayBeamThroughPixel is used for creation of beam of rays through
     * specific pixel in the view plane.
     * @param nX - the number of pixels in X axis
     * @param nY - the number of pixels in Y axis
     * @param j - the index on X axis
     * @param i - the index on Y axis
     * @return List of Ray
     */
    public List<Ray> constructRayBeamThroughPixel(int nX, int nY, int j, int i) {
        // super sampling with grid
        Point Pc = p0.add(vTo.scale(distance)); // the center point of the view plane

        double Ry = height/nY; // the height of pixel in the view plane
        double Rx = width/nX; // the width of pixel in the view plane

        double Yi = (i-nY/2d)*Ry+Ry/2d;
        double Xj = (j-nX/2d)*Rx+Rx/2d;

        double sqrtNumSamples = Math.sqrt(numSamples);
        double sampleWidth = Rx / (2*sqrtNumSamples);
        double sampleHeight = Ry / (2*sqrtNumSamples);

        Point p = Pc.add(vRight.scale(Xj)).subtract(vUp.scale(Yi));
        Point Pij;
        List<Ray> rays = new LinkedList<>(); // the beam of rays

        for(int k = 0; k < sqrtNumSamples; k++){
            for(int l = 0; l < sqrtNumSamples; l++){
                Pij = new Point(p.getXyz().getD1()+k*sampleWidth, p.getXyz().getD2()+l*sampleHeight,p.getXyz().getD3());
                rays.add(new Ray(p0, Pij.subtract(p0).normalize()));
            }
        }

        return rays;

    }



    /**
     * Function renderImage is used for constructing a rays through each pixel
     * in the view plane and coloring the pixels of the image accordingly.
     * @return Camera
     * @throws MissingResourceException
     * @throws UnsupportedOperationException
     */
    public Camera renderImage() throws MissingResourceException, UnsupportedOperationException {
        if(p0 == null || vTo == null || vUp == null || imageWriter == null || rayTracer == null )
            throw new MissingResourceException("Can't render image because of lack of resources", "Camera", "");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        if(numSamples <= 1){
            for(int i = 0;  i< nY; i++){
                for(int j = 0; j < nX; j++){
                    Ray ray = constructRay(nX, nY, j, i);
                    imageWriter.writePixel(j, i, rayTracer.traceRay(ray));
                }
            }
        }
        else{ // super sampling
            List<Ray> rays;
            Color avgColor;

            for(int i = 0;  i< nY; i++){
                for(int j = 0; j < nX; j++){
                    rays = this.constructRayBeamThroughPixel(nX, nY, j, i);
                    avgColor = new Color(0,0,0);
                    for(Ray ray : rays){
                        avgColor = avgColor.add(rayTracer.traceRay(ray));
                    }
                    avgColor = avgColor.reduce(numSamples);
                    imageWriter.writePixel(j, i, avgColor);
                }
            }

        }

        return this;
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
