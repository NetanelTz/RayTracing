package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.Material;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

/**
 * test class for spot light
 *
 * @author Noam and Netanel
 */
public class SpotLightTest {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(500, 300, 0), new Point3D(-50, 50, -50),
                new Vector(1, -1, 2), 0.9, 1, 0.00001, 0.00000001));

        ImageWriter imageWriter = new ImageWriter("sphereSharpSpot", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        scene.addLights(new SpotLight(new Color(500, 250, 250),
                new Point3D(10, 10, 130), new Vector(-2, 2, 1), 0.5,
                1, 0.0001, 0.000005));

        ImageWriter imageWriter = new ImageWriter( "trianglesSharpSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

}