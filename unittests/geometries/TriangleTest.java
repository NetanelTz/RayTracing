package geometries;

import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Testing Plane
 *
 * @author Noam and Netanel
 */

public class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle pl = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));

    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

        Triangle tr = new Triangle(new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(0, 0, -2));


        // ============ Equivalence Partitions Tests ==============

        // TC01: ray is inside the triangle
        Ray r1 = new Ray(new Vector(0, 0, 1), new Point3D(0.25, 0.25, -4));
        try {
            Intersectable.GeoPoint p1 = tr.findIntersections(r1).get(0);
            assertEquals("triangle return the wrong point", new Point3D(0.25, 0.25, -1), p1);
        } catch (Exception e) {
            fail("didn't found a point");
        }
        // TC02: ray is outside the triangle and against its edge
        Ray r2 = new Ray(new Vector(0, 0, 1), new Point3D(-0.5, -0.5, 2));
        assertEquals("triangle return a point", null, tr.findIntersections(r2));
        // TC03: ray is outside the triangle and against its vertex
        Ray r3 = new Ray(new Vector(-0.5, -0.5, 0), new Point3D(0, 0, -3));
        assertEquals("triangle return a point", null, tr.findIntersections(r3));

        // =============== Boundary Values Tests ==================

        // TC04: ray is in the edge
        Ray r4 = new Ray(new Vector(0, 0, 1), new Point3D(0.5, 0.5, -1));
        assertEquals("triangle return a point", null, tr.findIntersections(r4));
        // TC05: ray is in the vertex
        Ray r5 = new Ray(new Vector(0, 1, 0), new Point3D(0, 5, -2));
        assertEquals("triangle return a point", null, tr.findIntersections(r5));
        // TC06: ray is in the edge continuation
        Ray r6 = new Ray(new Vector(0, 0, 1), new Point3D(2, -1, -3));
        assertEquals("triangle return a point", null, tr.findIntersections(r6));

    }
}