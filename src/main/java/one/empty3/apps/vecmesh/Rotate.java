package one.empty3.apps.vecmesh;

import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.core.nurbs.ParametricSurface;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Rotate {
    private Representable representable;
    private final JPanel panel;
    private Matrix33 matrixObject;
    private boolean mouseDown = false;
    private int lastMouseX = 0;
    private int lastMouseY = 0;


    public Matrix33 getRotationMatrix() {
        this.matrixObject = new Matrix33(new Point3D[] {representable.getVectX(), representable.getVectY(),
                representable.getVectZ()});
        return matrixObject;
    }

    public void setRotationMatrix(Matrix33 rotationMatrix) {
        this.matrixObject = rotationMatrix;
        Point3D[] colVectors = rotationMatrix.getColVectors();
        representable.setVectX(colVectors[0]);
        representable.setVectY(colVectors[1]);
        representable.setVectZ(colVectors[2]);
    }


    public Rotate(Representable r, JPanel jPanel) {
        this.matrixObject = new Matrix33(new Point3D[] {r.getVectX(), r.getVectY(), r.getVectZ()});
        this.representable = r;
        this.panel = jPanel;
        jPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseDown(e);
                handleMouseMove(e);
                Point3D[] mult = getRotationMatrix().mult(matrixObject).getColVectors();
                r.setVectX(mult[0]);
                r.setVectY(mult[1]);
                r.setVectZ(mult[2]);
                matrixObject = new Matrix33(mult);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseUp(e);
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

    public JPanel getPanel() {
        return panel;
    }

    public Representable getRepresentable() {
        return representable;
    }

    public void setRepresentable(Representable parametricSurface) {
        this.representable = parametricSurface;
    }
}
