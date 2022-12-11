package one.empty3.apps.morph;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class ImageControls {
    boolean moving = false;
    boolean dropped = true;
    boolean clicked = false;
    boolean drags = false;
    public void initBools() {
        moving = false;
         dropped = true;
         clicked = false;
         drags = false;
    }

    public ImageControls(StructureMatrix<Point3D> grid, BufferedImage image, JPanel panelDisplay) {
        panelDisplay.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked = " + (clicked=true));
                clicked();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Released = " + (drags?(dropped=true):(dropped=false)));
                if(clicked==true&&dropped)
                    dragged();
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
                drags();

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    private void clicked() {
        //select();
    }

    private void drags() {
        //move if selected
    }

    private void dragged() {
        // drops if moved
    }
}
