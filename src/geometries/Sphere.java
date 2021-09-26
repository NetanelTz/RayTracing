package geometries;

import elements.Material;
import primitives.*;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

/**
 * class for sphere
 * that extends radial geometry
 *
 * @author Noam and Netanel
 */
public class Sphere extends RadialGeometry {
    private final Point3D _center;

    /**
     * ctor with 3 parameters
     *
     * @param emission emission of sphere
     * @param material material of sphere
     * @param r        radius of sphere
     * @param c        center of sphere
     */

    public Sphere(Color emission, Material material, double r, Point3D c) {
        super(emission, material, r);
        this._center = new Point3D(c);
        createBox();
    }

    /**
     * ctor
     *
     * @param emission emission of sphere
     * @param r        radius of sphere
     * @param c        center of sphere
     */
    public Sphere(Color emission, double r, Point3D c) {
        super(emission, r);
        this._center = new Point3D(c);
        createBox();
    }

    /**
     * ctor with parameters
     * using father ctor
     *
     * @param c center of sphere
     * @param r radius of sphere
     */
    public Sphere(double r, Point3D c) {
        super(r);
        this._center = new Point3D(c);
        createBox();
    }


    /**
     * get center point
     *
     * @return center of sphere
     */
    Point3D get_center() {
        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * get the normal vector
     *
     * @return Vector
     */
    @Override
    public Vector getNormal(Point3D p) {
        Vector normal = new Vector(p.subtract(_center)).normalize();
        return normal;
    }

    /**
     * implementation for findIntersections
     * according lab slide show
     *
     * @param ray ray
     * @return List of Point3D
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if(!_box.inBox(ray))
            return null;
        Point3D p0 = ray.getPoint();
        Vector v = ray.getVec();
        Vector u;
        try {
            u = _center.subtract(p0); // try u=o-p0
        } catch (IllegalArgumentException e) {//if it throws exception the point is in the sphere
            return alignZero(this._radius - maxDistance) <= 0 ? List.of(new GeoPoint(this, (ray.getTargetPoint(this._radius)))) : null;//return the point on the casing
        }
        double tm = alignZero(v.dotProduct(u));//tm=v*u
        double d = alignZero(u.lengthSquared() - (tm * tm));// d=u^2-tm^2
        double th = alignZero(_radius * _radius - d);// th=r^2-d^2
        if (th < 0) //if (d>r) there are no intersections
            return null;
        if (th == 0)
            return alignZero(tm - maxDistance) <= 0 ? List.of(new GeoPoint(this, ray.getTargetPoint(alignZero(tm)))) : null;
        double t1 = alignZero(tm + Math.sqrt(th));
        double t2 = alignZero(tm - Math.sqrt(th));
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) {
            if (alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0)
                return List.of(new GeoPoint(this, ray.getTargetPoint(t1)), new GeoPoint(this, ray.getTargetPoint(t2)));
            if (alignZero(t1 - maxDistance) <= 0)
                return List.of(new GeoPoint(this, ray.getTargetPoint(t1)));
            if (alignZero(t2 - maxDistance) <= 0)
                return List.of(new GeoPoint(this, ray.getTargetPoint(t1)));
        }
        if (t1 > 0)
            return alignZero(t1 - maxDistance) <= 0 ? List.of(new GeoPoint(this, ray.getTargetPoint(t1))) : null;
        else
            return alignZero(t2 - maxDistance) <= 0 ? List.of(new GeoPoint(this, ray.getTargetPoint(t2))) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Objects.equals(_center, sphere._center) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_center);
    }

    @Override
    public void createBox() {
        _box = new Box(this._center.getX()-this._radius,this._center.getX()+this._radius,
                this._center.getY()-this._radius,this._center.getY()+this._radius,
                this._center.getZ()-this._radius,this._center.getZ()+this._radius);
    }


}
