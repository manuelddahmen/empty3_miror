/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.apps.newboardgames;

import one.empty3.feature.app.replace.java.awt.Color;
import one.empty3.library.*;

public class Cell extends RepresentableConteneur {
    private Board board;

    public Cell(Board board, int x, int y) {
        this.board = board;
        Point3D[] point3DS = {
                Point3D.X.mult(x).plus(Point3D.Y.mult(y)),
                Point3D.X.mult(x + 1).plus(Point3D.Y.mult(y)),
                Point3D.X.mult(x + 1).plus(Point3D.Y.mult(y + 1)),
                Point3D.X.mult(x).plus(Point3D.Y.mult(y + 1))
        };
        Polygon polygon = new Polygon(point3DS, new ColorTexture(new Color(1f / (x + 1), 1f / (y + 1), 0.5f)));
        add(polygon);
    }

}
