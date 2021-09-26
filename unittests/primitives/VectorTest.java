package primitives;

import org.junit.Test;


import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for  primitives.Vector class
 *
 * @author Noam and Netanel
 */
public class VectorTest {
    /**
     * Test method for {@link Vector#Vector(double, double, double)}
     * Test method for {@link Vector#Vector(Coordinate, Coordinate, Coordinate)}
     * Test method for {@link Vector#Vector(Point3D)}
     * Test method for {@link Vector#Vector(Vector)}
     */
    @Test
    public void testCtorsVector() {
        // =============== Boundary Values Tests ==================
        //test if zero vector throws exception
        //TC0 for 3 doubles
        try {
            Vector vec = new Vector(0, 0, 0);
            fail("Constructor for zero vector with 3 doubles does not throw an exception");
        } catch (Exception e) {
        }
        //TC1 for point
        try {
            Vector vec = new Vector(new Point3D(0, 0, 0));
            fail("Constructor for zero vector with point does not throw an exception");
        } catch (Exception e) {
        }
        //TC2 for 3 coordinates
        try {
            Vector vec = new Vector(new Coordinate(0), new Coordinate(0), new Coordinate(0));
            fail("Constructor for zero vector with 3 coordinate does not throw an exception");
        } catch (Exception e) {
        }
        //TC3 for vector
        try {
            Vector vec = new Vector(new Vector(0, 0, 0));
            fail("Constructor for zero vector with vector does not throw an exception");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link Vector#getHead()}
     */
    @Test
    public void testGetHead() {
        Vector v1 = new Vector(1.0, 1.0, 1.0);
        // ============ Equivalence Partitions Tests ==============
        //  test if getHead return the right point
        assertEquals(new Point3D(1, 1, 1), v1.getHead());
    }

    /**
     * Test method for {@link Vector#equals(Object)}
     */
    @Test
    public void testEquals() {
        Vector v1 = new Vector(1.0, 1.0, 1.0);
        Vector v2 = new Vector(1.0, 1.0, 1.0);
        assertTrue("Equal() dosen't work correct", v1.equals(v2));
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}
     */
    @Test
    public void testAdd() {
        Vector v1 = new Vector(1.0, 1.0, 1.0);
        Vector v2 = new Vector(-1.0, -1.0, -1.5);
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = v1.add(v2);
        //test that add operation is correct
        assertEquals(new Vector(0.0, 0.0, -0.5), v3);
        // =============== Boundary Values Tests ==================
        //test if zero vector throws exception
        try {
            Vector v4 = new Vector(-1.0, -1.0, -1.0).add(v1);
            fail("add() does not throw an exception for zero vector");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Vector)}
     */
    @Test
    public void testSubtract() {
        Vector v1 = new Vector(1.0, 1.0, 1.0);
        Vector v2 = new Vector(-1.0, -1.0, -1.5);
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = v1.subtract(v2);
        //test that subtract operation is correct
        assertEquals(new Vector(2, 2, 2.5), v3);
        // =============== Boundary Values Tests ==================
        //test if zero vector throws exception
        try {
            v3 = v1.subtract(v1);
            fail("subtract() does not throw an exception for zero vector");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    public void testScale() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1.0, 1.0, 1.0);
        //test that scale operation is correct
        assertEquals(new Vector(1.5, 1.5, 1.5), v1.scale(1.5));
        // =============== Boundary Values Tests ==================
        //test if zero vector throws exception
        try {
            v1 = v1.scale(0);
            fail("subtract() does not throw an exception for zero vector");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}
     */
    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        //test that dot product operation is correct by comparing excepted number and the dot product results
        assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));
        assertTrue("ERROR: dotProduct() wrong value", isZero(v1.dotProduct(v2) + 28));
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }

    }

    /**
     * Test method for {@link primitives.Vector#length()}
     */
    @Test
    public void testLength() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests =============
        //check if the length() operation works well
        assertTrue("ERROR: lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
        assertTrue("ERROR: length() wrong value", isZero(new Vector(0, 3, 4).length() - 5));
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}
     */
    @Test
    public void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        // ============ Equivalence Partitions Tests =============
        //check if the normalize() operation works well
        // test vector normalization vs vector length and cross-product
        assertEquals("ERROR: normalize() function creates a new vector", vCopy, vCopyNormalize);
        assertTrue("ERROR: normalize() result is not a unit vector", isZero(vCopyNormalize.length() - 1));
        //check if the normalize() return new vector
        Vector u = v.normalized();
        assertNotEquals("ERROR: normalized() function does not create a new vector", u, v);
    }
}