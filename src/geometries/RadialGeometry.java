package geometries;

import elements.Material;
import primitives.*;

import java.util.Objects;

import static primitives.Util.*;

/**
 * abstract class for radial geometry shape
 *
 * @author Noam and Netanel
 */
public abstract class RadialGeometry extends Geometry {
    double _radius;

    /**
     * ctor
     *
     * @param r radius of geometry
     */
    public RadialGeometry(double r) {
        setRadius(r);
    }

    /**
     * ctor using another ctor
     *
     * @param material material of geometry
     * @param emission emission of geometry
     * @param r        radius of geometry
     */
    public RadialGeometry(Color emission, Material material, double r) {
        super(emission, material);
        setRadius(r);
    }

    /**
     * ctor with 2 parameters
     *
     * @param emission emission of geometry
     * @param r        radius of geometry
     */
    public RadialGeometry(Color emission, double r) {
        super(emission);
        setRadius(r);
    }

    /**
     * setter of radius
     *
     * @param r radius of geometry
     */
    private void setRadius(double r) {
        if (r <= 0.0 || isZero(r))//check correct of radius
            throw new IllegalArgumentException("Radius must be more than 0");
        _radius = r;
    }

    /**
     * copy ctor
     *
     * @param r radius of radial
     */
    public RadialGeometry(RadialGeometry r) {
        this._radius = r._radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadialGeometry that = (RadialGeometry) o;
        return Double.compare(that._radius, _radius) == 0;
    }

    /**
     * return the radius
     *
     * @return double of radius
     */
    public double get_radius() {
        return _radius;
    }
}
