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
import one.empty3.library.core.nurbs.SurfaceParametriquePolynomialeBezier;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

public class DistanceBezier {
    private final Dimension2D aDimReal;
    private final Dimension2D bDimReal;
    private final List<Point3D> A;
    private final List<Point3D> B;
    private final SurfaceParametriquePolynomialeBezier surfaceA;
    private final SurfaceParametriquePolynomialeBezier surfaceB;
    private final Point3D[][] sAij;
    private final Point3D[][] sBij;
    private final Dimension2D aDimReduced = new Dimension(80, 80);
    private final Dimension2D bDimReduced = new Dimension(80, 80);

    public DistanceBezier(List<Point3D> A, List<Point3D> B, Dimension2D aDimReal, Dimension2D bDimReal) {
        this.A = A;
        this.B = B;
        this.aDimReal = aDimReal;
        this.bDimReal = bDimReal;

        List<Double> listAX = new ArrayList<>();
        List<Double> listAY = new ArrayList<>();
        List<Double> listBX = new ArrayList<>();
        List<Double> listBY = new ArrayList<>();

        for (int i = 0; i < A.size(); i++) {
            listAX.add(A.get(i).getX());
            listAY.add(A.get(i).getY());
            listBX.add(B.get(i).getX());
            listBY.add(B.get(i).getY());
        }

        listAX.sort(Double::compare);
        listAY.sort(Double::compare);
        listBX.sort(Double::compare);
        listBY.sort(Double::compare);

        surfaceA = new SurfaceParametriquePolynomialeBezier();
        surfaceB = new SurfaceParametriquePolynomialeBezier();

        for (int i = 0; i < A.size(); i++) {
            for (int j = 0; j < B.size(); j++) {
//                int i1 = (int) Math.min((double) (i % ((int) Math.sqrt(A.size() )+ 1)) * (Math.sqrt(A.size() )+ 1), A.size() - 1);
//                int j1 = (int) Math.min((double) (j / ((int) Math.sqrt(B.size() )+ 1)) * (Math.sqrt(A.size() )+ 1), B.size() - 1);


                surfaceA.getCoefficients().setElem(new Point3D(listAX.get(i), listAY.get(j), 0.0), i, j);
                surfaceB.getCoefficients().setElem(new Point3D(listBX.get(i), listBY.get(j), 0.0), i, j);
            }
        }
        sAij = new Point3D[(int) this.aDimReduced.getWidth()][(int) this.aDimReduced.getHeight()];
        sBij = new Point3D[(int) this.bDimReduced.getWidth()][(int) this.bDimReduced.getHeight()];

        precomputeA();
        precomputeB();

    }

    public Point3D findAxPointInB(double u, double v) {
        Point3D searched = new Point3D(u, v, 0.0);
        double distance = Double.MAX_VALUE;
        Point3D found = searched;

        searched = sAij[(int) Math.min((u * aDimReduced.getWidth())
                , aDimReduced.getWidth() - 1)][(int) Math.min((v * aDimReduced.getHeight())
                , aDimReduced.getHeight() - 1)];
        for (int i = 0; i < bDimReduced.getWidth(); i++)
            for (int j = 0; j < bDimReduced.getHeight(); j++) {
                Double dist = Point3D.distance(sBij[i][j], searched);
                if (dist < distance) {
                    distance = dist;
                    found = new Point3D(i / bDimReduced.getWidth(), j / bDimReduced.getHeight(), 0.0);
                }
            }


        //return sAij[(int) (found.getX() * aDim.getWidth())]
        //        [(int) (found.getX() * aDim.getHeight())];
        return sAij[(int) Math.min((found.getX() * aDimReduced.getWidth())
                , aDimReduced.getWidth() - 1)][(int) Math.min((found.getY()
                        * aDimReduced.getHeight())
                , aDimReduced.getHeight() - 1)];
    }

    public void precomputeA() {
        for (int i = 0; i < aDimReduced.getWidth(); i++)
            for (int j = 0; j < aDimReduced.getHeight(); j++) {
                Point3D tried = new Point3D(1.0 * i / aDimReduced.getWidth(), 1.0 * j / aDimReduced.getHeight(), 0.0);
                sAij[i][j] = surfaceA.calculerPoint3D(tried.getX(), tried.getY());

            }
    }

    public void precomputeB() {
        for (int i = 0; i < bDimReduced.getWidth(); i++)
            for (int j = 0; j < bDimReduced.getHeight(); j++) {
                Point3D tried = new Point3D(1.0 * i / bDimReduced.getWidth(), 1.0 * j / bDimReduced.getHeight(), 0.0);
                sBij[i][j] = surfaceB.calculerPoint3D(tried.getX(), tried.getY());

            }
    }
}
