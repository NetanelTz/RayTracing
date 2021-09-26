package Tools;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import elements.*;
import geometries.*;
import org.w3c.dom.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.io.File;
import java.util.jar.Attributes;

import static java.lang.Double.parseDouble;

/**
 * class to reading image from xml file
 *
 * @author Noam and Netenel
 */
public class Xml {
    static public Render openRender(String path) {
        try {
            //creating a constructor of file class and parsing an XML file
            File file = new File(path);
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            Element root = doc.getDocumentElement();
            root.normalize();
            if (root.getNodeName() != "scene") {
                return null;
            }

            Scene resScene = new Scene("xml scene");
            resScene.setDistance(str2num(root.getAttribute("screen-distance")));
            resScene.setBackground(new Color(str2num(root.getAttribute("background-color"), 0),
                    str2num(root.getAttribute("background-color"), 1),
                    str2num(root.getAttribute("background-color"), 2)));


            Element cam = (Element) doc.getElementsByTagName("camera").item(0);


            resScene.setCamera(new Camera(str2point(cam.getAttribute("P0")),
                    str2vec(cam.getAttribute("Vto")),
                    str2vec(cam.getAttribute("Vup"))));

            Element img = (Element) doc.getElementsByTagName("image").item(0);

            ImageWriter resIM = new ImageWriter("xml image writer",
                    str2num(img.getAttribute("screen-width")),
                    str2num(img.getAttribute("screen-height")),
                    (int) str2num(img.getAttribute("Ny")),
                    (int) str2num(img.getAttribute("Nx")));

            Element light = (Element) doc.getElementsByTagName("ambient-light").item(0);

            resScene.setAmbientLight(new AmbientLight(new Color(str2num(light.getAttribute("color"), 0),
                    str2num(light.getAttribute("color"), 1),
                    str2num(light.getAttribute("color"), 2)), 1d));

            Element geometries = (Element) doc.getElementsByTagName("geometries").item(0);
            NodeList geo = geometries.getChildNodes();
            for (int i = 0; i < geo.getLength(); i++) {
                Node node = geo.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    if (eElement.getNodeName() == "sphere") {
                        resScene.addGeometries(new Sphere(str2num(eElement.getAttribute("radius")),
                                str2point(eElement.getAttribute("center"))));
                    } else if (eElement.getNodeName() == "cylinder") {
                        // dont know how ray will look like.
                        //resScene.addGeometries();
                    } else if (eElement.getNodeName() == "plane") {
                        resScene.addGeometries(new Plane(str2vec(eElement.getAttribute("normal")),
                                str2point(eElement.getAttribute("p0"))));
                    } else if (eElement.getNodeName() == "polygon") {
                        // preparation to read polygon from xml
                        //   resScene.addGeometries();
                    } else if (eElement.getNodeName() == "triangle") {
                        resScene.addGeometries(new Triangle(str2point(eElement.getAttribute("p0")),
                                str2point(eElement.getAttribute("p1")),
                                str2point(eElement.getAttribute("p2"))));
                    } else if (eElement.getNodeName() == "tube") {
                        //later
                        //resScene.addGeometries();
                    }
                }
            }
            return new Render(resIM, resScene);
        } catch (Exception e) {
            return null;
        }
    }

    static public double str2num(String str, int num) {
        String[] arrOfStr = str.split(" ", 10);
        return parseDouble(arrOfStr[num]);
    }

    static public double str2num(String str) {
        return str2num(str, 0);
    }

    static public Point3D str2point(String str) {
        return new Point3D(str2num(str, 0), str2num(str, 1), str2num(str, 2));
    }

    static public Vector str2vec(String str) {
        return new Vector(str2num(str, 0), str2num(str, 1), str2num(str, 2));
    }
}