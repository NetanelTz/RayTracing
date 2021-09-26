package scene;

import elements.*;
import geometries.*;
import primitives.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * class to scene from camera
 *
 * @author Noam and Netanel
 */
public class Scene {

    private final String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries;
    private Camera _camera;
    private double _distance;

    List<LightSource> _lights = new LinkedList<LightSource>();

    public List<LightSource> get_lights() {
        return _lights;
    }

    public void addLights(LightSource... lights) {
        _lights.addAll(Arrays.asList(lights));
    }

    /**
     * ctor just with the name of class
     *
     * @param name string of the scene
     */
    public Scene(String name) {
        this._name = name;
        //initial the geo
        this._geometries = new Geometries();
    }

    /**
     * return the name of scene
     *
     * @return string
     */
    public String getName() {
        return _name;
    }

    /**
     * return the color of background
     *
     * @return color
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * set the color of background
     *
     * @param _background color of background
     */
    public void setBackground(Color _background) {
        this._background = _background;
    }

    /**
     * return the Ambient Light of scene
     *
     * @return AmbientLight
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * set the the Ambient Light of scene
     *
     * @param _ambientLight the Ambient Light of scene
     */
    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * return geo of scene
     *
     * @return Geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * set the geo of scene
     *
     * @param _geometries geo of scene
     */
    public void setGeometries(Geometries _geometries) {
        this._geometries = _geometries;
    }

    /**
     * return the camera of scene
     *
     * @return camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * set the camera of scene
     *
     * @param _camera camera of scene
     */
    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * get the dis from camera to view plain
     *
     * @return double of dis
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * set the dis from cam to view plain
     *
     * @param _distance the dis from cam to view plain
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    /**
     * add geo to scene
     *
     * @param geometries collection of geo
     */
    public void addGeometries(Intersectable... geometries) {
        if (_geometries == null)//if it's the first geo
            this._geometries = new Geometries(geometries);
        else// we add a geo already
            _geometries.add(geometries);

    }
}

