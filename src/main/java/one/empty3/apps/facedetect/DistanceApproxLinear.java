/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.apps.facedetect;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.SurfaceParametriquePolynomiale;

import java.awt.geom.Dimension2D;
import java.util.List;

public class DistanceApproxLinear extends DistanceBezier2 {
    ;

    public DistanceApproxLinear(List<Point3D> A, List<Point3D> B, Dimension2D aDimReal, Dimension2D bDimReal) {
        super(A, B, aDimReal, bDimReal);
    }


    @Override
    public Point3D findAxPointInB(double u, double v) {
        return findAxPointInBal1(u, v);
    }

    private Point3D findAxPointInBal1(double u, double v) {
        Point3D pb = nearLandmark(u, v);
        Point3D pa = surfaceA.getCoefficients().getElem((int) (double) pb.get(0),
                (int) (double) pb.get(1));
        return pa;
    }

    /***
     *
     * @param u
     * @param v
     * @return
     */
    private Point3D nearLandmark(double u, double v) {
        Point3D uv = new Point3D(u, v, 0.0);
        double distance = Double.MAX_VALUE;
        int indexI = -1, indexJ = -1;
        for (int i = 0; i < listBX.size(); i++) {
            for (int j = 0; j < listBY.size(); j++) {
                if (Point3D.distance(surfaceB.getCoefficients().getElem(i, j), uv) < distance) {
                    indexI = i;
                    indexJ = j;
                    distance = Point3D.distance(surfaceB.getCoefficients().getElem(i, j), uv);
                }
            }
        }
        return new Point3D((double) indexI, (double) indexJ, 0.0);
    }

    /***
     *
     * @param u
     * @param v
     * @return
     */
    private Point3D nearLandmark1(double u, double v) {
        Point3D uv = new Point3D(u, v, 0.0);
        Point3D uvFace = model.findUvFace(u, v);
        double distance = Double.MAX_VALUE;
        int indexI = -1, indexJ = -1;
        for (int i = 0; i < listBX.size(); i++) {
            for (int j = 0; j < listBY.size(); j++) {
                Point3D elem = surfaceB.getCoefficients().getElem(i, j);
                if (Point3D.distance(model.findUvFace(elem.getX(), elem.getY()), uvFace) < distance) {
                    indexI = i;
                    indexJ = j;
                    distance = Point3D.distance(surfaceB.getCoefficients().getElem(i, j), uv);
                }
            }
        }
        return new Point3D((double) indexI, (double) indexJ, 0.0);
    }

    /***
     *
     * @param textureCord2D
     * @param listXX
     * @param listXY
     * @param surfaceX
     * @return
     */
    Point3D percentList(Point3D textureCord2D, List<Point3D> listXX, List<Point3D> listXY, SurfaceParametriquePolynomiale surfaceX) {
        return null;
    }
}
