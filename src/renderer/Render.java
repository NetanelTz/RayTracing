package renderer;

import elements.Camera;
import elements.LightSource;
import elements.Material;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * render class to create color matrix from the scene
 *
 * @author Noam and Netenl
 */
public class Render {
    private final ImageWriter _imageWriter;
    private final Scene _scene;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private boolean _isGlossy;

    private double _lastPrec;
    private boolean _printStats;

    /**
     * CTOR for render class
     *
     * @param imageWriter to save the image from scene that cam see
     * @param scene       the scene (geometries etc.) that cam see
     * @param _isGlossy   is the scene have gloss
     * @param _printStats show statistics
     */
    public Render(ImageWriter imageWriter, Scene scene, boolean _isGlossy, boolean _printStats) {
        this._imageWriter = imageWriter;
        this._scene = scene;
        this._isGlossy = _isGlossy;
        this._printStats = _printStats;
        this._lastPrec = 0;
    }

    /**
     * CTOR if glossy is undefined
     *
     * @param imageWriter image writer of render
     * @param scene       scene of image
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this(imageWriter, scene, false, false);
    }


    /**
     * set for _isGlossy
     *
     * @param _isGlossy value of glossy of obj
     */
    public void set_isGlossy(boolean _isGlossy) {
        this._isGlossy = _isGlossy;
    }


    /**
     * set for print statistic
     *
     * @param _printStats if we need to print
     */
    public void set_printStats(boolean _printStats) {
        this._printStats = _printStats;
    }

    /**
     * function to print the grid above the image
     *
     * @param interval the distance between lines in grid (distance between pixels)
     * @param color    color of grid line
     */
    public void printGrid(int interval, java.awt.Color color) {
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        //goes through every pixel and create the line if it needed
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }


    }

    /**
     * print the percentage of pixels we did
     * get the y and x of pixel
     *
     * @param i column
     * @param j row
     */
    private void printStat(int i, int j) {
        double prec = _imageWriter.getNx() * _imageWriter.getNy();
        prec = (i * j + j) / prec * 100;
        if (prec - _lastPrec < 0.5)
            return;
        String str = String.format("%.02f", prec);
        System.out.println(str + "%");
        _lastPrec = prec;
    }

    /**
     * function that write the image in memory by calling the function of the ImageWriter field in this class
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * find the closest point that intersect
     * with the ray
     *
     * @param ray ray to find when it intersect
     * @return Point3D or null if there is none
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        GeoPoint closes = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.getPoint());
            if (distance < closestDistance) {
                closes = geoPoint;
                closestDistance = distance;
            }
        }
        return closes;
    }


    /**
     * OLD
     * the function found the closes point to the view plain
     *
     * @param interPoints the point that intersect with the ray from camera
     * @return closes Point3D
     */
/*
    public GeoPoint getClosestPoint(List<GeoPoint> interPoints) {
        if (interPoints == null) {
            return null;
        }
        //save the cam location
        Point3D p0 = _scene.getCamera().get_p0();
        //reset the variables
        GeoPoint closes = null;
        double minDist = Double.MAX_VALUE;
        double currentDis = 0;
        //check for every point the distance from cam
        for (GeoPoint p : interPoints) {
            currentDis = p0.distance(p.getPoint());
            if (currentDis < minDist) {
                minDist = currentDis;
                closes = p;
            }
        }

        return closes;
    }
 */

    /**
     * used as a start the calc color function,
     * and adds the ambient light.
     *
     * @param geopoint point to calc the color
     * @param inRay    ray that hit the point
     * @return color of point
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }

    /**
     * recursive function for calculate color
     *
     * @param p     point that we search its color
     * @param inRay the ray of light
     * @param level level of recursion
     * @param k     transparency
     * @return color of point
     */
    private Color calcColor(GeoPoint p, Ray inRay, int level, double k) {
        if (level == 1 || k < MIN_CALC_COLOR_K) {//end of recursion
            return Color.BLACK;
        }
        Point3D getPoint = p.getPoint();
        Color color = (p.getGeometry().getEmission());
        Vector v = inRay.getVec();
        Vector n = p.getGeometry().getNormal(getPoint);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            //ray parallel to geometry surface ??
            //and orthogonal to normal
            return color;

        }
        Material m = p.getGeometry().getMaterial();
        int nShininess = m.get_nShininess();
        double kd = m.get_kD();
        double ks = m.get_ks();
        double kr = m.get_kR();
        double kt = m.get_kT();
        double glossy = m.get_glossy();
        double kkr = k * kr;
        double kkt = k * kt;

        color = color.add(getLSColors(p, p.getPoint(), color, v, n, nShininess, kd, ks, k));
        if (kkr > MIN_CALC_COLOR_K) {
            List<Ray> reflectedRays = RayGenerator.constructReflectedRay(n, getPoint, inRay, glossy, _isGlossy, false);
            if (fewColors(reflectedRays, p, level, kr, kkr))
                reflectedRays = RayGenerator.constructReflectedRay(n, getPoint, inRay, glossy, _isGlossy, true);
            if (reflectedRays != null) color = color.add(calcRaysColor(reflectedRays, p, level, kr, kkr));

        }
        if (kkt > MIN_CALC_COLOR_K) {
            List<Ray> refractedRays = RayGenerator.constructRefractedRay(getPoint, inRay, n, glossy, _isGlossy, false);
            if (fewColors(refractedRays, p, level, kt, kkt))
                refractedRays = RayGenerator.constructRefractedRay(getPoint, inRay, n, glossy, _isGlossy, true);
            if (refractedRays != null) color = color.add(calcRaysColor(refractedRays, p, level, kt, kkt));
        }
        return color;
    }


    /**
     * calculate the colors for an array of rays.
     *
     * @param rays  list of rays to calc the colors
     * @param point point that rays hit
     * @param level level of recursion
     * @param kx    kt or kr
     * @param kkx   kkt or kkr
     * @return average  color
     */
    private Color calcRaysColor(List<Ray> rays, GeoPoint point, int level, double kx, double kkx) {
        Color color = new Color(0, 0, 0);
        for (Ray reflectedRay : rays) {
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null && !reflectedPoint.getGeometry().equals(point.getGeometry())) {
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkx).scale(kx));
            }
        }
        return color.reduce(rays.size());
    }

    /**
     * check if few rays are the same color
     *
     * @param rays  list of rays
     * @param point point they hit
     * @param level level of recursion
     * @param kx    kt or kr
     * @param kkx   kkt or kkr
     * @return true or false
     */
    private boolean fewColors(List<Ray> rays, GeoPoint point, int level, double kx, double kkx) {
        Color color = new Color(0, 0, 0);
        GeoPoint _point = findClosestIntersection(rays.get(0));
        if (_point != null && !_point.getGeometry().equals(point.getGeometry()))
            color = color.add(calcColor(_point, rays.get(0), level - 1, kkx));
        for (Ray ray : rays) {
            _point = findClosestIntersection(ray);
            Color colorCmp = new Color(0, 0, 0);
            if (_point != null && !_point.getGeometry().equals(point.getGeometry())) {
                colorCmp = colorCmp.add(calcColor(_point, ray, level - 1, kkx).scale(kx));
                if (colorCmp.getColor() != color.getColor())
                    return true;
            }
        }
        return false;
    }

    /**
     * calculate the color of a the light sources.
     *
     * @param p          point that light hit
     * @param getPoint   point of
     * @param color      color of point
     * @param v          vector from light
     * @param n          normal vector
     * @param nShininess shininess of point
     * @param kd         kd of light source
     * @param ks         value of ks in material
     * @param k          the additional of color
     * @return color of light source
     */
    private Color getLSColors(GeoPoint p, Point3D getPoint, Color color, Vector v, Vector n, int nShininess, double kd, double ks, double k) {
        for (LightSource ls : _scene.get_lights()) {
            Vector l = ls.getL(getPoint);

            double nl = alignZero(n.dotProduct(l));
            double nv = alignZero(n.dotProduct(v));
            if (Math.signum(nl) == Math.signum(nv) && !Util.isZero(nl)) {
                double ktr = transparency(l, n, p, ls);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = ls.getIntensity(getPoint).scale(ktr);
                    color = color.add(calcDiffusive(kd, nl, lightIntensity), calcSpecular(ks, l, nl, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * function to calc the diffuse of light reflection
     *
     * @param kD             value of diffusive
     * @param nl             normal of geometry * direction of light source
     * @param lightIntensity intensity of light source
     * @return color of diffusive
     */
    private Color calcDiffusive(double kD, double nl, Color lightIntensity) {
        if (nl < 0) nl = -nl;
        return lightIntensity.scale(nl * kD);
    }

    /**
     * function to calc the specular of light reflection
     *
     * @param kS             value of ks in material
     * @param l              vector to point from light source
     * @param nl             normal vector from point * l vector
     * @param n              normal from point
     * @param v              vector from camera
     * @param nShininess     shininess of point
     * @param lightIntensity intensity of light
     * @return color of specular
     */
    private Color calcSpecular(double kS, Vector l, double nl, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl)).normalize();
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0) return Color.BLACK;
        return lightIntensity.scale(kS * Math.pow(minusVR, nShininess));
    }

    /**
     * transparency check if this geoPoint is have some
     * other object between the light.
     * this function replace unshaded function
     *
     * @param l  vector from light to geoPoint
     * @param n  vector normal
     * @param gp geoPoint
     * @param ls light
     * @return double of how much shade.
     */
    private double transparency(Vector l, Vector n, GeoPoint gp, LightSource ls) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = RayGenerator.rayWithOffset(gp.getPoint(), lightDirection, n);
        Point3D pointGeo = gp.getPoint();
        double lightDistance = ls.getDistance(pointGeo);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay,lightDistance);
        double ktr = 1.0;
        if (intersections == null) return ktr;


        for (GeoPoint _gp : intersections) {
                ktr *= _gp.getGeometry().getMaterial().get_kT();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
    }

    //=====================threads implementation area===============
    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents >= 0)
                return true;
            return false;
        }
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double dist = _scene.getDistance();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final Camera camera = _scene.getCamera();
        java.awt.Color background = _scene.getBackground().getColor();

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    if (_printStats)
                        printStat(pixel.row, pixel.col);
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, dist, width, height);
                    GeoPoint closesPoint = findClosestIntersection(ray);
                    if (closesPoint == null) {
                        _imageWriter.writePixel(pixel.col, pixel.row, background);
                    } else {
                        _imageWriter.writePixel(pixel.col, pixel.row, calcColor(closesPoint, ray).getColor());
                    }
                }


            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (_print) System.out.printf("\r100%%\n");
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

}
