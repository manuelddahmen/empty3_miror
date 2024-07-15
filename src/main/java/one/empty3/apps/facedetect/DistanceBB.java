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
import one.empty3.library1.shader.Mat;

import java.awt.geom.Dimension2D;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DistanceBB extends DistanceBezier2 {
    ;

    public DistanceBB(List<Point3D> A, List<Point3D> B, Dimension2D aDimReal, Dimension2D bDimReal) {
        super(A, B, aDimReal, bDimReal);
    }

    public Point3D findAxPointInB1(double u, double v) {
        Mat matA = new Mat(3, A.size());
        Mat matB = new Mat(B.size(), 3);
        for (int i = 0; i < A.size(); i++) {
            matA.set(0, i, A.get(i).getX());
            matA.set(1, i, A.get(i).getY());
            matA.set(2, i, A.get(i).getZ());
            matA.set(i, 0, B.get(i).getX());
            matA.set(i, 1, B.get(i).getY());
            matA.set(i, 2, B.get(i).getZ());

        }
        Mat product = matA.product(matB);

        Mat mat = new Mat(3, 1);
        mat.set(0, 0, u);
        mat.set(1, 0, v);
        mat.set(2, 0, 1);

        Mat product1 = product.product(mat);

        return new Point3D(product1.get(0, 0), product1.get(0, 1), product1.get(0, 2));
    }

    @Override
    public Point3D findAxPointInB(double u, double v) {
        return findAxPointInB5(u, v);
    }

    public Point3D findAxPointInB5(double u, double v) {
        Point3D searched = null;
        searched = new Point3D(u, v, 0.0);
        double distance = Double.MAX_VALUE;
        Point3D found = searched.multDot(new Point3D(bDimReduced.getWidth(), bDimReduced.getHeight(), 0.0));
        if (isInvalidArray()) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "DistanceAB, array is invalid");
            return found;
        }
        for (int i = 0; i < bDimReduced.getWidth(); i++) {
            for (int j = 0; j < bDimReduced.getHeight(); j++) {
                Double dist = Point3D.distance(sBij[i][j], searched);
                if (dist < distance) {
                    distance = dist;
                    found = new Point3D(i / bDimReduced.getWidth(), j / bDimReduced.getHeight(), 0.0);
                    //found = new Point3D(((double) i), ((double) j), 0.0);
                    //found = new Point3D((double) i, (double) j, 0.0);
                }
            }
        }
        return found;
    }
}
