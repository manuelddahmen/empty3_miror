/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.apps.newboardgames.boards;

import one.empty3.apps.newboardgames.Board;
import one.empty3.apps.newboardgames.Cell;
import one.empty3.apps.newboardgames.Character;
import one.empty3.library.Camera;
import one.empty3.library.Point2D;
import one.empty3.library.Point3D;
import one.empty3.library.core.math.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Grid extends Board {


    private Matrix matrix = null;

    public Grid(int dimX, int dimY, int heightView, int medianDistView) {

        this.dimX = dimX;
        this.dimY = dimY;
        this.heightView = heightView;
        this.medianDistView = medianDistView;

        Matrix matrix = new Matrix(dimX, dimY, heightView);
    }
    @Override
    public Camera camera() {
        if(camera==null) {
            camera = new Camera(Point3D.Y.mult(-medianDistView), Point3D.O0, Point3D.Z);
        }
        return camera;
    }

    @Override
    public Point2D getSize2D() {
        return new one.empty3.library.Point2D((double)dimX, (double)dimY);
    }

    @Override
    public Point3D getSize3D() {
        return new Point3D((double)dimX, (double)dimY, (double)heightView);
    }
    @Override
    protected List<Character> getCharacters() {
        return new ArrayList<>();
    }
}
