package one.empty3.apps.morph;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Consumer;

public class ImageControls implements Runnable {
    private final JPanel panelDisplay;
    private final BufferedImage image;
    private final StructureMatrix<Point3D> grid;
    boolean moving = false;
    boolean dropped = false;
    boolean clicked = false;
    boolean drags = false;
    private boolean isSelected;
    private boolean isPressed;
    private int RADIUS = 5;
    private Point3D selectedPoint;
    private int xGrid;
    private int yGrid;

    public ImageControls(StructureMatrix<Point3D> grid, BufferedImage image, JPanel panelDisplay) {
        this.grid = grid;
        this.image = image;
        this.panelDisplay = panelDisplay;
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

    public boolean isNear(Point3D candidate, Point3D gridPoint) {
        if (candidate.getX() - RADIUS / 2 >= gridPoint.getX() &&
                candidate.getX() + RADIUS / 2 <= gridPoint.getX()
                && candidate.getY() - RADIUS / 2 >= gridPoint.getY() &&
                candidate.getY() + RADIUS / 2 <= gridPoint.getY())
            return true;
        else
            return false;
    }
    private boolean selectPoint(int x, int y) {
        selectedPoint = null;
        Point3D point3D = new Point3D((double) x, (double) y, 0d);
        for (int i = 0; i < grid.data2d.size(); i++) {
            for (int j = 0; j < grid.data2d.get(i).size(); j++) {
                Point3D point3D1 = grid.data2d.get(i).get(j);
                if(isNear(point3D, point3D1)) {
                    selectedPoint = point3D;
                    this.xGrid = i;
                    this.yGrid = j;
                }
            }
        }
        return selectedPoint!=null;
    }
    private void dragPoint(int x, int y) {
        Point3D point3Dmoved = new Point3D((double) x, (double) y, 0d);
        if(selectedPoint!=null) {
            grid.setElem(point3Dmoved, this.xGrid, this.yGrid);
        }
    }

    public void run() {
        while(isRunning()) {
            display();
        }
    }

    private boolean isRunning() {
        return false;
    }

    private void display() {
        Graphics graphics = panelDisplay.getGraphics();
        grid.getData2d().forEach(new Consumer<List<Point3D>>() {
            @Override
            public void accept(List<Point3D> point3DS) {
                point3DS.forEach(new Consumer<Point3D>() {
                    @Override
                    public void accept(Point3D point3D) {
                        graphics.setColor(Color.BLACK);
                        graphics.drawOval((int)(double) point3D.getX()-RADIUS/2,
                                (int) (double)point3D.getY()-RADIUS/2,
                                RADIUS, RADIUS);
                    }
                });
            }
        });
    }
}
