package one.empty3.apps.morph;

import one.empty3.library.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageControls implements Runnable {
    private final JPanel panelDisplay;
    private final BufferedImage image;
    private final StructureMatrix<Point3D> grid;
    private final JFrame jframe;
    private final ITexture texture;
    boolean moving = false;
    boolean dropped = false;
    boolean clicked = false;
    boolean drags = false;
    RepresentableConteneur rc;
    private StructureMatrix<Point3D> gridUv;
    private boolean isSelected;
    private boolean isPressed;
    private double RADIUS = 5;
    private Point3D selectedPoint;
    private int xGrid;
    private int yGrid;
    private boolean running = false;
    private Scene scene;
    private Camera camera;
    private boolean displaying;
    private ZBuffer zBuffer;

    public ImageControls(JFrame jframe,
                         StructureMatrix<Point3D> grid, StructureMatrix<Point3D> gridUv, BufferedImage image,
                         JPanel panelDisplay, ITexture texture) {
        this.jframe = jframe;
        this.grid = grid;
        this.gridUv = gridUv;
        this.image = image;
        this.panelDisplay = panelDisplay;
        this.texture = texture;
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
                System.out.println("Selected point: " + grid.getElem(xGrid, yGrid));
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
            //System.out.println("::move a point");
        }
    }

    private void dragged(int x, int y) {
        // drops if moved
        if (!moving && !isPressed && isSelected) {
            System.out.println("::update a point position");
            System.out.println(selectedPoint);
            grid.setElem(selectedPoint, this.xGrid, this.yGrid);
            System.out.println(grid.getElem(xGrid, yGrid));
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
        double minDist = Double.MAX_VALUE;
        Point3D point3D;
        if ((camera == null) || (zBuffer == null)) {
            point3D = convertScreenCordToSceneCord(new Point3D((double) x, (double) y, 0d));
        } else {
            point3D = convertScreenCordToSceneCord(new Point3D((double) x, (double) y, 0d));
        }
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

    public void setRunning(boolean isRunning) {
        this.running = isRunning;
    }

    public Point3D convertSceneCordToScreenCord(Point3D pScene) {
        double x = pScene.getX() * panelDisplay.getWidth() / image.getWidth();
        double y = pScene.getY() * panelDisplay.getHeight() / image.getHeight();


        return new Point3D(x, y, 0d);

    }

    public Point3D convertScreenCordToSceneCord(Point3D pScreen) {
        double x = pScreen.getX();/// panelDisplay.getWidth() * image.getWidth();
        double y = pScreen.getY();/// panelDisplay.getHeight() * image.getHeight();

        return new Point3D(x, y, 0d);

    }

    private void display() {
        while (isDisplaying()) {
            try {
                Thread.sleep(80);
                // Temps d'attente max? Tuer thread.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        displaying = true;

        int resX = 400;//imageRead1.getWidth();
        int resY = 400;//imageRead1.getHeight();


        if (texture == null) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, "Error texture Movie or Image is null");
            return;
        }

        PolygonsDistinctUV polygons = new PolygonsDistinctUV();
        polygons.setCoefficients(grid);
        polygons.setUvMap(gridUv);
        polygons.texture(null);
        polygons.setTexture2(texture);

        scene = new Scene();

        scene.add(polygons);

        addToScene(scene);

        Point3D plus = Point3D.X.mult(
                resX / 2.).plus(Point3D.Y.mult(resY / 2.));

        camera = new Camera(Point3D.Z.mult(
                -Math.max(resX, resY)).plus(plus), plus);
        camera.declareProperties();
        camera.calculerMatrice(Point3D.Y);

        zBuffer = new ZBufferImpl(resX, resY);

        zBuffer.scene(scene);
        scene.cameraActive(camera);

        zBuffer.draw();

        BufferedImage image = zBuffer.image();

        ImageIcon imageIcon = new ImageIcon(image);

        JLabel jLabelResult = new JLabel(imageIcon);

        if (panelDisplay.getComponents().length > 0) {
            panelDisplay.remove(0);
        }
        panelDisplay.add(jLabelResult);

        jframe.pack();

        displaying = false;
    }

    public void addToScene(Scene scene) {
        rc = new RepresentableConteneur();
        for (int i = 0; i < grid.getData2d().size(); i++) {
            for (int j = 0; j < grid.getData2d().get(i).size(); j++) {
                Point3D point3D = grid.getData2d().get(i).get(j);
                Sphere sphere = new Sphere(new Axe(point3D.plus(Point3D.Y.mult(RADIUS / 2.)),
                        point3D.moins(Point3D.Y.mult(RADIUS / 2.))),
                        RADIUS);
                if (point3D.equals(selectedPoint)) {
                    sphere.texture(new ColorTexture(Color.RED));
                } else {
                    sphere.texture(new ColorTexture(Color.BLACK));
                }
                sphere.setIncrU(0.4);
                sphere.setIncrV(0.4);
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