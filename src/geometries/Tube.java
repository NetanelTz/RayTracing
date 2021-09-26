package geometries;

import elements.Material;
import primitives.*;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class for tube
 * tube it's actually an infinite cylinder
 *
 * @author Noam and Netanel
 */
public class Tube extends RadialGeometry {

    protected final Ray _axisRay;

    /**
     * Ctor
     *
     * @param emission emission of tube
     * @param material material of tube
     * @param aR       axis ray of tube
     * @param r        radius of tube
     */
    public Tube(Color emission, Material material, Ray aR, double r) {
        super(emission, material, r);
        this._axisRay = new Ray(aR);
        createBox();
    }

    public Tube(Color emission, Ray aR, double r) {
        super(emission, r);
        this._axisRay = new Ray(aR);
        createBox();
    }

    public Tube(Ray aR, double r) {
        super(r);
        this._axisRay = new Ray(aR);
        createBox();
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * get the normal vector
     *
     * @return Vector
     */
    @Override
    public Vector getNormal(Point3D point) {
        double t = _axisRay.getVec().dotProduct(point.subtract(_axisRay.getPoint()));
        if (Util.isZero(t)) {
            Vector normal = point.subtract(_axisRay.getPoint());
            return normal;
        }
        Point3D p = _axisRay.getPoint().add(_axisRay.getVec().scale(t));
        Vector normal = point.subtract(p).normalize();
        return normal;
    }

    /**
     * override findIntersection
     *
     * @param ray         ray to find the intersect
     * @param maxDistance maximum distance from initial start of ray to point
     * @return list of geo points
     */

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {

        Point3D P = ray.getPoint();
        Point3D _point = this._axisRay.getPoint();

        Vector V = ray.getVec();
        Vector Va = this._axisRay.getVec();
        try {
            V.crossProduct(Va);//check if they parallel so they not intersect
        } catch (IllegalArgumentException ex) {
            return null;
        }
        if (P.equals(_point)) _point = this._axisRay.getTargetPoint(-1);
        Vector Vb = new Vector(P.subtract(_point));

        double V_dot_Va = V.dotProduct(Va);
        double DeltaP_dot_Va = Vb.dotProduct(Va);
        Vector tempV1;
        Vector tempV2 = null;
        if (isZero(DeltaP_dot_Va))
            tempV2 = Vb;
        else if (!Vb.equals(Va.scale(DeltaP_dot_Va)))
            tempV2 = Vb.subtract(Va.scale(DeltaP_dot_Va));
        if (isZero(V_dot_Va))
            tempV1 = V;
        else
            tempV1 = V.subtract(Va.scale(V_dot_Va));

        double A = tempV2.dotProduct(tempV2);
        double B;
        double C;
        if (isZero(DeltaP_dot_Va) || !Vb.equals(Va.scale(DeltaP_dot_Va))) {
            B = 2 * tempV1.dotProduct(tempV2);
            C = tempV2.dotProduct(tempV2) - _radius * _radius;
        } else {
            B = 0;
            C = -_radius * _radius;
        }
        double desc = alignZero(B * B - 4 * A * C);

        if (desc < 0) {//No solution
            return null;
        }

        double t1 = alignZero((-B + Math.sqrt(desc)) / (2 * A)),
                t2 = alignZero((-B - Math.sqrt(desc)) / (2 * A));

        if (desc == 0) {//One solution
            if (-B / (2 * A) < 0) {
                return null;
            } else {
                return alignZero(-B / (2 * A)) <= 0 ? List.of(new GeoPoint(this, P.add(V.scale(-B / (2 * A))))) : null;
            }
        } else if (t1 < 0 && t2 < 0) {
            return null;
        } else if (t1 < 0 && t2 > 0) {
            return alignZero(-B / (2 * A)) <= 0 ? List.of(new GeoPoint(this, P.add(V.scale(t2)))) : null;
        } else if (t1 > 0 && t2 < 0) {
            return alignZero(-B / (2 * A)) <= 0 ? List.of(new GeoPoint(this, P.add(V.scale(t1)))) : null;
        } else if (alignZero(t1) <= 0 && alignZero(t2) <= 0) {
            return List.of(
                    new GeoPoint(this, P.add(V.scale(t1))),
                    new GeoPoint(this, P.add(V.scale(t2)))
            );
        } else if (alignZero(t1) <= 0) {
            return List.of(new GeoPoint(this, P.add(V.scale(t1))));
        } else if (alignZero(t2) <= 0) {
            return List.of(new GeoPoint(this, P.add(V.scale(t2))));
        } else return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tube tube = (Tube) o;
        return Objects.equals(_axisRay, tube._axisRay) && super.equals(o);
    }

    @Override
    public void createBox() {
        _box = new Box();
    }

}
