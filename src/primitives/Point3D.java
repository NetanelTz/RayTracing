package primitives;

import java.util.Objects;

/**
 * class that define 3D point with 3 coordinates
 *
 * @author Noam and Netanel
 */
public class Point3D {
    final Coordinate x, y, z;
    static public final Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

    /**
     * ctor that get 3 numbers to initial point by making it to coordinate
     *
     * @param x x value of point with number
     * @param y y value of point with number
     * @param z z value of point with number
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /**
     * ctor that get 3 coordinates to initial point
     *
     * @param x x value of point with coordinate object
     * @param y y value of point with coordinate object
     * @param z z value of point with coordinate object
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x.get(), y.get(), z.get());
    }

    /**
     * ctor that get one 3D point to initial point
     *
     * @param point point in axis
     */
    public Point3D(Point3D point) {
        this(point.getX(), point.getY(), point.getZ());
    }

    /**
     * return the x value of point
     *
     * @return double of x point
     */
    public double getX() {
        return x.get();
    }

    /**
     * return the y value of point
     *
     * @return double of y point
     */
    public double getY() {
        return y.get();
    }

    /**
     * return the z value of point
     *
     * @return double of z point
     */
    public double getZ() {
        return z.get();
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
        Point3D point3D = (Point3D) o;
        return Objects.equals(x, point3D.x) &&
                Objects.equals(y, point3D.y) &&
                Objects.equals(z, point3D.z);
    }

    /**
     * override toString's function
     *
     * @return string
     */
    @Override
    public String toString() {
        return x.toString() + ", " + y.toString() + ", " + z.toString();
    }

    /**
     * subtract 2 points and return the vector
     *
     * @param point point to sub from another point
     * @return Vector
     */
    public Vector subtract(Point3D point) {
        return new Vector(new Point3D(this.getX() - point.getX(),
                this.getY() - point.getY(),
                this.getZ() - point.getZ()));
    }

    /**
     * add vector to point and return a new point
     *
     * @param point point to add to another point
     * @return the new point
     */
    public Point3D add(Vector point) {
        return new Point3D(this.getX() + point.getHead().getX(),
                this.getY() + point.getHead().getY(),
                this.getZ() + point.getHead().getZ());
    }

    /**
     * return the squared distance between 2 points
     *
     * @param point point that we need to find the dis
     * @return double of squared  dis
     */
    public double distanceSquared(Point3D point) {
        return (getX() - point.getX()) * (getX() - point.getX()) +
                (getY() - point.getY()) * (getY() - point.getY()) +
                (getZ() - point.getZ()) * (getZ() - point.getZ());

    }

    /**
     * return real distance between 2 points
     *
     * @param point point that we need to find the dis
     * @return double of  dis
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }
}
