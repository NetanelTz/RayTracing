package geometries;

import java.util.List;

import elements.Material;
import primitives.*;

import static primitives.Util.*;

import java.util.Arrays;
import java.util.Objects;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;


    public Polygon(Color emission, Material material, Point3D... vertices) {
        super(emission, material);
        setVertices(vertices);
        createBox();
    }

    public Polygon(Point3D... vertices) {
        super();
        setVertices(vertices);
        createBox();
    }

    public Polygon(Color emission, Point3D... vertices) {

        super(emission);
        setVertices(vertices);
        createBox();

    }

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <p>
     *                                  Less than 3 vertices
     *                                  Consequent vertices are in the same
     *                                  point
     *                                  The vertices are not in the same
     *                                  plane
     *                                  The order of vertices is not according
     *                                  to edge path
     *                                  Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  The polygon is concave (not convex
     */
    private void setVertices(Point3D[] vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = Arrays.asList(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }


    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }

    /**
     * implementation for findIntersections
     *
     * @param ray ray to find intersect
     * @return List of GeoPoint
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if(!_box.inBox(ray))
            return null;
        GeoPoint p;
        try {
            p = _plane.findIntersections(ray, maxDistance).get(0);
            if (p == null) return null;
        } catch (Exception e) {//the initial point is on plane
            return null;
        }
        // changing to an array so it will be faster to get one of them, like vertices[1] and vertices[3].
        Point3D[] vertices = new Point3D[_vertices.size()];
        vertices = _vertices.toArray(vertices);
        Vector rn = ray.getVec();
        Point3D rp = ray.getPoint();
        // getting the first and second vectors
        try {

            Vector vec1 = rp.subtract(vertices[vertices.length - 1]);
            Vector vec2 = rp.subtract(vertices[0]);

            if (isZero(vec1.crossProduct(vec2).normalized().dotProduct(rn)))
                return null;
            boolean flag = vec1.crossProduct(vec2).normalized().dotProduct(rn) > 0;

            for (int i = 1; i < vertices.length; ++i) {
                vec1 = vec2;
                vec2 = rp.subtract(vertices[i]);
                if ((isZero(vec1.crossProduct(vec2).normalized().dotProduct(rn))) ||
                        (flag != vec1.crossProduct(vec2).normalized().dotProduct(rn) > 0)) {
                    return null;
                }
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        return List.of(new GeoPoint(this, p.getPoint()));

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        return Objects.equals(_vertices, polygon._vertices) &&
                Objects.equals(_plane, polygon._plane);
    }


    @Override
    public void createBox() {
        double _minX = _vertices.get(0).getX();
        double _maxX = _vertices.get(0).getX();
        double _minY = _vertices.get(0).getY();
        double _maxY = _vertices.get(0).getY();
        double _minZ = _vertices.get(0).getZ();
        double _maxZ = _vertices.get(0).getZ();
        for (Point3D p : this._vertices) {
            if (_maxX < p.getX())
                _maxX = p.getX();
            if (_minX > p.getX())
                _minX = p.getX();
            if (_maxY < p.getY())
                _maxY = p.getY();
            if (_minY > p.getY())
                _minY = p.getY();
            if (_maxZ < p.getZ())
                _maxZ = p.getZ();
            if (_minZ > p.getZ())
                _minZ = p.getZ();

        }
        _box = new Box(_minX,
                _maxX,
                _minY,
                _maxY,
                _minZ,
                _maxZ);
    }


}