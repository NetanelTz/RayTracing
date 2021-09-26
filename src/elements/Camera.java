package elements;


import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * class to define Camera in scene
 *
 * @author Noam and Netanel
 */

public class Camera {
    Point3D _p0;//position of camera in scene
    Vector _vTo, _vUp, _vRight;//direction of camera


    /**
     * ctor with 2 vectors and 1 point
     * to locate camera
     *
     * @param p0  point that camera stand
     * @param vTo vector toward the cam
     * @param vUp vector to up the cam
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException(" 'up' and 'to' vectors are not perpendicular");
        }
        this._vTo = vTo.normalize();
        this._vUp = vUp.normalize();
        this._vRight = this._vUp.crossProduct(this._vTo).normalize();
        this._p0 = p0;
    }
//get all info of camera

    /**
     * get initial point
     *
     * @return Point3D
     */
    public Point3D get_p0() {
        return _p0;
    }

    /**
     * get toward vector
     *
     * @return Vector
     */
    public Vector get_vTo() {
        return _vTo;
    }

    /**
     * get up vector
     *
     * @return Vector
     */
    public Vector get_vUp() {
        return _vUp;
    }

    /**
     * get right vector
     *
     * @return Vector
     */
    public Vector get_vRight() {
        return _vRight;
    }


    /**
     * gives ray for each pixel
     * according lab slide shows
     *
     * @param nX             pixels of x row
     * @param nY             pixels of y row
     * @param j              specific "cube" at x row
     * @param i              specific "cube" at y row
     * @param screenDistance distance from screen
     * @param screenWidth    width of screen
     * @param screenHeight   height of screen
     * @return Ray
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j,
                                        int i, double screenDistance, double screenWidth, double screenHeight) {
        Point3D center = this._p0.add(this._vTo.scale(screenDistance));
        double r_y = screenHeight / (double) nY;
        double r_x = screenWidth / (double) nX;

        double y_i = ((double) i - (double) nY / 2.0) * r_y + r_y / 2.0;
        double x_j = ((double) j - (double) nX / 2.0) * r_x + r_x / 2.0;

        Point3D resPoint;
        if (isZero(y_i) && isZero(x_j)) {
            resPoint = center;
        } else if (isZero(x_j)) {
            resPoint = center.add(this._vUp.scale(-y_i));
        } else if (isZero(y_i)) {
            resPoint = center.add(this._vRight.scale(x_j));
        } else {
            resPoint = center.add(this._vRight.scale(x_j).subtract(this._vUp.scale(y_i)));
        }
        return new Ray(resPoint.subtract(this._p0), this._p0);

    }

    /**
     * override toString method
     *
     * @return String
     */

    @Override
    public String toString() {
        return "Camera{" +
                "_p0=" + _p0 +
                ", _vTo=" + _vTo +
                ", _vUp=" + _vUp +
                ", _vRight=" + _vRight +
                '}';
    }
}
