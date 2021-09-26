package primitives;

/**
 * class for ray - contain a vector for direction and point
 *
 * @author Noam and Netanel
 */
public class Ray {
    final Vector _direction;
    final Point3D _p;

    /**
     * constructor with direction vector and point
     *
     * @param _direction direction of ray
     * @param _p         initial start
     */
    public Ray(Vector _direction, Point3D _p) {
        this._p = new Point3D(_p);
        this._direction = new Vector(_direction.normalize());
    }

    /**
     * copy constructor
     *
     * @param _ray ray to copy
     */
    public Ray(Ray _ray) {
        this._p = new Point3D(_ray._p);
        this._direction = new Vector(_ray._direction);
    }

    /**
     * return the direction vector
     *
     * @return vector
     */
    public Vector getVec() {
        return _direction;
    }

    /**
     * return the initial point
     *
     * @return point
     */
    public Point3D getPoint() {
        return _p;
    }

    /**
     * override equal's function
     *
     * @param o object
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _direction.equals(ray._direction) && _p.equals(ray._p);
    }

    /**
     * override toString's function
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Start point: " + _p.toString() + " Direction: " + _direction.toString();
    }

    /**
     * return P = P0 + t*v
     * multiply the vector of ray with scalar and return the new point
     *
     * @param t is the scalar to mul the vec to reach new point
     * @return new Point3D
     */
    public Point3D getTargetPoint(double t) {
        Point3D p = new Point3D(_p.add(this.getVec().scale(t)));
        return p;
    }
}
