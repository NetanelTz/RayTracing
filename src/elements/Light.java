package elements;

import primitives.Color;

/**
 * abstract class for every kind of light
 *
 * @author Noam and Netanel
 */
abstract class Light {

    protected Color _intensity;

    /**
     * ctor for light class for the intensity of color
     *
     * @param _intensity intensity of color
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }


    /**
     * get the intensity of color of this class
     *
     * @return color
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }

}
