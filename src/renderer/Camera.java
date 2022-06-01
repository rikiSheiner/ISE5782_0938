package renderer;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.lang.UnsupportedOperationException;
import java.util.Random;

/**
 * Camera class represents the camera which takes the picture
 * @author Rivka Sheiner
 */
public class Camera {

    // fields
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
    /**
     * indicates is we want to render with the effect of depth of field or not
     */
    private boolean isDepthOfField;
    /**
     * the size of the aperture of the camera
     */
    private double apertureSize;
    /**
     * the maximal distance between the camera to the objects in focus
     */
    private double focalLength;


    // constructors
    /**
     * Camera parameters constructor
     *
     * @param location
     * @param v_to
     * @param v_up
     * @throws IllegalArgumentException
     */
    public Camera(Point location, Vector v_to, Vector v_up) throws IllegalArgumentException {
        this.p0 = location;
        this.vTo = v_to.normalize();
        this.vUp = v_up.normalize();
        if (v_to.dotProduct(v_up) != 0)
            throw new IllegalArgumentException("The direction vectors of the camera are not orthogonal");
        this.vRight = v_to.crossProduct(v_up).normalize();
    }

    /**
     * Camera parameters constructor
     * @param p0
     * @param vTo
     * @param vUp
     * @param apertureSize
     * @param focalLength
     * @param isDepthOfField
     * @param numSamples
     */
    public Camera(Point p0, Vector vTo, Vector vUp, double apertureSize, double focalLength, boolean isDepthOfField, int numSamples)
        throws IllegalArgumentException{

        this.p0 = p0;
        this.vTo = vTo;
        this.vUp = vUp;
        if (vTo.dotProduct(vUp) != 0)
            throw new IllegalArgumentException("The direction vectors of the camera are not orthogonal");
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
        this.apertureSize = apertureSize;
        this.focalLength = focalLength;
        this.numSamples = numSamples;
        this.isDepthOfField = isDepthOfField;
    }


    // getters
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


    // setters
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    public Camera setVPDistance(double distance) {
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
     *
     * @param nX - the number of pixels in X axis
     * @param nY - the number of pixels in Y axis
     * @param j  - the index on X axis
     * @param i  - the index on Y axis
     * @return Ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point Pc = p0.add(vTo.scale(distance));

        double Ry = height / nY;
        double Rx = width / nX;

        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;

        Point Pij = Pc;

        if (Xj != 0)
            Pij = Pij.add(vRight.scale(Xj));
        if (Yi != 0)
            Pij = Pij.add(vUp.scale(Yi));

        return new Ray(p0, Pij.subtract(p0));
    }



    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i)
    {
        Ray ray=constructRay( nX,  nY, j,  i); // the ray through the center of the pixel

        // the effect of anti aliasing is active
        if(this.isDepthOfField == false)
            return constructRaysForAntiAliasing(nX,nY,j,i);

        // the effect of depth of field is active
        Point edgePoint=p0.add(vUp.scale((apertureSize/2)).subtract(vRight.scale((apertureSize /2))));
        return constructRaysForDepthOfField(ray,edgePoint);
    }

    /**
     * The function constructRaysForAntiAliasing is used for creation of beam of rays through
     * specific pixel in the view plane.
     *
     * @param nX - the number of pixels in X axis
     * @param nY - the number of pixels in Y axis
     * @param j  - the index on X axis
     * @param i  - the index on Y axis
     * @return List of Ray
     */
    public List<Ray> constructRaysForAntiAliasing(int nX, int nY, int j, int i) {

        // super sampling - random access

        Point Pc = p0.add(vTo.scale(distance)); // the center point of the view plane

        double Ry = height / nY; // the height of pixel in the view plane
        double Rx = width / nX; // the width of pixel in the view plane

        double Yi = (i - nY / 2d) * Ry + Ry / 2d;
        double Xj = (j - nX / 2d) * Rx + Rx / 2d;

        List<Ray> rays = new LinkedList<>(); // the beam of rays

        Point p = Pc.add(vRight.scale(Xj)).subtract(vUp.scale(Yi)); // the center of pixel (j,i)
        Point Pij;

        double move1, move2;

        for (int k = 0; k < numSamples; k++) {
            move1 = new Random().nextDouble(-Rx / 2, Rx / 2);
            move2 = new Random().nextDouble(-Ry / 2, Ry / 2);

            Pij = new Point(p.getXyz().getD1() + move1, p.getXyz().getD2() + move2, p.getXyz().getD3());
            rays.add(new Ray(p0, Pij.subtract(p0).normalize()));
        }

        return rays;
    }


    /**
     * Function findFocalPoint is used for finding the focal point with ray,
     * by using law of cosines on the triangle that the ray and vTo create.
     * @param ray - the ray that through the center of the pixel
     * @return focal point
     */
    public Point findFocalPoint( Ray ray)
    {
        double cosine=vTo.dotProduct(ray.getDir()); //the cosine of the angle between vto and the vector of ray from pixel
        double distance=this.focalLength /cosine; //the length of the ray from the camera to the focal point by using the law of cosines
        Point focalPoint=ray.getPoint(distance); //find focal point by the ray and the distance from camera.
        return focalPoint;
    }

    /**
     * Function constructRaysForDepthOfField gets the up right point of the square,
     * creates the three others edges based on the aperture size
     * and returns the rays from them to the focal point.
     * @param ray - the ray through the center of the pixel
     * @param edge1 - the up and right point of the square
     * @return list of Ray
     */
    public List<Ray> constructRaysForDepthOfField(Ray ray,Point edge1)
    {
        Point focalPoint=this.findFocalPoint(ray); // the focal point of the camera
        List<Ray> rays=new LinkedList<>();

        //p1 is the left up edge of the square
        Point edge2 = edge1.add(vRight.scale(apertureSize));// the right up edge
        Point edge3 = edge2.subtract(vUp.scale(apertureSize));// the right down edge
        Point edge4 = edge3.subtract(vRight.scale(apertureSize));// the left down edge

        //create rays from the points to the focal point
        rays.add(new Ray(edge1,focalPoint.subtract(edge1)));
        rays.add(new Ray(edge2,focalPoint.subtract(edge2)));
        rays.add(new Ray(edge3,focalPoint.subtract(edge3)));
        rays.add(new Ray(edge4,focalPoint.subtract(edge4)));
        rays.add(ray); //the ray through the center of the pixel

        return rays;
    }


    /**
     * Function renderImage is used for constructing a rays through each pixel
     * in the view plane and coloring the pixels of the image accordingly.
     *
     * @return Camera
     * @throws MissingResourceException
     * @throws UnsupportedOperationException
     */
    public Camera renderImage() throws MissingResourceException, UnsupportedOperationException {
        if (p0 == null || vTo == null || vUp == null || imageWriter == null || rayTracer == null)
            throw new MissingResourceException("Can't render image because of lack of resources", "Camera", "");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        if (numSamples <= 1) {
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = constructRay(nX, nY, j, i);
                    imageWriter.writePixel(j, i, rayTracer.traceRay(ray));
                }
            }
        }
        else { // super sampling
            List<Ray> rays;
            Color avgColor;

            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    rays = constructRaysThroughPixel(nX,nY,j,i);
                    avgColor = new Color(0, 0, 0);

                    for (Ray ray : rays) {
                        avgColor = avgColor.add(rayTracer.traceRay(ray));
                    }

                    avgColor = avgColor.reduce(rays.size());
                    imageWriter.writePixel(j, i, avgColor);
                }
            }
        }

        return this;
    }


    /**
     * Function printGrid is used for coloring the grid of the image
     *
     * @param interval - the width of each square in the grid
     * @param color    - the color of the grid lines'
     * @throws MissingResourceException
     */
    public void printGrid(int interval, Color color) throws MissingResourceException {
        if (imageWriter == null)
            throw new MissingResourceException("Can't print grid, because there isn't producer to the picture", "Camera", "lack of imageWriter");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Function writeToImage is used for producing the picture
     *
     * @throws MissingResourceException
     */
    public void writeToImage() throws MissingResourceException {
        if (imageWriter == null)
            throw new MissingResourceException("Can't write to image, because of lack of producer to the picture", "Camera", "lack of imageWriter");
        this.imageWriter.writeToImage();
    }






}
