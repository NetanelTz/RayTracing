package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * interface for findIntsersections
 *
 * @author Noam and Netanel
 */
public interface Intersectable {

    void createBox();

   // void BVH(int deep);

    Box getBox();

    class Box {
        double _minX;
        double _maxX;
        double _minY;
        double _maxY;
        double _minZ;
        double _maxZ;
        double _midX;
        double _midY;
        double _midZ;

        public Box() {
            this(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
                    Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }

        public Box(double _minX, double _maxX, double _minY, double _maxY, double _minZ, double _maxZ) {
            this._minX = _minX;
            this._maxX = _maxX;
            this._minY = _minY;
            this._maxY = _maxY;
            this._minZ = _minZ;
            this._maxZ = _maxZ;
            this._midX=_maxX+_minX/2;
            this._midY=_maxY+_minY/2;
            this._midZ=_maxZ+_minZ/2;
        }

        public double get_minX() {
            return _minX;
        }

        public void set_minX(double _minX) {
            this._minX = _minX;
        }

        public double get_maxX() {
            return _maxX;
        }

        public void set_maxX(double _maxX) {
            this._maxX = _maxX;
        }

        public double get_minY() {
            return _minY;
        }

        public void set_minY(double _minY) {
            this._minY = _minY;
        }

        public double get_maxY() {
            return _maxY;
        }

        public void set_maxY(double _maxY) {
            this._maxY = _maxY;
        }

        public double get_minZ() {
            return _minZ;
        }

        public void set_minZ(double _minZ) {
            this._minZ = _minZ;
        }

        public double get_maxZ() {
            return _maxZ;
        }

        public void set_maxZ(double _maxZ) {
            this._maxZ = _maxZ;
        }

        public boolean inBox(Ray ray) {
            double tmin = (_minX - ray.getPoint().getX()) / ray.getVec().getHead().getX();
            double tmax = (_maxX - ray.getPoint().getX()) / ray.getVec().getHead().getX();
            if (tmin > tmax) {
                tmax = swap(tmin, tmin = tmax);
            }
            double tymin = (_minY - ray.getPoint().getY()) / ray.getVec().getHead().getY();
            double tymax = (_maxY - ray.getPoint().getY()) / ray.getVec().getHead().getY();
            if (tymin > tymax)
                tymax = swap(tymin, tymin = tymax);
            if (tmin > tymax || tymin > tmax)
                return false;
            if (tymin > tmin)
                tmin = tymin;

            if (tymax < tmax)
                tmax = tymax;
            double tzmin = (_minZ - ray.getPoint().getZ()) / ray.getVec().getHead().getZ();
            double tzmax = (_maxZ - ray.getPoint().getZ()) / ray.getVec().getHead().getZ();
            if (tzmin > tzmax)
                tzmax = swap(tzmin, tzmin = tzmax);
            if ((tmin > tzmax) || (tzmin > tmax))
                return false;
            return true;
        }

        public double swap(double a, double b) {
            return a;
        }

    }

    /**
     * GeoPoint is tuple of references
     * to a specific geometry and its intersection point
     *
     * @author Noam and Netanel
     */
    public static class GeoPoint {
        protected Geometry geometry;
        protected Point3D point;

        /**
         * override equal function
         *
         * @param o object of class
         * @return true or false
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) &&
                    Objects.equals(point, geoPoint.point);
        }

        /**
         * ctor of this class
         *
         * @param _geometry geo shape
         * @param pt        point of intersect
         */
        public GeoPoint(Geometry _geometry, Point3D pt) {
            this.geometry = _geometry;
            point = pt;
        }

        /**
         * get the specific geo
         *
         * @return Geometry
         */
        public Geometry getGeometry() {
            return geometry;
        }

        /**
         * get the point of intersect
         *
         * @return point 3d
         */
        public Point3D getPoint() {
            return point;
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * @param ray ray pointing toward a Geomtry
     * @return List of GeoPoint return values
     */
    default List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    List<GeoPoint> findIntersections(Ray ray, double maxDistance);

}
