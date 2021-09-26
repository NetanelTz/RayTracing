package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


import static org.junit.Assert.*;

/**
 * test class for Geometries class
 *
 * @author Noam and Netanel
 */
public class GeometriesTest {
    /**
     * Test method for { @Link geometries.Geometries#add }
     */
    @Test
    public void add() {
        Geometries listGeo = new Geometries();
        listGeo.add(new Plane(new Vector(1, 1, 1), new Point3D(1, 1, 1)), new Sphere(1, new Point3D(1, 0, 0)),
                new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0)));
        assertEquals("The function does not add properly", 3, listGeo.getSize());
    }

    /**
     * Test method fo
     * { @Link geometries.Geometries#Geometries }
     */
    @Test
    public void Geometries() {
        Geometries listGeo = new Geometries((new Plane(new Vector(1, 1, 1), new Point3D(1, 1, 1))), new Sphere(1, new Point3D(1, 0, 0)),
                new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0)));
        assertEquals("The CTOR does not construct properly", 3, listGeo.getSize());
        Geometries listGeo2 = new Geometries();
        assertEquals("The CTOR does not construct properly", 0, listGeo2.getSize());

    }

    /**
     * Test method for { @Link geometries.Geometries#findIntersections(primitives.Ray) }
     */
    @Test
    public void findIntersections() {
        Geometries listGeo = new Geometries();
        listGeo.add(new Plane(new Vector(1, 1, 1), new Point3D(1, 1, 1)), new Sphere(1, new Point3D(1, 0, 0)),
                new Triangle(new Point3D(-3, 0, 0), new Point3D(0, 2, 0), new Point3D(0, 0, 3)));
        // ============ Equivalence Partitions Tests ==============
        //TC00 check intersection with part of geo
        assertEquals("Wrong number of intersections", 3,
                listGeo.findIntersections(new Ray(new Vector(1, 0, 0), new Point3D(-1, 0, 0))).size());
        // =============== Boundary Values Tests ==================
        //TC10 empty collection of geo
        assertEquals("There is an intersection in empty collection", null,
                new Geometries().findIntersections(new Ray(new Vector(1, 0, 0), new Point3D(-1, 0, 0))));

        //TC11 zero intersections
        assertEquals("Wrong number of intersections", null,
                listGeo.findIntersections(new Ray(new Vector(-6.5, 9, 0), new Point3D(5, 0, 0))));

        //TC12 the ray intersect all the geo in collection
        assertEquals("Wrong number of intersections", 4,
                listGeo.findIntersections(new Ray(new Vector(4, 0, -4), new Point3D(-4, 0.5, 4))).size());

        //TC13 only one geo intersect
        assertEquals("Wrong number of intersections", 1,
                listGeo.findIntersections(new Ray(new Vector(0, 2, 0), new Point3D(2.5, 0, 0))).size());

    }
}