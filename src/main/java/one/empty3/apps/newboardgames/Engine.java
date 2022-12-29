/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.apps.newboardgames;

import one.empty3.apps.newboardgames.boards.Grid;
import one.empty3.library.Camera;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;

public class Engine implements Runnable {
    private final WindowDrawing window;
    private Board board;
    private boolean isRunning;
    private Level level;

    public Engine(WindowDrawing windowDrawing) {
        this.window = windowDrawing;
    }
    @Override
    public void run() {
        while (isRunning()) {
            updateCameraPosition(board, level);
            setCameraOn(window, board.camera());
            draw(board);
        }
    }

    private void setCameraOn(WindowDrawing window, Camera camera) {
        if(camera==null) {
            board.camera = new Camera(board.cellAt(0, 0).rotation.getElem().getCentreRot().data0d.plus(Point3D.Z.mult(board.heightView)),
                    board.cellAt(board.dimX, board.dimY).rotation.getElem().getCentreRot().data0d.plus(Point3D.Z.mult(board.heightView))

                    );
            camera = camera;
        }
        window.setCamera(camera);
    }

    private void updateCameraPosition(Board board, Level level) {

    }

    private void draw(Board board) {
        for(int i=0; i<board.getSize2D().getX(); i++)
            for(int j=0; j<board.getSize2D().getY(); j++) {
                drawCell(board.cellAt(i, j));
            }
    }

    private void drawCell(Representable cellAt) {
    }

    private boolean isRunning() {
        return isRunning;
    }

    public void loadLevel(String pathname) {
        level = new Level("");
        board = new Grid(10, 10, 5, 7);
    }

}
