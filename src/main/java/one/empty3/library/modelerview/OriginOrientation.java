/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.library.modelerview;

import one.empty3.library.Point3D;

public class OriginOrientation {
    Point3D origin;
    CubesOrientation [] cubes = new CubesOrientation[4];

    public OriginOrientation(Point3D origin) {
        this.origin = origin;
    }
}
