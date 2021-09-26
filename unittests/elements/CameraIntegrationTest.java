package elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static geometries.Intersectable.GeoPoint;

import primitives.*;
import geometries.*;

import java.util.List;

/**
 * Integration Tests between the Creation of the camera Rays and the Calculation of Rays Cuts with Geometric Bodies
 *
 * @author Noam and Netanel
 */
class CameraIntegrationTest {
    int Nx = 3; // number of pixels in a row of view plane
    int Ny = 3; // number of pixels in a column of view plane

    /**
     * integration test of camera ray construction whit ray sphere intersections
     */
    @Test
    void CameraSphereIntegration() {
        Camera camera1 = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        Camera camera2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> results;
        Sphere sphere;
        int count = 0;

        //TC01 : small sphere ,there are 2 intersection points
        sphere = new Sphere(1, new Point3D(0, 0, 3));
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals(2, count, "Wrong number of intersections");

        //TC02 : Big sphere , there have 18 intersection points
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        count = 0;
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(18, count, "Wrong number of intersections");

        //TC03 : Medium sphere , there have 10 intersection points
        sphere = new Sphere(2d, new Point3D(0, 0, 2));
        count = 0;
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(10, count, "Wrong number of intersections");

        //TC04 : Inside sphere, there have 9 intersection points
        sphere = new Sphere(4d, new Point3D(0, 0, 2));
        count = 0;
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(9, count, "Wrong number of intersections");

        //TC05 : Beyond sphere, there have 9 intersection points
        sphere = new Sphere(0.5d, new Point3D(0, 0, -1));
        count = 0;
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(0, count, "Wrong number of intersections");
    }

    /**
     * integration test of camera ray construction whit ray-plane intersections
     */
    @Test
    void CameraPlaneIntegration() {
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> results;
        Plane plane;
        int count = 0;
        //TC01 : The plane against camera , there are 9 intersection points
        plane = new Plane(new Point3D(0, 0, 2), new Point3D(-1, 2, 2), new Point3D(-1, -2, 2));
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntersections(camera.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(9, count, "Wrong number of intersections");

        //TC02 : The plane whit small angle toward the camera , there are 9 intersection points
        plane = new Plane(new Point3D(0, 0, 2.5), new Point3D(-1, 2, 2), new Point3D(-1, -2, 2));
        count = 0;
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntersections(camera.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(9, count, "Wrong number of intersections");

        //TC03 : The plane whit big angle toward the camera , the lower rays don't intersect the plane, there are 6 intersection points
        plane = new Plane(new Point3D(0, 0, 4), new Point3D(-1, 2, 2), new Point3D(-1, -2, 2));
        count = 0;
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntersections(camera.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(6, count, "Wrong number of intersections");
    }

    /**
     * integration test of camera ray construction whit ray-triangle intersections
     */
    @Test
    void CameraTriangleIntegration() {
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> results;
        Triangle triangle;
        int count = 0;

        //TC01 : small triangle  , there is 1 intersection point
        triangle = new Triangle(new Point3D(0, -1, 2), new Point3D(-1, 1, 2), new Point3D(1, 1, 2));
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = triangle.findIntersections(camera.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(1, count, "Wrong number of intersections");

        //TC02 : medium triangle , there are 2 intersection points
        triangle = new Triangle(new Point3D(0, -20, 2), new Point3D(-1, 1, 2), new Point3D(1, 1, 2));
        count = 0;
        for (int i = 0; i < Ny; ++i)
            for (int j = 0; j < Nx; ++j) {
                results = triangle.findIntersections(camera.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        assertEquals(2, count, "Wrong number of intersections");
    }

}