/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.apps.newboardgames;

import one.empty3.library.*;

import java.util.List;

public abstract class Board extends Representable {


    protected int dimX;
    protected int dimY;
    protected int heightView;
    protected int medianDistView;
    protected Camera camera;

    public Camera camera() {
        return camera;
    }
    public abstract Point2D getSize2D();
    public abstract Point3D getSize3D();
    protected abstract List<Character> getCharacters();

    public Representable cellAt(int i, int j) {
        return new Cell(this, i, j);
    }
}
