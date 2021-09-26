package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class for cylinder that extends tube class
 *
 * @author Noam and Netanel
 */
public class Cylinder extends Tube {
    private final double _height;

    /**
     * ctor with height, radius and ray, using father ctor
     *
     * @param h  height of cylinder
     * @param r  radius of cylinder
     * @param aR axis ray of cylinder
     */
    public Cylinder(Ray aR, double r, double h) {
        super(aR, r);
        if (h < 0.0 || isZero(h))//check the correct of height
            throw new IllegalArgumentException("Height can't be negative");
        this._height = h;
    }

    /**
     * override toString's function
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height + " "
                + super.toString() + " " +
                '}';
    }

    /**
     * override get normal method
     *
     * @param point point to get normal from it
     * @return Vector getNormal
     */

    @Override
    public Vector getNormal(Point3D point) {
        Point3D o = _axisRay.getPoint();
        Vector v = _axisRay.getVec();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(point.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        return super.findIntersections(ray, maxDistance);
    }
}
