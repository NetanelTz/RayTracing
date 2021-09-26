package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * test class for cylinder
 *
 * @author Noam and Netanel
 */

public class CylinderTest {

    /**
     * Test method for
     * {@link geometries.Cylinder#Cylinder(Ray, double, double)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // checking radius < 0 exception
        try {
            Cylinder t = new Cylinder(new Ray(new Vector(1, 1, 1), new Point3D(0, 0, 0)), -2, 3);
            fail("didnt catch exception r<0");
        } catch (IllegalArgumentException e) {
        }
        // checking hight < 0 exception
        try {
            Cylinder t = new Cylinder(new Ray(new Vector(1, 1, 1), new Point3D(0, 0, 0)), 2, -3);
            fail("didnt catch exception h<0");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        Cylinder c = new Cylinder(new Ray(new Vector(0, 0, 1), new Point3D(0, 0, 0)), 1, 2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: the middle
        assertEquals("Bad normal to cylinder mid", new Vector(1, 0, 0), c.getNormal(new Point3D(1, 0, 1.5)));
        //        // TC02: the top
        assertEquals("Bad normal to cylinder top", new Vector(0, 0, 1), c.getNormal(new Point3D(-0.5, 0.5, 2)));
        // TC03: the bottom
        assertEquals("Bad normal to cylinder bot", new Vector(0, 0, 1), c.getNormal(new Point3D(0.5, -0.7, 0)));

        // =============== Boundary Values Tests ==================
        //test top boundary
        assertEquals("Bad normal to cylinder boundary top", new Vector(0, 0, 1), c.getNormal(new Point3D(1, 1, 2)));
        //test bot boundary
        assertEquals("Bad normal to cylinder boundary bot", new Vector(0, 0, 1), c.getNormal(new Point3D(-1, -1, 0)));
    }

}