package one.empty3.apps.morph;

import one.empty3.library.*;

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
    private boolean running = false;
    private Scene scene;
    RepresentableConteneur rc;
    private boolean displaying;

    public ImageControls(StructureMatrix<Point3D> grid, BufferedImage image,
                         JPanel panelDisplay) {
        this.grid = grid;
        this.image = image;
        this.panelDisplay = panelDisplay;
        setRunning(true);
        panelDisplay.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked = " + (clicked = true));
                clicked();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Pressed : " + (isPressed = true));
                isSelected = selectPoint(e.getX(), e.getY());
                moving = true;
                drags();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Pressed : " + (isPressed = false));
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

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public StructureMatrix<Point3D> getMatrice() {
        return grid;
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
        if (isPressed) {
            System.out.println("::move a point");
        }
    }

    private void dragged(int x, int y) {
        // drops if moved
        if (!moving && !isPressed && isSelected) {
            System.out.println("::update a point position");
            display();
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
        double minDist = 1000d;
        Point3D point3D = convertScreenCordToSceneCord(new Point3D((double) x, (double) y, 0d));
        for (int i = 0; i < grid.data2d.size(); i++) {
            for (int j = 0; j < grid.data2d.get(i).size(); j++) {
                Point3D point3D1 = grid.data2d.get(i).get(j);
                double distanceActuelle = Point3D.distance(point3D, point3D1);
                if (distanceActuelle < minDist) {
                    selectedPoint = point3D;
                    this.xGrid = i;
                    this.yGrid = j;
                    minDist = distanceActuelle;
                }
            }
        }
        return selectedPoint != null;
    }

    private void dragPoint(int x, int y) {
        Point3D point3dMoved = convertScreenCordToSceneCord(new Point3D((double) x, (double) y, 0d));
        if (selectedPoint != null) {
            grid.setElem(point3dMoved, this.xGrid, this.yGrid);
        }
    }

    public void run() {
        while (isRunning()) {
            display();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isRunning() {
        return running;
    }

    private void setRunning(boolean isRunning) {
        this.running = isRunning;
    }

    public Point3D convertSceneCordToScreenCord(Point3D pScene) {
        double x = pScene.getX() * panelDisplay.getWidth() / image.getWidth();
        double y = pScene.getY() * panelDisplay.getHeight() / image.getHeight();


        return new Point3D(x, y, 0d);

    }

    public Point3D convertScreenCordToSceneCord(Point3D pScreen) {
        double x = pScreen.getX() / panelDisplay.getWidth() * image.getWidth();
        double y = pScreen.getY() / panelDisplay.getHeight() * image.getHeight();

        return new Point3D(x, y, 0d);

    }

    private void display() {
        StructureMatrix<Point3D> copy = null;
        try {
            copy = grid.copy();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (CopyRepresentableError e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        if (copy != null) {
            while(isDisplaying()) {
                try {
                    Thread.sleep(80);
                    // Temps d'attente max? Tuer thread.
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            displaying = true;

            Polygons polygons1 = new Polygons();
            polygons1.setCoefficients(grid);

            Polygons polygons2 = new Polygons();
            polygons2.setCoefficients(grid);

            int resX = 400;//imageRead1.getWidth();
            int resY = 400;//imageRead1.getHeight();


            ImageTexture imageTexture = new ImageTexture(new ECBufferedImage(image));

            Polygons polygons = new Polygons();
            polygons.setCoefficients(copy);
            polygons.texture(imageTexture);

            rc = new RepresentableConteneur();
            grid.getData2d().forEach(point3DS -> point3DS.forEach(point3D ->
                    rc.add(new Sphere(new Axe(point3D.plus(Point3D.Y),
                            point3D.plus(Point3D.Y.mult(-1))),
                            2))));


            Scene scene = new Scene();
            scene.add(polygons);

            addToScene(scene);

            Point3D plus = Point3D.X.mult(
                    resX / 2.).plus(Point3D.Y.mult(resY / 2.));

            Camera camera = new Camera(Point3D.Z.mult(
                    Math.max(resX, resY)).plus(plus), plus);
            camera.declareProperties();
            camera.calculerMatrice(Point3D.Y);

            ZBufferImpl zBuffer = new ZBufferImpl(resX, resY);
            zBuffer.scene(scene);
            scene.cameraActive(camera);

            zBuffer.draw();
            BufferedImage image = zBuffer.image2();

            ImageIcon imageIcon = new ImageIcon(image);

            JLabel jLabelResult = new JLabel(imageIcon);

            if (panelDisplay.getComponents().length > 0) {
                panelDisplay.remove(0);
            }
            panelDisplay.add(jLabelResult);

            displaying = false;
        }


        Graphics graphics = panelDisplay.getGraphics();
        grid.getData2d().forEach(point3DS -> point3DS.forEach(point3D -> {


        }));
    }

    public void addToScene(Scene scene) {
        rc = new RepresentableConteneur();
        for (int i = 0; i < grid.getData2d().size(); i++) {
            for (int j = 0; j < grid.getData2d().get(i).size(); j++) {
                Point3D point3D = grid.getData2d().get(i).get(j);
                Sphere sphere = new Sphere(new Axe(point3D.plus(Point3D.Y.mult(RADIUS / 2)),
                        point3D.moins(Point3D.Y.mult(RADIUS / 2))),
                        RADIUS);
                sphere.texture(new ColorTexture(Color.BLACK));
                rc.add(sphere);
            }
        }
        scene.add(rc);
    }

    public boolean isDisplaying() {
        return displaying;
    }

    public void setDisplaying(boolean displaying) {
        this.displaying = displaying;
    }
}
