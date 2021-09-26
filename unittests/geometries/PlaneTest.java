package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing Plane
 *
 * @author Noam and Netanel
 */
public class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Vector(0, 0, 1), new Point3D(0, 0, 0));

        assertEquals("Bad normal to plane", new Vector(0, 0, 1), pl.getNormal(new Point3D(0, 0, 0)));

    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */

    @Test
    public void findIntersections() {
        Plane plane = new Plane(new Vector(1, 1, 1), new Point3D(1, 1, 1));
        // ============ Equivalence Partitions Tests ==============
        //The Ray must be neither orthogonal nor parallel to the plane
        //TC01 Ray intersects the plane (1 point)
        Point3D p = new Point3D(1.333333333333333, 1.6666666666666667, 0);
        List<Intersectable.GeoPoint> result = plane.findIntersections(new Ray(new Vector(1, 2, 0), new Point3D(1, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray miss the plain", List.of(p), result);

        //TC02 Ray does not intersect the plane (0 point)
        assertEquals("Ray's line hit the plane", null,
                plane.findIntersections(new Ray(new Vector(0, 1, 0), new Point3D(0, 5, 0))));

        // =============== Boundary Values Tests ==================
        //**Group of Rays that parallel to the plane
        //TC100  ray included in the plane (0 point)
        assertEquals("Ray's line hit the plane", null,
                plane.findIntersections(new Ray(new Vector(0.5, 0.5, -1), new Point3D(1, 1, 1))));

        //TC101  ray not included in the plane (0 point)
        assertEquals("Ray's line hit the plane", null,
                plane.findIntersections(new Ray(new Vector(0.5, 0.5, -1), new Point3D(2, 2, 2))));

        //**Group of Rays that orthogonal to the plane
        //TC110 ray start at the plane (0 point)
        assertEquals("Ray's line hit the plane", null,
                plane.findIntersections(new Ray(new Vector(-1, -1, -1), new Point3D(1.5, 1.5, 0))));

        //TC111 ray start before the plane (1 point)
        p = new Point3D(1, 1, 1);
        result = plane.findIntersections(new Ray(new Vector(-1, -1, -1), new Point3D(2, 2, 2)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray miss the plain", List.of(p), result);

        //TC112 ray start after the plane (0 point)
        assertEquals("Ray's line hit the plane", null,
                plane.findIntersections(new Ray(new Vector(-1, -1, -1), new Point3D(-1, -1, -1))));

        //**Group of rays that begin at the plain but neither orthogonal nor parallel to the plain
        //TC120 ray start at the plain in Q point (0 point)
        assertEquals("Ray's line hit the plane", null,
                plane.findIntersections(new Ray(new Vector(1, 3, -1), new Point3D(1, 1, 1))));
        //TC121 ray start at the plain
        assertEquals("Ray's line hit the plane", null,
                plane.findIntersections(new Ray(new Vector(1, 3, -1), new Point3D(1.5, 1.5, 0))));
    }
}