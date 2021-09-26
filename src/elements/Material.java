package elements;

/**
 * class that define the material of object
 */
public class Material {
    private final double _kD;
    private final double _kS;
    private final double _kT;//transparency of surface =1 for translucent or 0 for opaque
    private final double _kR;//how much the surface is matt(=0) or mirror(=1)
    private final int _nShininess;
    private final double _glossy;

    /**
     * ctor to initial the class with 6 parameters
     *
     * @param _kD         diffusive factor
     * @param _kS         specular factor
     * @param _kT         transparency factor
     * @param _kR         reflection factor
     * @param _nShininess shininess of material
     * @param _glossy     glossy
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR, double _glossy) {
        this._kD = _kD;
        this._kS = _kS;
        this._kT = _kT;
        this._kR = _kR;
        this._nShininess = _nShininess;
        this._glossy = _glossy;
    }

    /**
     * ctor to initial the class with 3 parameters
     * calling to new ctor
     *
     * @param kD         diffusive factor
     * @param ks         specular factor
     * @param nShininess shininess of material
     */
    public Material(double kD, double ks, int nShininess) {
        this(kD, ks, nShininess, 0, 0, 0);
    }

    /**
     * ctor to initial the class with 5 parameters
     *
     * @param _kD         diffusive factor
     * @param _kS         specular factor
     * @param _kT         transparency factor
     * @param _kR         reflection factor
     * @param _nShininess shininess of material
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR) {
        this(_kD, _kS, _nShininess, _kT, _kR, 0);
    }


    public double get_kD() {
        return _kD;
    }

    public double get_ks() {
        return _kS;
    }

    /**
     * return the transparency factor
     *
     * @return double
     */
    public double get_kT() {
        return _kT;
    }

    /**
     * return the reflection factor
     *
     * @return double
     */
    public double get_kR() {
        return _kR;
    }

    /**
     * return the Shininess factor
     *
     * @return int
     */
    public int get_nShininess() {
        return _nShininess;
    }

    /**
     * return glossy of surface
     *
     * @return double
     */
    public double get_glossy() {
        return _glossy;
    }
}
