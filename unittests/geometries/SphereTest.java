package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import javax.swing.*;
import java.util.List;


import static org.junit.Assert.*;

/**
 * class for tube
 *
 * @author Noam and Netanel
 */

public class SphereTest {
    /**
     * Test method for
     * {@link geometries.Sphere #(Point3D, double)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // checking radius < 0 exception
        try {
            Sphere s = new Sphere(-2, new Point3D(0, 0, 0));
            fail("didnt catch exception r<0");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere s = new Sphere(2, new Point3D(1, -2, 3));
        assertEquals("Bad normal to sphere", new Vector(1, 0, 0), s.getNormal(new Point3D(3, -2, 3)));
    }


    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {


        Sphere sphere = new Sphere(1, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(1, 1, 0), new Point3D(-1, 0, 0))));
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Intersectable.GeoPoint> result = sphere.findIntersections(new Ray(new Vector(3, 1, 0),
                new Point3D(-1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getPoint().getX() > result.get(1).getPoint().getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere p1", p1, result.get(0).getPoint());
        assertEquals("Ray crosses sphere p2", p2, result.get(1).getPoint());

        // TC03: Ray starts inside the sphere (1 point)
        Point3D p = new Point3D(1.8603796100280632, 0.36037961002806324, 0.36037961002806324);
        result = sphere.findIntersections(new Ray(new Vector(2, 2, 2), new Point3D(1.5, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray miss the sphere", p, result.get(0).getPoint());


        // TC04: Ray starts after the sphere (0 points)
        assertEquals("Ray's line in the sphere", null,
                sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point3D(2.5, 0.5, 0))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals("Ray's line dosen't across the sphere(TC11)", List.of(new Intersectable.GeoPoint(sphere, new Point3D(1.9999999999999998, 0, 0))),
                sphere.findIntersections(new Ray(new Vector(2, -2, 0), new Point3D(1, 1, 0))));

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray's line goes inside the sphere", null,
                sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point3D(1.5, Math.sqrt(3) / 2, 0))));

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point3D(0.5527864045000421, 0.8944271909999159, 0);
        p2 = new Point3D(1.4472135954999579, -0.894427190999916, 0);
        result = sphere.findIntersections(new Ray(new Vector(0.5, -1, 0), new Point3D(0.5, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getPoint().getX() > result.get(1).getPoint().getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray not crosses sphere", p1, result.get(0).getPoint());
        assertEquals("Ray not crosses sphere", p2, result.get(1).getPoint());

        // TC14: Ray starts at sphere and goes inside (1 points)
        p = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Vector(0, -1, 0), new Point3D(1, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray does'nt hit the sphere", p,  result.get(0).getPoint());

        // TC15: Ray starts inside (1 points)
        p = new Point3D(1.9114378277661477, 0.4114378277661476, 0);
        result = sphere.findIntersections(new Ray(new Vector(2, 2, 0), new Point3D(1.5, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray does'nt hit the sphere",  p,  result.get(0).getPoint());

        // TC16: Ray starts at the center (1 points)
        p = new Point3D(1.7071067811865475, 0.7071067811865475, 0);
        result = sphere.findIntersections(new Ray(new Vector(2, 2, 0), new Point3D(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray does'nt hit the sphere",  p,  result.get(0).getPoint());

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray's line hit the sphere", null,
                sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point3D(1, 1, 0))));

        // TC18: Ray starts after sphere (0 points)
        assertEquals("Ray's line hit the sphere", null,
                sphere.findIntersections(new Ray(new Vector(0, 2, 0), new Point3D(1, 2, 0))));
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        p = new Point3D(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Vector(-2, 0, 0), new Point3D(3, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray does'nt hit the sphere",  p,  result.get(0).getPoint());

        // TC20: Ray starts at the tangent point
        assertEquals("Ray's line hit the sphere", null,
                sphere.findIntersections(new Ray(new Vector(1, 1, 0), new Point3D(1, 1, 0))));

        // TC21: Ray starts after the tangent point
        assertEquals("Ray's line hit the sphere", null,
                sphere.findIntersections(new Ray(new Vector(0.1, 0.1, 0), new Point3D(1.1, 1.1, 0))));


        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertEquals("Ray's line hit the sphere", null,
                sphere.findIntersections(new Ray(new Vector(0, -1.5, 0), new Point3D(2.5, 0, 0))));
    }


}