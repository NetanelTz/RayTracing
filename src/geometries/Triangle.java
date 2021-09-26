package geometries;

import elements.Material;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * class for triangle contain  ctor
 *
 * @author Noam and Netanel
 */
public class Triangle extends Polygon {
//all ctors is from polygon ctor

    /**
     * ctor just with points
     *
     * @param p1 point 3d
     * @param p2 point 3d
     * @param p3 point 3d
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * ctor with emission too
     *
     * @param emission emission of object
     * @param p1       point 3d
     * @param p2       point 3d
     * @param p3       point 3d
     */
    public Triangle(Color emission, Point3D p1, Point3D p2, Point3D p3) {
        super(emission, p1, p2, p3);
    }

    /**
     * ctor with material too
     *
     * @param emission emission of object
     * @param material material of obj
     * @param p1       point 3d
     * @param p2       point 3d
     * @param p3       point 3d
     */
    public Triangle(Color emission, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emission, material, p1, p2, p3);
    }

}
