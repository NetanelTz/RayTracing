package renderer;

import Tools.HaltonSerie2f;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;


public class RayGenerator {
    private static final double DELTA = 0.1;
    private static final int MAX_RAY_SAMPLES = 81;
    private static double[] _randomlist;
    private static HaltonSerie2f num;

    private static final int FIRST_SAMPLE_RAY = 8;
    private static double[] firstRanList;
    private static HaltonSerie2f getNum;

    static {
        firstRanList = new double[FIRST_SAMPLE_RAY * 2];
        getNum = new HaltonSerie2f();
        generateRandoms(FIRST_SAMPLE_RAY, getNum, firstRanList);
        _randomlist = new double[MAX_RAY_SAMPLES * 2];
        num = new HaltonSerie2f();
        generateRandoms(MAX_RAY_SAMPLES, num, _randomlist);
    }

    /**
     * generates a random xy for the sample ray.
     * circle shaped.
     * @param numOfRays num of rays to calculate
     * @param _num num from Halton serie
     * @param list list of numbers
     */
    static public void generateRandoms(int numOfRays, HaltonSerie2f _num, double[] list) {
        for (int i = 0; i < numOfRays * 2; ) {
            // getting the next number in the sequence.
            _num.inc();
            // the 'x' and 'y' we get is a double between 0 and 1,
            // so for the range to be between -1 and 1, we need to
            // add this calculation: X * 2 - 1
            double x = _num.getValues()[0] * 2 - 1;
            double y = _num.getValues()[1] * 2 - 1;
            // check 2 things.
            //
            // one, if the x or y is zero.
            // if so, when we scale a vector with zero it will
            // make an exception pop and stop the program.
            //(we could add an if there that check it before its adding the vector,
            // but u prefer to keep the checks in the minimum and here its a one time check,
            // and there its every time he make a ray)
            //
            // two, simply if X^2 + Y^2 <= R^2. and this is
            // the basic formula for a circle.
            if ((!Util.isZero(x) && !Util.isZero(y)) &&
                    (x * x + y * y <= 0.5)) {
                list[i] = x;
                i++;
                list[i] = y;
                i++;
            }
        }
    }


    /**
     * return a ray that starts a little bit above an object.
     *
     * @param head      start point
     * @param direction vector to point
     * @param normal    normal to point
     * @return new ray with offset
     */
    public static Ray rayWithOffset(Point3D head, Vector direction, Vector normal) {
        Vector delta = normal.scale(normal.dotProduct(direction) >= 0 ? DELTA : -DELTA);
        return new Ray(direction.normalize(), head.add(delta));
    }

    /**
     * get for random list
     *
     * @return random list of doubles
     */
    public static double[] get_randomlist() {
        return _randomlist;
    }

    /**
     * generate a list of ray.
     *
     * @param n        normal from point
     * @param point    point that we get
     * @param inRay    ray that hit point
     * @param glossy   value of glossy
     * @param isglossy if we need to calculate the glossy
     * @param isMax    check if we construct the maximum amount of rays
     * @return list of ray
     */
    static public List<Ray> constructReflectedRay(Vector n, Point3D point, Ray inRay, double glossy, boolean isglossy, boolean isMax) {
        Vector v = inRay.getVec();
        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn));
        Ray mainray = rayWithOffset(point, r, n);
        List<Ray> res = List.of(mainray);
        if (!isglossy || glossy == 0)
            return res;
        if (!isMax)
            res = constructSampleRay(mainray, glossy, new Plane(n, point), FIRST_SAMPLE_RAY, firstRanList);
        else
            res = constructSampleRay(mainray, glossy, new Plane(n, point), MAX_RAY_SAMPLES, _randomlist);
        return res;
    }

    public static List<Ray> constructRefractedRay(Point3D point, Ray inRay, Vector n, double glossy, boolean isglossy, boolean isMax) {
        Ray mainray = rayWithOffset(point, inRay.getVec(), n);
        List<Ray> res = List.of(mainray);
        if (!isglossy || glossy == 0)
            return res;

        if (!isMax)
            res = constructSampleRay(mainray, glossy, new Plane(n, point), FIRST_SAMPLE_RAY, firstRanList);
        else
            res = constructSampleRay(mainray, glossy, new Plane(n, point), MAX_RAY_SAMPLES, _randomlist);
        return res;
    }

    /**
     * calculate sample of rays
     *
     * @param mainray   main ray from light source
     * @param distance  distance from it
     * @param bound     plane of scene
     * @param numOfRays num of rays to calculate
     * @param list      list of random numbers
     * @return list of rays
     */
    static public List<Ray> constructSampleRay(Ray mainray, double distance, Plane bound, int numOfRays, double[] list) {
        List<Ray> res = new LinkedList<>();
        res.add(mainray);
        Ray sampleray = null;

        // here we calculate 2 vectors that act like a plane
        // that orthogonal to the main ray.
        Point3D upCoordinates = mainray.getVec().getHead();
        // the 'y' vector.
        Vector up = mainray.getVec().crossProduct(new Vector(upCoordinates.getX() * -1, upCoordinates.getY(), upCoordinates.getZ())).normalize();
        // the 'x' vector.
        Vector right = up.crossProduct(mainray.getVec()).normalize();
        // the middle point which the 'plane' starts from.
        Point3D middle = mainray.getTargetPoint(distance);

        for (int i = 0; i < numOfRays * 2; i += 2) {
            // we calculate a new point that we going to change,
            // and this point will be the new end point of the
            // new sample ray.
            Point3D targetPoint = new Point3D(middle);
            // we calculate the 'y' vector * the y random, and add it to the middle point.
            targetPoint = targetPoint.add(up.scale(list[i]));
            // we calculate the 'x' vector * the x random, and add it to the point.
            targetPoint = targetPoint.add(right.scale(list[i + 1]));
            // we create a new ray that start from the main point to the target point we calculated now.
            sampleray = new Ray(targetPoint.subtract(mainray.getPoint()), mainray.getPoint());
            // we check if the ray is hiting the same plane that she came from.
            if (bound.findIntersections(sampleray) == null)
                res.add(sampleray);
        }
        return res;
    }

}
