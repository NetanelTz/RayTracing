package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for direct light on scene (like sun)
 *
 * @author Noam and Netanel
 */
public class DirectionalLight extends Light implements LightSource {

    private final Vector _direction;

    /**
     * Initialize directional light with it's intensity and direction, direction
     * vector will be normalized.
     *
     * @param colorIntensity intensity of the light
     * @param direction      direction vector
     */

    public DirectionalLight(Color colorIntensity, Vector direction) {
        super(colorIntensity);
        _direction = direction.normalize();
    }

    /**
     * @param p the lighted point is not used and is mentioned
     *          only for compatibility with LightSource
     * @return fixed intensity of the directionLight
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    /**
     * override getl
     *
     * @param p point
     * @return vector
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    @Override
    public double getDistance(Point3D p) {
        return Double.POSITIVE_INFINITY;
    }
}
