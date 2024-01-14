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

package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricSurface;

public class OrientableParametricSurface extends ParametricSurface {
    ParametricSurface ps;
    public OrientableParametricSurface(ParametricSurface ps) {
        this.ps = ps;
    }
    public void setAxes(Point3D o, Point3D vx, Point3D vy, Point3D vz) {
        ps.setOrig(o);
        ps.setVectX(vx);
        ps.setVectY(vy);
        ps.setVectZ(vz);
    }
    public Point3D calculerPoint3D(double u, double v) {
        Point3D point3D = ps.calculerPoint3D(u, v);
        return getOrientedPoint(point3D);
    }
}
