package one.empty3.testscopy.tests;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.*;
import one.empty3.library.core.nurbs.PcOnPs;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.library.core.tribase.CurveSurface;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

public class TestEarth extends TestObjetSub {
    public static final int SECONDS = 3;
    public static final int FPS = 25;
    private final File planets = new File("res\\img\\planets");
    private File[] planetsImagesFiles;
    private int i = -1;
    private BufferedImage image;
    private Sphere sphere;
    private Logger logger;
    private Point3D axe = Point3D.Z;
    private Circle circle;

    @Override
    public void ginit() {
        logger = Logger.getLogger(this.getClass().getCanonicalName());
        planetsImagesFiles = planets.listFiles();
        sphere = new Sphere(new Axe(axe.mult(1.0), axe.mult(-1)), 2.0);
        sphere.texture(new ColorTexture(Color.WHITE));

        z().setDisplayType(ZBufferImpl.SURFACE_DISPLAY_COL_QUADS);

        z().texture(new ColorTexture(Color.BLACK));
        scene().texture(new ColorTexture(Color.BLACK));


        scene().add(sphere);

        PcOnPs pcOnPs = new PcOnPs(sphere, new LineSegment(new Point3D(0., 0.5, 0.),
                new Point3D(1., 0.5, 0.)));
        pcOnPs.texture(new TextureCol(Color.GREEN));

        //scene().add(pcOnPs);
        sphere.setIncrU(.003);
        sphere.setIncrV(.003);

        circle = sphere.getCircle();

        //scene().add(circle);
    }

    @Override
    public void testScene() throws Exception {

    }

    @Override
    public void afterRenderFrame() {

    }

    public void incr() {
        i++;
        if (i < planetsImagesFiles.length)
            image = ImageIO.read(planetsImagesFiles[i]);
        else return;
        TextureImg textureImg = new TextureImg(new ECBufferedImage(image));
        sphere.texture(textureImg);

        Point3D p1axe = axe;

        //circle = new Circle(new Axe(p1axe.mult(1), p1axe.mult(-1)), 5.0);
        System.out.println("Planets:" + i + "/" + planetsImagesFiles.length);
    }

    public void vectDirRotate(Point3D vectOrigX, Point3D vectOrigy, double ratio,
                              Point3D outX, Point3D outY) {
        outX.changeTo(vectOrigX.mult(Math.cos(2*Math.PI*ratio)).plus(
                vectOrigy.mult(Math.sin(2*Math.PI*ratio))));
        outY.changeTo(vectOrigX.mult(-Math.sin(2*Math.PI*ratio)).plus(
                vectOrigy.mult(Math.cos(2*Math.PI*ratio))));
    }

    @Override
    public void finit() throws Exception {
        if ((frame() %( FPS * SECONDS) == 1)) {
            incr();
        }
        double v = (frame() % (FPS * SECONDS)) / (1.0 * FPS * SECONDS);
        camera(new Camera(circle.calculerPoint3D
                (v).mult(5.0),
                Point3D.O0, axe));
        camera().calculerMatrice(Point3D.Z);
        circle.setVectX(Point3D.Z.mult(Math.cos(2*Math.PI*v)));
        circle.setVectY(Point3D.X.mult(Math.sin(2*Math.PI*v)));
        circle.setVectZ(Point3D.Y);

        vectDirRotate(Point3D.Z, Point3D.X, v, circle.getVectX(), circle.getVectY());

        camera().calculerMatrice(axe);
        System.out.println("Camera t : "+v);
        scene().cameras().clear();
        camera(camera());
        z().scene().cameraActive(camera());
        scene().cameraActive(camera());
        z().camera(camera());
    }

    @Override
    public void afterRender() {

    }

    public static void main(String[] args) {
        TestEarth testEarth = new TestEarth();
        testEarth.loop(true);
        testEarth.setResolution(1024, 768);
        testEarth.setMaxFrames(9*FPS*SECONDS);
        Thread thread = new Thread(testEarth);
        thread.start();
    }
}
