package renderer;

import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.List;

public class rayGeneratorTest {




    @Test
    public void get_randomlisttest() {

        ImageWriter _imageWriter = new ImageWriter("random circle test", 100,100,200,200);

        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        //goes through every pixel and create the line if it needed
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                    _imageWriter.writePixel(j, i, new java.awt.Color(0,0,0));
            }
        }

        double[] r = RayGenerator.get_randomlist();
        System.out.println(r.length);
        for(int i = 0; i < 8; i+=2){
            System.out.println(r[i] + " / " + r[i+1]);
            _imageWriter.writePixel((int)(-r[i]*100)+100,((int)(-r[i+1]*100)+100) ,new java.awt.Color(255, 0, 14));
        }

        for(int i = 8; i < r.length; i+=2){
            System.out.println(r[i] + " / " + r[i+1]);
            _imageWriter.writePixel((int)(-r[i]*100)+100,((int)(-r[i+1]*100)+100) ,new java.awt.Color(0, 25,255));
        }
        _imageWriter.writeToImage();
    }
    @Test
    public void testConstructreflectedRay(){
    //    List<Ray> size = RayGenerator.constructReflectedRay(new Vector(0,0,1),new Point3D(0,0,0),
     //           new Ray(new Vector(1,0,1),new Point3D(-1,0,-1)),0.2,true,true);

    }

    @Test
    public void constructSampleRayTest() {
       // List<Ray> rays = RayGenerator.constructSampleRay(new Ray(new Vector(0,0,1),new Point3D(0,0,0)),100);
       // for(Ray r : rays)
        // System.out.println(r.toString());
    }
}
