package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for implement point light
 * point light is light of 360 degrees
 *
 * @author Netanel and Noam
 */
public class PointLight extends Light implements LightSource {

    Point3D _position;
    double _kC, _kL, _kQ;

    /**
     * ctor of class
     *
     * @param _intensity intensity of color
     * @param _position  position of light
     * @param _kC        constant
     * @param _kL        linear
     * @param _kQ        square
     */
    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    /**
     * intensity of light according lab slide show
     *
     * @param p point to get the intensity
     * @return color of point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);
        return _intensity.reduce(_kC + _kL * d + _kQ * d * d);
    }

    @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position)) {
            return null;
        }
        return p.subtract(_position).normalize();
    }

    @Override
    public double getDistance(Point3D p) {
        return _position.distance(p);
    }
}
