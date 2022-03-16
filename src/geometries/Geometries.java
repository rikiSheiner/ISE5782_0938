package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of geometric shapes
 * @author Rivka Sheiner
 */
public class Geometries implements Intersectable{

    /**
     * The list of the geometric shapes
     */
    private List<Intersectable> geometries;

    /**
     * Geometries default constructor
     */
    public Geometries() {
        geometries = new LinkedList<>();
    }

    /**
     * Geometries parameters constructor
     * @param geometries the geometric shapes
     */
    public Geometries(Intersectable... geometries){
        this.geometries = List.of(geometries);
    }

    /**
     * This method adds new geometric shapes to the collection
     * @param geometries the shapes for adding
     */
    public void add(Intersectable... geometries){
        for(int i=0; i<geometries.length; i++) {
            this.geometries.add(geometries[i]);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        List<Point> allPoints = new LinkedList<>();
        List<Point> points;
        int counter = 0;
        for(Intersectable geo : geometries){
            points = geo.findIntersections(ray);
            if(points != null)
                for(Point p : points){
                    allPoints.add(p);
                    counter++;
                }
        }

        if(allPoints == null || counter == 0)
            return null;
        return allPoints;
    }
}
