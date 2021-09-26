
package elements;

import primitives.Color;

/**
 * class for Ambient Light of the scene
 *
 * @author Noam and Netanel
 */
public class AmbientLight extends Light {

    /**
     * CTOR for class
     *
     * @param iA the color
     * @param kA the intensity to scale
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }

}

