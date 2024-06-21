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

import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

public class DistanceAB {
    private Dimension2D bDim;
    private Dimension2D aDim;
    private List<Point3D> A;
    private List<Point3D> B;
    private double pixMa[][][];
    private double pixMb[][][];

    public DistanceAB(List<Point3D> A, List<Point3D> B, Dimension2D Adim, Dimension2D Bdim) {
        this.A = A;
        this.B = B;
        this.aDim = Adim;
        this.bDim = Bdim;

        pixMa = new double[(int) aDim.getWidth()][(int) aDim.getHeight()][A.size()];
        for (double i = 0; i < aDim.getWidth(); i++) {
            for (double j = 0; j < aDim.getHeight(); j++) {
                for (int k = 0; k < A.size(); k++) {
                    pixMa[(int) i][(int) j][k] = distance(A.get(k), new Point3D(i, j, 0.0));
                }
            }
        }


        pixMb = new double[(int) bDim.getWidth()][(int) bDim.getHeight()][B.size()];
        for (double i = 0; i < bDim.getWidth(); i++) {
            for (double j = 0; j < bDim.getHeight(); j++) {
                for (int k = 0; k < B.size(); k++) {
                    pixMb[(int) i][(int) j][k] = distance(B.get(k), new Point3D(i, j, 0.0));
                }
            }
        }
    }

    public Point3D findAxPointInB(int x, int y) {
        Point3D p3 = Point3D.O0;
        List<Integer> orderedIndexes = order(A, x, y, pixMa);
        for (int j = 0; j < A.size(); j++) {
            p3 = searchInB(orderedIndexes, pixMa, pixMb);
        }
        return p3;
    }

    private Point3D searchInB(List<Integer> orderedIndexes, double[][][] pixMa, double[][][] pixMb) {
        for (int x = 0; x < pixMb.length; x++) {
            for (int y = 0; y < pixMb[x].length; y++) {
                List<Integer> order = order(B, x, y, pixMb);
                for (int i = 0; i < B.size(); i++) {
                    if (orderedIndexes.equals(order)) {
                        return new Point3D((double) x, (double) y, 0.0);
                    }
                }
            }
        }
        return Point3D.O0;
    }

    private List<Integer> order(List<Point3D> a, int x, int y, double[][][] pXYa) {
        List<Integer> ints = new ArrayList<>();

        for (int j = 0; j < A.size(); j++) {
            int min = min(pXYa[x][y], ints);
        }
        return ints;
    }

    private int min(double[] listValue, List<Integer> excepts) {
        double min = Double.MAX_VALUE;
        int curMin = -1;
        for (int i = 0; i < listValue.length; i++) {
            if (!excepts.contains(i) && listValue[i] < min) {
                min = listValue[i];
                curMin = i;
            }
        }
        excepts.add(curMin);
        return curMin;
    }

    private double distance(Point3D A, Point3D B) {
        return Math.sqrt(Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2) + Math.pow(A.getZ() - B.getZ(), 2));
    }
}
