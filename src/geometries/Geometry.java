package geometries;

import primitives.Color;
import elements.Material;
import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

/**
 * abstract class for geometric shape
 *
 * @author Noam and Netanel
 */
public abstract class Geometry implements Intersectable {
    protected final Color _emission;
    protected final Material _material;
    protected Box _box;

    /**
     * CTOR for initial this class
     *
     * @param _emission the color that the light create
     * @param material  material of shape
     */

    public Geometry(Color _emission, Material material) {
        this._emission = _emission;
        this._material = material;
    }

    /**
     * ctor with just emission light that calling to ctor with 2 parameters
     *
     * @param emission the color that the light create
     */
    public Geometry(Color emission) {
        this(emission, new Material(0, 0, 0));
    }

    /**
     * default ctor for geometry calling to ctor with 2 parameters
     */
    public Geometry() {
        this(Color.BLACK, new Material(0, 0, 0));
    }

    /**
     * abstract method for abstract class
     *
     * @param point point to get normal from it
     * @return vector
     */

    abstract public Vector getNormal(Point3D point);

    /**
     * return the emission of geo
     *
     * @return Color
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * return the emission of geo
     *
     * @return material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * override toString method
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Geometry{" +
                "_emission=" + (_emission != null) +
                ", _material=" + (_material != null) +
                '}';
    }

    @Override
    abstract public boolean equals(Object o);

    @Override
    public Box getBox() {
        return _box;
    }
}
