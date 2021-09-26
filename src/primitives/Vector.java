package primitives;

/**
 * class to define vector
 *
 * @author Noam and Netanel
 */
public class Vector {
    Point3D head;

    /**
     * ctor with point
     *
     * @param head initial start
     */
    public Vector(Point3D head) {
        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector size can't be zero");
        this.head = new Point3D(head);
    }

    /**
     * ctor with 3 numbers by calling another ctor
     *
     * @param x double
     * @param y double
     * @param z double
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    /**
     * ctor with 3 coordinate
     *
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x, y, z));
    }

    /**
     * ctor with vector
     *
     * @param head the initial of vec
     */
    public Vector(Vector head) {
        this(head.getHead());
    }

    /**
     * retrun  the head of vector
     *
     * @return point3D
     */
    public Point3D getHead() {
        return head;
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
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }

    /**
     * override toString's function
     *
     * @return string
     */
    @Override
    public String toString() {
        return head.toString();
    }

    /**
     * add vector and return new vector
     *
     * @param vec vector to add
     * @return new vector
     */
    public Vector add(Vector vec) {
        return new Vector(this.head.add(vec));
    }

    /**
     * subtract vector and return new vector
     *
     * @param vec vector to sub
     * @return new vector
     */
    public Vector subtract(Vector vec) {
        return this.head.subtract(vec.getHead());
    }

    public Vector(Point3D p1, Point3D p2) {
        this(p1.subtract(p2));
    }

    /**
     * multiply vector with number and return new vector
     *
     * @param s scalar to mul
     * @return new vector
     */
    public Vector scale(double s) {
        return new Vector(new Point3D(this.head.getX() * s, this.head.getY() * s, this.head.getZ() * s));
    }

    /**
     * multiply 2 vectors
     *
     * @param vec vec to mul
     * @return double of result
     */
    public double dotProduct(Vector vec) {
        return vec.getHead().getX() * this.getHead().getX() +
                vec.getHead().getY() * this.getHead().getY() +
                vec.getHead().getZ() * this.getHead().getZ();
    }

    /**
     * return new vector stands to 2 vectors
     *
     * @param vec vector to cross product
     * @return new vertical vector
     */
    public Vector crossProduct(Vector vec) {
        return new Vector(new Point3D(vec.getHead().getY() * this.getHead().getZ() - this.getHead().getY() * vec.getHead().getZ(),
                vec.getHead().getZ() * this.getHead().getX() - this.getHead().getZ() * vec.getHead().getX(),
                vec.getHead().getX() * this.getHead().getY() - this.getHead().getX() * vec.getHead().getY()));
    }

    /**
     * return the squared length of vector
     *
     * @return double
     */
    public double lengthSquared() {
        return this.getHead().distanceSquared(Point3D.ZERO);
    }

    /**
     * return the real length of vector
     *
     * @return double
     */
    public double length() {
        return this.getHead().distance(Point3D.ZERO);
    }

    /**
     * normalized the vector and return new one
     *
     * @return new vector
     */
    public Vector normalized() {
        double size = this.length();
        return new Vector(this.getHead().getX() / size, this.getHead().getY() / size, this.getHead().getZ() / size);
    }

    /**
     * normalize the vector itself
     *
     * @return the same vector
     */
    public Vector normalize() {
        this.head = this.normalized().getHead();
        return this;
    }
}
