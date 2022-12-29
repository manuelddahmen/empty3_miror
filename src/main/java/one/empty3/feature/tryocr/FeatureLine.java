/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.feature.tryocr;

import one.empty3.library.Point3D;

import java.awt.*;

public class FeatureLine {
    private Point3D p0, p1;
    static double[][] featLine = {
            {0, 0, 0, 0},
            {0, 0, 0, 1},
            {0, 0, 1, 0},
            {0, 1, 0, 0},
            {1, 0, 0, 0},
    {0, 0, 1,1},
    {0, 1, 0, 1},
    {0, 1, 1,1},
    {1,0,0,1},
    {1, 0, 1, 0},
    {1, 0, 1, 1},
    {1, 1, 0, 0},
    {1, 1, 0, 1},
    {1, 1, 1, 0},
    {1, 1, 1, 1}};

    public static Point3D getFeatLine(int line, int i) {
        return new Point3D(featLine[line][i*2], featLine[line][i*2+1],0.0) ;
    }
}
