
package renderer;

import Tools.HaltonSerie2f;
import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
						new Point3D(0, 0, 50)),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

		scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
				0.0004, 0.0000006));

		ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}


	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.addGeometries(
				new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
				new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

		scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(-750, 750, 150),
				new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
	 *  producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						30, new Point3D(60, -50, 50)));

		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}


	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
	 *  producing partial shadow
	 */
	@Test
	public void myPicForHomework() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(1000, 0, 250), new Vector(-1, 0, -0.25), new Vector(-0.25, 0, 1)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1),
						new Point3D(500,0,0),new Point3D(0,500,0),new Point3D(0,-500,0)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.5, 0),
						50, new Point3D(0, -25, 50)),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.2, 0.2, 30, 0.5, 0),
						50, new Point3D(0, 25, 50)));

		scene.addLights(new PointLight(new Color(700, 400, 400), //
				new Point3D(100, 0, 50), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("3 objects", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}


	/**
	 * 8+ objects and 3+ light source. mini project 1
	 */
	@Test
	public void miniProjectReflective() {
		Scene scene = new Scene("Scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(1, 0, 0)));
		scene.setCamera(new Camera(new Point3D(1500, 0, 350), new Vector(-1, 0, -0.1), new Vector(-0.1, 0, 1)));
		scene.setDistance(300);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.000015));

		scene.addGeometries(
				new Plane(new Color(java.awt.Color.DARK_GRAY),new Material(0.2, 0.2, 30, 0, 0.2, 50),
						new Vector(0,0,1), new Point3D(0,0,0)),



				new Sphere(new Color(java.awt.Color.DARK_GRAY), new Material(0.2, 0.2, 30, 0.7, 0,30),
						125, new Point3D(0, 0, 375)),

				new Polygon(new Color(20,20,20), new Material(0.2, 0.2, 30, 0, 0.1),
						new Point3D(-200,0,250),new Point3D(0,200,250),new Point3D(200,0,250),new Point3D(0,-200,250)),
				new Polygon(new Color(20,20,20), new Material(0.2, 0.2, 30, 0, 0.1),
						new Point3D(200,0,250),new Point3D(200,0,0),new Point3D(0,-200,0),new Point3D(0,-200,250)),
				new Polygon(new Color(20,20,20), new Material(0.2, 0.2, 30, 0, 0.1),
						new Point3D(200,0,250),new Point3D(200,0,0),new Point3D(0,200,0),new Point3D(0,200,250)),


				new Sphere(new Color(java.awt.Color.DARK_GRAY), new Material(0.2, 0.2, 30, 0, 0.2),
						125, new Point3D(0, 500, 300)),

				new Polygon(new Color(40,192,177), new Material(0.2, 0.2, 30),
						new Point3D(-200,500,175),new Point3D(0,700,175),new Point3D(200,500,175),new Point3D(0,300,175)),
				new Polygon(new Color(40,192,177), new Material(0.2, 0.2, 30),
						new Point3D(200,500,175),new Point3D(200,500,0),new Point3D(0,300,0),new Point3D(0,300,175)),
				new Polygon(new Color(40,192,177), new Material(0.2, 0.2, 30),
						new Point3D(200,500,175),new Point3D(200,500,0),new Point3D(0,700,0),new Point3D(0,700,175)),


				new Sphere(new Color(java.awt.Color.DARK_GRAY), new Material(0.2, 0.2, 30, 0, 0.2),
						125, new Point3D(0, -500, 225)),

				new Polygon(new Color(149,56,158), new Material(0.2, 0.2, 30),
						new Point3D(-200,-500,100),new Point3D(0,-300,100),new Point3D(200,-500,100),new Point3D(0,-700,100)),
				new Polygon(new Color(149,56,158), new Material(0.2, 0.2, 30),
						new Point3D(200,-500,100),new Point3D(200,-500,0),new Point3D(0,-700,0),new Point3D(0,-700,100)),
				new Polygon(new Color(149,56,158), new Material(0.2, 0.2, 30),
						new Point3D(200,-500,100),new Point3D(200,-500,0),new Point3D(0,-300,0),new Point3D(0,-300,100)),


				new Polygon(new Color(255,255,255), new Material(0.2, 0.2, 500),
						new Point3D(500,500,1000),new Point3D(500,1000,1000),new Point3D(500,1000,1500),new Point3D(500,500,1500)),
				new Polygon(new Color(255,255,255), new Material(0.2, 0.2, 500),
						new Point3D(500,500,1800),new Point3D(500,1000,1800),new Point3D(500,1000,2300),new Point3D(500,500,2300)),
				new Polygon(new Color(255,255,255), new Material(0.2, 0.2, 500),
						new Point3D(500,300,1000),new Point3D(500,-200,1000),new Point3D(500,-200,1500),new Point3D(500,300,1500)),
				new Polygon(new Color(255,255,255), new Material(0.2, 0.2, 500),
						new Point3D(500,300,1800),new Point3D(500,-200,1800),new Point3D(500,-200,2300),new Point3D(500,300,2300))
		);


		scene.addLights(new PointLight(new Color(201,648,603),new Point3D(200,-1000,600), 1, 8E-5, 2E-6),
				new PointLight(new Color(745,280,790),new Point3D(0,1000,700), 1, 8E-5, 2E-6),
				new PointLight(new Color(500,500,500),new Point3D(500,0,400), 1, 8E-5, 2E-6));

		ImageWriter imageWriter = new ImageWriter("miniProjectReflective", 320, 180, 640,360);
		//ImageWriter imageWriter = new ImageWriter("miniProjectReflective", 320, 180, 1920,1080);
		//ImageWriter imageWriter = new ImageWriter("miniProjectReflective", 320, 180, 320,180);
		Render render = new Render(imageWriter, scene).setMultithreading(4);//.setDebugPrint();
		render.set_isGlossy(true);
		render.set_printStats(true);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * test for mini project 1
	 */
	@Test
	public void miniProject1() {
		Scene scene = new Scene("Scene");
		//scene.setCamera(new Camera(new Point3D(-100, 0, 600), new Vector(0, 0, -1), new Vector(1, 0, 0)));

		 scene.setCamera(new Camera(new Point3D(200, 200, 50), new Vector(-1, -0.7, 0), new Vector(0, 0, 1)));

		Color[] colors = new Color[]{new Color(230, 57, 70),new Color(241, 250, 238),new Color(168, 218, 220),
				new Color(69, 123, 157),new Color(29, 53, 87)};
		scene.setDistance(250);
		scene.setBackground(colors[4]);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.000015));




		scene.addGeometries(
				new Plane(colors[4],new Material(0.2, 0.2, 30, 0, 0.2, 50),
						new Vector(0,0,1), new Point3D(0,0,0)),

				new Geometries(new Geometries(	new Polygon(colors[4].reduce(2),  new Material(0.25, 0.25, 20, 0.7, 0,70),
						new Point3D(0,0,0),new Point3D(0,200,0),new Point3D(0,200,100),new Point3D(0,0,100)),
				new Polygon(colors[4].reduce(2), new Material(0.25, 0.25, 20, 0.7, 0,70),
						new Point3D(0,200,0),new Point3D(-15,200,0),new Point3D(-15,200,100),new Point3D(0,200,100))),

						new Geometries(new Polygon(colors[4].reduce(2), new Material(0.25, 0.25, 20, 0.7, 0,70),
						new Point3D(0,-250,0),new Point3D(0,-50,0),new Point3D(0,-50,100),new Point3D(0,-250,100)),
				new Polygon(colors[4].reduce(2), new Material(0.25, 0.25, 20, 0.7, 0,70),
						new Point3D(0,-50,0),new Point3D(-15,-50,0),new Point3D(-15,-50,100),new Point3D(0,-50,100)))),


				new Geometries(new Triangle(colors[0].reduce(2), new Material(0.25, 0.25, 20),
						new Point3D(-50,0,0),new Point3D(-100,-50,100),new Point3D(-50,-100,0)),
				new Triangle(colors[0].reduce(2), new Material(0.25, 0.25, 20),
						new Point3D(-50,0,0),new Point3D(-100,-50,100),new Point3D(-150,0,0)),
				new Triangle(colors[0].reduce(2), new Material(0.25, 0.25, 20),
						new Point3D(-150,0,0),new Point3D(-100,-50,100),new Point3D(-150,-100,0)),
				new Triangle(colors[0].reduce(2), new Material(0.25, 0.25, 20),
						new Point3D(-50,-100,0),new Point3D(-100,-50,100),new Point3D(-150,-100,0)))



				);
		HaltonSerie2f xy = new HaltonSerie2f();
		HaltonSerie2f r = new HaltonSerie2f();
		for(int i = 0; i<40; i++){
			xy.inc();
			r.inc();
			double x = xy.getValues()[0]*-400-15;
			double y = xy.getValues()[1]*700-500;
			double z = r.getValues()[0]*20+5;
			scene.addGeometries(new Sphere(colors[i%4].reduce(4), new Material(0.5, 0.5, 100)
					,z,new Point3D(x,y,z)));
		}

		scene.addLights(
				new PointLight(new Color(200,200,200),new Point3D(-200,-200,200), 1, 8E-5, 2E-6),
				new PointLight(new Color(200,200,200),new Point3D(200,-200,200), 1, 8E-5, 2E-6)
		);

		ImageWriter imageWriter = new ImageWriter("miniProject1", 320, 180, 640,360);
		//ImageWriter imageWriter = new ImageWriter("miniProject1", 320, 180, 1920,1080);
		//ImageWriter imageWriter = new ImageWriter("miniProject1", 320, 180, 320,180);
		Render render = new Render(imageWriter, scene).setMultithreading(7);
		render.set_isGlossy(true);
		render.set_printStats(true);
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void glossTest() {
		Scene scene = new Scene("Scene");
		scene.setCamera(new Camera(new Point3D(1500, 0, 350), new Vector(-1, 0, -0.1), new Vector(-0.1, 0, 1)));
		scene.setDistance(300);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.000015));

		scene.addGeometries(
				new Plane(new Color(0,0,0),new Material(0, 0, 0, 0, 1, 50),
						new Vector(0,0,1), new Point3D(0,0,0)),

				new Sphere(new Color(114, 48, 123), new Material(0.2, 0.2, 30),
						125, new Point3D(0, 0, 125))

		);


		scene.addLights(new PointLight(new Color(500,500,500),new Point3D(0,0,500), 1, 8E-5, 2E-6));

		ImageWriter imageWriter = new ImageWriter("test reflection with gloss", 320, 180, 1920,1080);
		//ImageWriter imageWriter = new ImageWriter("test reflection with gloss", 320, 180, 640,360);
		//ImageWriter imageWriter = new ImageWriter("test reflection with gloss", 320, 180, 320,180);
		Render render = new Render(imageWriter, scene).setMultithreading(4);
		render.set_isGlossy(true);
		render.set_printStats(true);
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void transparentTest() {
		Scene scene = new Scene("Scene");
		scene.setCamera(new Camera(new Point3D(1500, 0, 350), new Vector(-1, 0, -0.1), new Vector(-0.1, 0, 1)));
		scene.setDistance(300);
		scene.setBackground(new Color(Color.BLACK));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.000015));

		scene.addGeometries(
				new Sphere(new Color(20,20,20), new Material(0.25, 0.25, 20, 1, 0,10),
						125, new Point3D(0, 0, 125)),
				new Polygon(new Color(0, 0, 255), new Material(0.2, 0.2, 30),
						new Point3D(0, 0, 400), new Point3D(0, -100, 20),new Point3D(0, 100, 20))

		);


		scene.addLights(new PointLight(new Color(500,500,500),new Point3D(0,0,500), 1, 8E-5, 2E-6));

		//ImageWriter imageWriter = new ImageWriter("test trasperent with gloss", 320, 180, 1920,1080);
		//ImageWriter imageWriter = new ImageWriter("test reflection with gloss", 320, 180, 640,360);
		ImageWriter imageWriter = new ImageWriter("test reflection with gloss", 320, 180, 320,180);
		Render render = new Render(imageWriter, scene).setMultithreading(8);
		render.set_isGlossy(true);
		render.set_printStats(true);
		render.renderImage();
		render.writeToImage();
	}

}
