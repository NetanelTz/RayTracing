package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

/**
 * class for list of geometries and implements intersectable interface
 *
 * @author Noam and Netanel
 */

public class Geometries implements Intersectable {
    private List<Intersectable> _listOfGeo;
    Box box;

    /**
     * default ctor
     */
    public Geometries() {
        this._listOfGeo = new LinkedList<>();
        createBox();
    }

    /**
     * ctor with few of geo and using build in function
     *
     * @param geometries list of geo
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
        createBox();
    }

    /**
     * constructor
     *
     * @param intersectables
     */
    public Geometries(List<Intersectable> intersectables) {
        this._listOfGeo = intersectables;
        createBox();
    }

    /**
     * return the size of this class (necessary for test unit for this class)
     *
     * @return int of class's size
     */
    public int getSize() {
        return this._listOfGeo.size();
    }

    /**
     * add geometries to list by using build in function
     * and update the box
     *
     * @param geometries list of geo
     */
    public void add(Intersectable... geometries) {
        if (box == null)
            createBox();
        double Xmax = box.get_maxX();
        double Xmin = box.get_minX();
        double Ymax = box.get_maxY();
        double Ymin = box.get_minY() ;
        double Zmax = box.get_maxZ();
        double Zmin = box.get_minZ();
        for (Intersectable inter : geometries) {
            Xmax = Math.max(Xmax, inter.getBox().get_maxX());
            Xmin = Math.min(Xmin, inter.getBox().get_minX());
            Ymax = Math.max(Ymax, inter.getBox().get_maxY());
            Ymin = Math.min(Ymin, inter.getBox().get_minY());
            Zmax = Math.max(Zmax, inter.getBox().get_maxZ());
            Zmin = Math.min(Zmin, inter.getBox().get_minZ());
        }
        box = new Box(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        this._listOfGeo.addAll(Arrays.asList(geometries));

    }

    /**
     * create new list for all points that ray intersect with all geo
     *
     * @param ray ray to find intersect
     * @return List of Point3D  listOfIntersections
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if (!box.inBox(ray))
            return null;
        List<GeoPoint> listOfIntersections = new LinkedList<>();

        List<GeoPoint> temp;
        for (Intersectable item : _listOfGeo) {
            temp = item.findIntersections(ray, maxDistance);//checking the intersection of every item
            if (temp != null)
                listOfIntersections.addAll(temp);
        }

        if (listOfIntersections.size() == 0)
            return null;
        else
            return listOfIntersections;
    }

    @Override
    public void createBox() {
        if (_listOfGeo.size() == 0) {
            box = new Box();
            return;
        }
        double _minX = _listOfGeo.get(0).getBox().get_minX();
        double _maxX = _listOfGeo.get(0).getBox().get_maxX();
        double _minY = _listOfGeo.get(0).getBox().get_minY();
        double _maxY = _listOfGeo.get(0).getBox().get_maxY();
        double _minZ = _listOfGeo.get(0).getBox().get_minZ();
        double _maxZ = _listOfGeo.get(0).getBox().get_maxZ();
        for (Intersectable geo : _listOfGeo) {
            if (_maxX < geo.getBox().get_maxX())
                _maxX = geo.getBox().get_maxX();
            if (_minX > geo.getBox().get_minX())
                _minX = geo.getBox().get_minX();
            if (_maxY < geo.getBox().get_maxY())
                _maxY = geo.getBox().get_maxY();
            if (_minY > geo.getBox().get_minY())
                _minY = geo.getBox().get_minY();
            if (_maxZ < geo.getBox().get_maxZ())
                _maxZ = geo.getBox().get_maxZ();
            if (_minZ > geo.getBox().get_minZ())
                _minZ = geo.getBox().get_minZ();
        }
        box = new Box(_minX,
                _maxX,
                _minY,
                _maxY,
                _minZ,
                _maxZ);
    }
    /**
     * BVH recursive algorithm. create binary tree.
     * @param deep
     */
  /*  @Override
    public void BVH(int deep) {
        if(_listOfGeo.size() < 2 || deep--  <= 0)
            return;
        int range = 2;
        Geometries[] geometriesArr = new Geometries[range];
        for (int i = 0; i < range; i++) {
            geometriesArr[i] = new Geometries();
        }
        for (Intersectable geo : this._listOfGeo){
            // check where the geo is closer.
            double toMax = geo.getBox().mid.distance(box._max);
            double toMin = geo.getBox().mid.distance(box._min);
            if (Math.min(toMax,toMin) == toMax)
                geometriesArr[0].add(geo);
            else
                geometriesArr[1].add(geo);
        }
        _listOfGeo.clear();

        for (int i = 0; i < range; i++) {
            this.add(geometriesArr[i]);
            if (deep > 0)
                geometriesArr[i].BVH(deep);
        }
    }
*/
    @Override
    public Box getBox() {
        return box;
    }
}
