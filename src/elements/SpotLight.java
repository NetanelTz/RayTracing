package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * class for spot light
 * point light is light of 180 degrees
 *
 * @author Noam and Netanel
 */
public class SpotLight extends PointLight implements LightSource {

    Vector _direction;
    double _angle;

    /**
     * CTOR for spotlight. light only in 1 direction.
     *
     * @param _intensity intensity of light
     * @param _position  position of light
     * @param _direction direction of light
     * @param _kC        constant
     * @param _kL        linear
     * @param _kQ        square
     */
    public SpotLight(Color _intensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        this(_intensity, _position, _direction, 0, _kC, _kL, _kQ);
    }

    /**
     * CTOR for spotlight. light only in 1 direction.
     *
     * @param _intensity intensity of light
     * @param _position  position of light
     * @param _direction direction of light
     * @param _kC        constant
     * @param _kL        linear
     * @param _kQ        square
     * @param _angle     angle of spot light
     */
    public SpotLight(Color _intensity, Point3D _position, Vector _direction, double _angle, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._angle = _angle;
        this._direction = _direction.normalize();
    }


    @Override
    public Color getIntensity(Point3D p) {
        //angle between vectors
        double dl = Util.alignZero(_direction.dotProduct(getL(p)));
        if (dl <= _angle)
            return Color.BLACK;
        else if (!Util.isZero(_angle))
            return super.getIntensity(p).scale((dl - _angle) / (1 - _angle));
        else
            return super.getIntensity(p).scale(dl);
    }

}
