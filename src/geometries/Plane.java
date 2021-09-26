package geometries;

import elements.Material;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static primitives.Util.alignZero;

/**
 * class to define plane
 *
 * @author Noam and Netanel
 */
public class Plane extends flatGeometry {
    Vector normal;
    Point3D _p;

    /**
     * ctor with four parameters
     *
     * @param emission emission of plane
     * @param material material of plane
     * @param normal   normal vec of plane
     * @param _p       point of plane
     */
    public Plane(Color emission, Material material, Vector normal, Point3D _p) {
        super(emission, material);
        this.normal = normal.normalize();
        this._p = _p;
        createBox();
    }

    /**
     * ctor with five parameters.
     * 3 points to create the normal
     *
     * @param emission emission of plane
     * @param material material of plane
     * @param p1 point 3d
     * @param p2 point 3d
     * @param p3 point 3d
     */
    public Plane(Color emission, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emission, material);
        setNormal(p1, p2, p3);
        createBox();
    }

    /**
     * ctor just with emission and 3 points to create the normal
     *
     * @param emission emission of plane
     * @param p1       point 3d
     * @param p2       point 3d
     * @param p3       point 3d
     */
    public Plane(Color emission, Point3D p1, Point3D p2, Point3D p3) {
        super(emission);
        setNormal(p1, p2, p3);
        createBox();
    }

    /**
     * ctor just with points and father's ctor
     *
     * @param p1 point 3d
     * @param p2 point 3d
     * @param p3 point 3d
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        super();
        setNormal(p1, p2, p3);
        createBox();
    }

    /**
     * basic ctor with 3 parameters
     *
     * @param emission emission of plane
     * @param normal   normal of plane
     * @param _p       point of plane
     */
    public Plane(Color emission, Vector normal, Point3D _p) {
        super(emission);
        this.normal = normal.normalize();
        this._p = _p;
        createBox();
    }

    /**
     * ctor with 2 parameters
     *
     * @param normal normal vector to plane
     * @param _p     point in plane
     */
    public Plane(Vector normal, Point3D _p) {
        super();
        this.normal = normal.normalize();
        this._p = _p;
        createBox();
    }

    /**
     * set the normal of plane by subtracting points, create
     * the vector and do cross product
     *
     * @param p1 point 3d
     * @param p2 point 3d
     * @param p3 point 3d
     */
    private void setNormal(Point3D p1, Point3D p2, Point3D p3) {
        try {
            Vector tmp1 = p1.subtract(p2);
            Vector tmp2 = p1.subtract(p3);
            normal = tmp2.crossProduct(tmp1).normalized();
            _p = p2;
        } catch (IllegalArgumentException a) {//if one of the vectors is 0
            throw new IllegalArgumentException("cant create a plane with 2 dots on the same line");
        }
    }


    @Override
    public String toString() {
        return "Plane:" +
                "n: " + normal.toString() +
                "| p: " + _p.toString();

    }

    /**
     * get the normal of plane
     *
     * @return vector
     */
    public Vector getNormal() {
        return getNormal(_p);
    }

    /**
     * return point of plane
     *
     * @return Point3D
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * get the normal vector
     *
     * @return Vector
     */
    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

    /**
     * implementation for findIntersections
     * according lab slide shows
     *
     * @param ray ray to find intersect
     * @return List of GeoPoint
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if(!_box.inBox(ray))
            return null;
        Vector p;
        try {
            p = _p.subtract(ray.getPoint());
        } catch (IllegalArgumentException e) {
            return null; //the point is on the plane
        }
        try {
            double t = alignZero((normal.dotProduct(p) / alignZero(normal.dotProduct(ray.getVec()))));
            if (alignZero(t) <= 0) return null;
            return alignZero(t - maxDistance) <= 0 ? List.of(new GeoPoint(this, ray.getTargetPoint(t))) : null;
        } catch (Exception e) {//in case divide by zero
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Objects.equals(normal, plane.normal) &&
                Objects.equals(_p, plane._p);
    }

    @Override
    public void createBox() {
        _box=new Box();
    }
}