/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * 2013 Manuel Dahmen
 */
package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.SurfaceElem;

import java.awt.*;

/*__
 * @author Manuel
 */
public class Polygon extends Representable implements SurfaceElem, ClosedCurve {

    /*__
     *
     */
    private StructureMatrix<Point3D> points = new StructureMatrix<>(1, Point3D.class);

    public Polygon() {
        super();
        declareProperties();
    }

    public Polygon(Color c) {
        this();
        texture(new TextureCol(c));
    }

    public Polygon(ITexture c) {
        this();
        texture(c);
    }

    public Polygon(Point3D[] list, Color c) {
        this(list, new TextureCol(c));
    }

    public Polygon(Point3D[] list, ITexture c) {
        this();
        this.texture = c;
        points.setAll(list);
    }

    public void add(Point3D point3D) {
        int newLength;
        if (points == null) {
            points = new StructureMatrix<>(1, Point3D.class);
        }
        else {
            newLength = points.getData1d().size() + 1;
            java.util.List<Point3D> tmp = points.getData1d();
            points = new StructureMatrix<>(1, Point3D.class);
            for (int i = 0; i < tmp.size(); i++)
            {
                points.setElem(tmp.get(i), i);
            }
            points.setElem(point3D, newLength - 1);
        }
        declareProperties();
    }


    public StructureMatrix<Point3D> getPoints() {
        return points;
    }

    public void setPoints(Point3D[] points) {
        this.points.setAll(points);
        declareProperties();
    }


    @Override
    public String toString() {
        String t = "poly (\n\t(";
        for (Point3D p : points.getData1d()) {
            t += "\n\t\t" + (p==null?"null":p.toString());
        }
        t += "\n\t)\n\t" + (texture == null ? "" : texture.toString()) + "\n)\n\n";
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polygon polygone = (Polygon) o;

        return getPoints() != null ? getPoints().equals(polygone.getPoints()) : polygone.getPoints() == null;

    }

    @Override
    public int hashCode() {
        return getPoints() != null ? getPoints().hashCode() : 0;
    }

    public Point3D getIsocentre() {
        Point3D p = Point3D.O0;

        for (Point3D p0 : points.getData1d()) {
            p = p.plus(p0);
        }
        return p.mult(1. / points.getData1d().size());
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("points/point 0 à N du Polygone", points);

    }
/*
    @Override
    public Point3D calculerPoint3D(double t) {
        int size = points.getData1d().size();
        int i = (int) t * size;
        if (i >= size)
            i = size - 1;
        int j = (i + 1) >= size? i : i + 1;
        Point3D p1 = points.getData1d().get(i);
        Point3D p2 = points.getData1d().get(j);
        double d = t - 1.0 * i / size;
        return p1.plus(p2.moins(p1).mult(1 - d));
    }
*/
}
