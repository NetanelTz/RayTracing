package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * class for tube
 *
 * @author Noam and Netanel
 */

public class TubeTest {


    /**
     * Test method for
     * {@link geometries.Tube#Tube(Ray, double)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // checking radius < 0 exception
        try {
            Tube t = new Tube(new Ray(new Vector(1, 1, 1), new Point3D(0, 0, 0)), -2);
            fail("didnt catch exception r<0");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube t = new Tube(new Ray(new Vector(0, 0, 1), new Point3D(3, 4, -2)), 1);
        assertEquals("Bad normal to tube", new Vector(1, 0, 0), t.getNormal(new Point3D(4, 4, 0)));
    }

    @Test
    public void findIntsersections() {
    }
}