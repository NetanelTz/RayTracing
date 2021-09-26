package elements;

import primitives.*;

/**
 * interface for light source
 *
 * @author Noam and Netanel
 */
public interface LightSource {
    public Color getIntensity(Point3D p);

    public Vector getL(Point3D p);

    double getDistance(Point3D p);
}
