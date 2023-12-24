package one.empty3.apps.vecmesh;

import one.empty3.feature.M;
import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Rotate {
    private final Matrix33 matrixOrig;
    private boolean mouseDown = false;
    private int lastMouseX = 0;
    private int lastMouseY = 0;

    private Matrix33 RotationMatrix = new Matrix33();

    public Matrix33 getRotationMatrix() {
        return RotationMatrix;
    }

    public void setRotationMatrix(Matrix33 RotationMatrix) {
        this.RotationMatrix = RotationMatrix;
    }


    public Rotate(Representable r, JPanel jPanel) {
        this.matrixOrig = new Matrix33(new Point3D[] {r.getVectX(), r.getVectY(), r.getVectZ()});
        jPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseDown(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseUp(e);
                handleMouseMove(e);
                Point3D[] mult = getRotationMatrix().mult(matrixOrig).getColVectors();
                r.setVectX(mult[0]);
                r.setVectY(mult[1]);
                r.setVectZ(mult[2]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    void handleMouseDown(MouseEvent event) {
        mouseDown = true;
        lastMouseX = event.getX();
        lastMouseY = event.getY();
    }

    void handleMouseUp(MouseEvent event) {
        mouseDown = false;
    }

     void handleMouseMove(MouseEvent event) {
        if (!mouseDown) {
            return;
        }
        var newX = event.getX();
        var newY = event.getY();

        var deltaX = newX - lastMouseX;
        Matrix33 newRotationMatrix = new Matrix33();
         Matrix33 newRotationMatrixY = Matrix33.rotationY(2 * Math.PI * deltaX / 10 / 360);

         var deltaY = newY - lastMouseY;
         Matrix33 newRotationMatrixX = Matrix33.rotationX(2 * Math.PI * deltaY / 10 / 360);

         newRotationMatrix = newRotationMatrixX.mult(newRotationMatrixY);

        lastMouseX = newX;
        lastMouseY = newY;
    }
}
