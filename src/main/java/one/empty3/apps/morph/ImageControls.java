package one.empty3.apps.morph;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class ImageControls {
    boolean moving = false;
    boolean dropped = false;
    boolean clicked = false;
    boolean drags = false;
    private boolean isSelected;
    private boolean isPressed;

    public ImageControls(StructureMatrix<Point3D> grid, BufferedImage image, JPanel panelDisplay) {
        panelDisplay.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked = " + (clicked = true));
                clicked();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Pressed : " + (isPressed=true));
                isSelected = selectPoint(e.getX(), e.getY());
                moving = true;
                drags();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Pressed : " + (isPressed=false));
                moving = false;
                dropped = true;
                dragged(e.getX(), e.getY());

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        panelDisplay.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }


    public void initBools() {
        moving = false;
        dropped = false;
        clicked = false;
        drags = false;
        isSelected = false;
        isPressed = false;
    }

    private void clicked() {
        //select();
//        System.out.println("::select a point");
//        isSelected = true;
    }

    private void drags() {
        //move if selected
        moving = true;
        if(isPressed) {
            System.out.println("::move a point");
        }
    }

    private void dragged(int x, int y) {
        // drops if moved
        if (!moving && !isPressed && isSelected) {
            System.out.println("::update a point position");
        }
        initBools();

    }
    private boolean selectPoint(int x, int y) {
        Point3D point3D = new Point3D((double) x, (double) y, 0d);
        return true;
    }
    private void dragPoint(int x, int y) {
        Point3D point3D = new Point3D((double) x, (double) y, 0d);
    }
}
