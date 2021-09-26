package geometries;

import elements.Material;
import primitives.Color;

/**
 * abstract class for flat geometry
 *
 * @author Noam and Netanel
 */
public abstract class flatGeometry extends Geometry {
    /**
     * ctor with 2 parameters using the super ctor
     *
     * @param _emission color from light
     * @param material material of geo
     */
    public flatGeometry(Color _emission, Material material) {
        super(_emission, material);
    }

    /**
     * ctor just with color and using super
     *
     * @param emission color from light
     */
    public flatGeometry(Color emission) {
        super(emission);
    }

    /**
     * default ctor
     */
    public flatGeometry() {
        super();
    }

}