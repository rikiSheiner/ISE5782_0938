package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.lang.UnsupportedOperationException;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Camera class represents the camera which takes the picture
 * @author Rivka Sheiner
 */
public class Camera {

    //------------------------------------------ fields -----------------------------------------------------
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
     * indicates whether we want to render with the effect of depth of field or not
     */
    private boolean isDepthOfField;
    /**
     * the size of the aperture of the camera -
     * determines how blurry objects that are out of focus will appear
     */
    private double apertureSize;
    /**
     * the distance between the camera to the objects in focus
     */
    private double focalLength;
    /**
     * the number of threads used for rendering the pictures
     */
    private int numThreads = 1;
    /**
     * the maximal level of multi threading which can be used for rendering the pictures
     */
    private final int MAX_NUM_THREADS = 4;
    /**
     * indicates whether we use the method of adaptive super sampling or not
     */
    private boolean is_ASS = false;
    /**
     * the maximal difference between the average color to the color of the sample
     * for stopping of sampling in ASS
     */
    private final int MAX_DIF = 10;



    /**
     * Pixel is a helper class. It is used for multi-threading in the renderer and
     * for follow up its progress.<br/>
     * There is a main follow-up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     *
     */
    class Pixel {
        private static int maxRows = 0;
        private static int maxCols = 0;
        private static long totalPixels = 0l;

        private static volatile int cRow = 0;
        private static volatile int cCol = -1;
        private static volatile long pixels = 0l;
        private static volatile long last = -1l;
        private static volatile int lastPrinted = -1;

        private static boolean print = false;
        private static long printInterval = 100l;
        private static final String PRINT_FORMAT = "%5.1f%%\r";
        private static Object mutexNext = new Object();
        private static Object mutexPixels = new Object();

        int row;
        int col;

        /**
         * Initialize pixel data for multi-threading
         *
         * @param maxRows  the amount of pixel rows
         * @param maxCols  the amount of pixel columns
         * @param interval print time interval in seconds, 0 if printing is not required
         */
        static void initialize(int maxRows, int maxCols, double interval) {
            Pixel.maxRows = maxRows;
            Pixel.maxCols = maxCols;
            Pixel.totalPixels = (long) maxRows * maxCols;
            cRow = 0;
            cCol = -1;
            pixels = 0;
            printInterval = (int) (interval * 1000);
            print = printInterval != 0;
            last = -1l;
        }

        /**
         * Function for thread-safe manipulating of main follow-up Pixel object - this
         * function is critical section for all the threads, and static data is the
         * shared data of this critical section.<br/>
         * The function provides next available pixel number each call.
         *
         * @return true if next pixel is allocated, false if there are no more pixels
         */
        public boolean nextPixel() {
            synchronized (mutexNext) {
                if (cRow == maxRows)
                    return false;
                ++cCol;
                if (cCol < maxCols) {
                    row = cRow;
                    col = cCol;
                    return true;
                }
                cCol = 0;
                ++cRow;
                if (cRow < maxRows) {
                    row = cRow;
                    col = cCol;
                    return true;
                }
                return false;
            }
        }

        /**
         * Finish pixel processing
         */
        static void pixelDone() {
            synchronized (mutexPixels) {
                ++pixels;
            }
        }

        /**
         * Wait for all pixels to be done and print the progress percentage - must be
         * run from the main thread
         */
        public static void waitToFinish() {
            if (print)
                System.out.printf(PRINT_FORMAT, 0d);

            while (last < totalPixels) {
                printPixel();
                try {
                    Thread.sleep(printInterval);
                } catch (InterruptedException ignore) {
                    if (print)
                        System.out.print("");
                }
            }
            if (print)
                System.out.println("100.0%");
        }

        /**
         * Print pixel progress percentage
         */
        public static void printPixel() {
            long current = pixels;
            if (print && last != current) {
                int percentage = (int) (1000l * current / totalPixels);
                if (lastPrinted != percentage) {
                    last = current;
                    lastPrinted = percentage;
                    System.out.printf(PRINT_FORMAT, percentage / 10d);
                }
            }
        }
    }


    //------------------------------------------ constructors -----------------------------------------------------
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



    //------------------------------------------ getters -----------------------------------------------------
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


    //------------------------------------------ setters -----------------------------------------------------
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
    public Camera setNumThreads(int numThreads) {
        if(numThreads < 1 )
            throw new IllegalArgumentException("the level of multi threading must be positive number");
        if(numThreads > MAX_NUM_THREADS )
            throw new IllegalArgumentException("the level of multi threading can't be greater than 4");

        this.numThreads = numThreads;
        return this;
    }
    public Camera setIs_ASS(boolean is_ASS) {
        this.is_ASS = is_ASS;
        return this;
    }

    //------------------------------------------ constructors of rays -----------------------------------------------------
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

    /**
     * The function constructRaysForAntiAliasing is used for creation of numSamples rays through
     * specific pixel in the view plane for anti aliasing in the picture.
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
        Random random = new Random();

        for (int k = 0; k < numSamples; k++) {
            move1 = random.nextDouble(-Rx / 2, Rx / 2);
            move2 = random.nextDouble(-Ry / 2, Ry / 2);

            Pij = new Point(p.getXyz().getD1() + move1, p.getXyz().getD2() + move2, p.getXyz().getD3());
            rays.add(new Ray(p0, Pij.subtract(p0).normalize()));
        }

        return rays;
    }


    /**
     * Function findFocalPoint is used for finding the focal point with ray.
     * @param ray - the ray that through the center of the pixel
     * @return Point - focal point of the camera
     */
    public Point findFocalPoint(Ray ray) {
        // calculating of focal point by multiplying
        // the ray direction by the focal length
        return ray.getPoint(focalLength);
    }

    /**
     * Function constructRaysForDepthOfField is used for constructing rays for
     * the DOF effect.
     * It is done by creating of numSamples rays from the origin of the ray
     * -which passes through the center of the pixel- shifted by random number
     * -which is effected by the apertureSize- to the focal point of the camera.
     * @param ray - the ray which passes through the center of the pixel
     * @return List of Ray
     */
    public List<Ray> constructRaysForDepthOfField(Ray ray) {
        Point focalPoint = this.findFocalPoint(ray); // the focal point of the camera
        List<Ray> rays = new LinkedList<>();
        Random random = new Random();
        Vector multiVector;
        Point originPoint;

        for(int i = 0; i < numSamples; i++){

            // calculating of the vector for shifting the origin of the ray
            multiVector = new Vector(random.nextDouble(-0.5,0.5),random.nextDouble(-0.5,0.5),random.nextDouble(-0.5,0.5));
            multiVector.scale(apertureSize);

            originPoint = ray.getP0().add(multiVector); // shifting the point of the origin of the ray
            rays.add(new Ray(originPoint, focalPoint.subtract(originPoint))); // adding the shifted ray to the rays' list
        }

        return rays;
    }

    /**
     * Function calcColor_ASS is used for calculating the color of pixel using
     * adaptive super sampling
     * @param nX - the number of pixels in X axis
     * @param nY - the number of pixels in Y axis
     * @param j  - the index on X axis
     * @param i  - the index on Y axis
     * @return Color of pixel (j,i)
     */
    public Color calcColor_ASS(int nX, int nY, int j, int i){
        Point Pc = p0.add(vTo.scale(distance)); // the center point of the view plane

        double Ry = height / nY; // the height of pixel in the view plane
        double Rx = width / nX; // the width of pixel in the view plane

        double Yi = (i - nY / 2d) * Ry + Ry / 2d;
        double Xj = (j - nX / 2d) * Rx + Rx / 2d;

        Point p = Pc.add(vRight.scale(Xj)).subtract(vUp.scale(Yi)); // the center of pixel (j,i)

        return calcColor_ASS(Rx,Ry,5,p);
    }

    /**
     * Function calcColor_ASS is used for calculating the color of pixel using
     * adaptive super sampling
     * @param Rx - the height of subpixel in the view plane
     * @param Ry - the width of subpixel in the view plane
     * @param depth - the maximal level of recursion in sampling of subpixel
     * @param pc - the center point of the subpixel
     * @return Color - the color of the pixel calculated by ASS
     */
    private Color calcColor_ASS(double Rx, double Ry, int depth, Point pc){
        List<Ray> rays = new LinkedList<>(); // the beam of rays through the samples
        List<Color> colors = new LinkedList<>(); // list of the colors of the samples
        Color avgColor = new Color (0,0,0); // the average color of the samples
        Point newPc; // the center of the new subpixel

        // taking of samples from the 4 corners of the subpixel
        Point tmp = new Point(pc.getXyz().getD1() +Rx/2, pc.getXyz().getD2() + Ry/2, pc.getXyz().getD3());
        rays.add(new Ray(p0, tmp.subtract(p0).normalize()));
        tmp = new Point(pc.getXyz().getD1() +Rx/2, pc.getXyz().getD2() - Ry/2, pc.getXyz().getD3());
        rays.add(new Ray(p0, tmp.subtract(p0).normalize()));
        tmp = new Point(pc.getXyz().getD1() -Rx/2, pc.getXyz().getD2() + Ry/2, pc.getXyz().getD3());
        rays.add(new Ray(p0, tmp.subtract(p0).normalize()));
        tmp = new Point(pc.getXyz().getD1() -Rx/2, pc.getXyz().getD2() - Ry/2, pc.getXyz().getD3());
        rays.add(new Ray(p0, tmp.subtract(p0).normalize()));

        // calculating of the average color of the samples
        for(int k = 0; k < rays.size(); k++){
            colors.add(rayTracer.traceRay(rays.get(k)));
            avgColor = avgColor.add(colors.get(k));
        }
        avgColor = avgColor.reduce(colors.size());

        if(depth == 0) // this is the max possible level of recursion
            return avgColor;

        // If the color of the first sample is very different from the average color
        // we will continue to sample in the upper right sub-pixel
        if(Math.abs(avgColor.diff(colors.get(0))) > MAX_DIF){
            newPc = new Point(pc.getXyz().getD1() +Rx/4, pc.getXyz().getD2() + Ry/4, pc.getXyz().getD3());;
            colors.set(0, calcColor_ASS(Rx/2, Ry/2,depth-1,  newPc));
        }

        // If the color of the second sample is very different from the average color
        // we will continue to sample in the lower right sub-pixel
        if(Math.abs(avgColor.diff(colors.get(1))) > MAX_DIF){
            newPc = new Point(pc.getXyz().getD1() +Rx/4, pc.getXyz().getD2() - Ry/4, pc.getXyz().getD3());;
            colors.set(1, calcColor_ASS(Rx/2, Ry/2,depth-1,  newPc));
        }

        // If the color of the third sample is very different from the average color
        // we will continue to sample in the upper left sub-pixel
        if(Math.abs(avgColor.diff(colors.get(2))) > MAX_DIF){
            newPc = new Point(pc.getXyz().getD1() -Rx/4, pc.getXyz().getD2() + Ry/4, pc.getXyz().getD3());;
            colors.set(2, calcColor_ASS(Rx/2, Ry/2,depth-1,  newPc));
        }

        // If the color of the fourth sample is very different from the average color
        // we will continue to sample in the lower left sub-pixel
        if(Math.abs(avgColor.diff(colors.get(3))) > MAX_DIF){
            newPc = new Point(pc.getXyz().getD1() -Rx/4, pc.getXyz().getD2() - Ry/4, pc.getXyz().getD3());;
            colors.set(3, calcColor_ASS( Rx/2, Ry/2, depth-1,  newPc));
        }

        // calculating the average color of the samples
        avgColor = new Color(0,0,0);
        for(int k = 0; k < colors.size(); k++){
            avgColor = avgColor.add(colors.get(k));
        }
        avgColor = avgColor.reduce(colors.size());
        return avgColor;

    }


    //------------------------------------------ additional functions -----------------------------------------------------
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
            // using in multi threading for acceleration of performances

            if(isDepthOfField){ // depth of field
                Pixel.initialize(nY, nX, Pixel.printInterval);
                IntStream.range(0, nY).parallel().forEach(i -> {
                    IntStream.range(0, nX).parallel().forEach(j -> {
                        List<Ray> rays = constructRaysForDepthOfField(constructRay( nX,  nY, j,  i));
                        imageWriter.writePixel(j, i, calcColor(rays));
                        Pixel.pixelDone();
                        Pixel.printPixel();
                    });
                });
            }
            else if(is_ASS){ // adaptive super sampling
                Pixel.initialize(nY, nX, Pixel.printInterval);
                IntStream.range(0, nY).parallel().forEach(i -> {
                    IntStream.range(0, nX).parallel().forEach(j -> {
                        imageWriter.writePixel(j, i, calcColor_ASS(nX, nY, j, i));
                        Pixel.pixelDone();
                        Pixel.printPixel();
                    });
                });
            }
            else{ // anti aliasing
                Pixel.initialize(nY, nX, Pixel.printInterval);
                IntStream.range(0, nY).parallel().forEach(i -> {
                    IntStream.range(0, nX).parallel().forEach(j -> {
                        List<Ray> rays = constructRaysForAntiAliasing(nX,nY,j,i);
                        imageWriter.writePixel(j, i, calcColor(rays));
                        Pixel.pixelDone();
                        Pixel.printPixel();
                    });
                });
            }
        }

        return this;
    }

    /**
     * The function calcColor is help function used for calculating the color of pixel
     * according to the average color of the rays which are being sent through
     * the pixel.
     * @param rays - List of rays through pixel
     * @return Color - the average color of the rays
     */
    private Color calcColor(List<Ray> rays) {
        Color avgColor = new Color(0, 0, 0);

        for (Ray ray : rays) {
            avgColor = avgColor.add(rayTracer.traceRay(ray));
        }

        avgColor = avgColor.reduce(rays.size());
        return avgColor;
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
