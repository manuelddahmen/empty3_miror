/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.library.modelerview;

import java.util.HashMap;

public class CubesOrientation {
    public double size = 1.0;
    public enum CubesOrientationConstants { upFrontLeft, upFrontRight, downFrontLeft, downFrontRight,
        upBackLeft, upBackRight, downBackLeft, downBackRight }
    public enum OrientationConstants {up, right, down, left, front, back};
    public HashMap<CubesOrientationConstants, CubeProperty> properties = new HashMap<>();
    public CubesOrientation(double size) {
        this.size = size;
    }
}
