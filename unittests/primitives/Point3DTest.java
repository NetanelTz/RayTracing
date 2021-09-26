package primitives;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for primitives.Point3D class
 *
 * @author Noam and Netanel
 */
public class Point3DTest {
    /**
     * Test method for
     * {@link primitives.Point3D#subtract(Point3D)}.
     */
    @Test
    public void testSubtract() {
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(-2, 2, 0);

        // ============ Equivalence Partitions Tests ==============
        Vector v1 = p1.subtract(p2);
        assertEquals("not the right vector", v1, new Vector(3, -1, 1));
        // test if subtract gives the right answer.

        // =============== Boundary Values Tests ==================

        // test if it catch vector 0,0,0.
        try {
            p1.subtract(p1);
            fail("didn't catch a  vector 0,0,0 exception");
        } catch (IllegalArgumentException e) {
        }
    }


    @Test
    public void testAdd() {
        Point3D p1 = new Point3D(1, 1, 1.5);
        Vector v1 = new Vector(-2, 2, 0);

        // ============ Equivalence Partitions Tests ==============
        Point3D p2 = p1.add(v1);
        assertEquals("not the right point", p2, new Point3D(-1, 3, 1.5));
        // test if add gives the right answer.

    }

    @Test
    public void testDistanceSquared() {
        Point3D p1 = new Point3D(4, 4, 4);
        Point3D p2 = new Point3D(-4, -4, -4);
        // ============ Equivalence Partitions Tests ==============
        // test if distanceSquared gives the right answer.
        assertEquals("not the right distance.", p1.distanceSquared(p2), 192, 0);
    }

    @Test
    public void testDistance() {
        Point3D p1 = new Point3D(-2, 9, 1);
        Point3D p2 = new Point3D(-4, 7, 0);
        // ============ Equivalence Partitions Tests ==============
        // test if distanceSquared gives the right answer.
        assertEquals("not the right distance.", p1.distance(p2), 3, 0);

    }

}