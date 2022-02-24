package geometries;

import primitives.*;

public class Plane implements Geometry {
    private Point q0;
    private Vector normal;

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        if(normal.length() == 1)
            this.normal = normal;
        else
            this.normal = normal.normalize();
    }
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        this.normal = null; //נממש בהמשך
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    @Override
    public Vector getNormal(Point p){
        return this.normal;
    }
}
