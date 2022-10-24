package one.empty3.testscopy.tests;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.*;
import one.empty3.library.core.testing.TestObjetSub;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

public class TestEarth extends TestObjetSub {
    private File planets = new File("C:\\Users\\manue\\empty3-library-generic\\res\\img\\planets");
    private File[] planetsImagesFiles;
    private int i = -1;
    private BufferedImage image;
    private Sphere sphere;
    private Logger logger;


    @Override
    public void ginit() {
        logger = Logger.getLogger(this.getClass().getCanonicalName());
        planetsImagesFiles = planets.listFiles();
        sphere = new Sphere(new Axe(Point3D.Y.mult(1.0), Point3D.Y.mult(-1)), 2.0);

        z().setDisplayType(ZBufferImpl.SURFACE_DISPLAY_TEXT_TRI);

        z().texture(new ColorTexture(Color.BLACK));
        scene().texture(new ColorTexture(Color.BLACK));


        Scene scene = new Scene();

        scene().add(sphere);

        sphere.setIncrU(.001);
        sphere.setIncrV(.001);
    }

    @Override
    public void testScene() throws Exception {

    }

    @Override
    public void afterRenderFrame() {

    }

    public void incr() {
        i++;
        if (i < planets.length())
            image = ImageIO.read(planetsImagesFiles[i]);
        else return;
        ImageTexture textureImg = new ImageTexture(new ECBufferedImage(image));
        sphere.texture(textureImg);


        System.out.println("Planets:" + i + "/" + planetsImagesFiles.length);
    }

    @Override
    public void finit() throws Exception {
        if ((frame() %( 25 * 10) == 0)) {
            incr();
        }

        Point3D p1axe = Point3D.Y;

        Circle circle = new Circle(new Axe(p1axe.mult(1), p1axe.mult(-1)), 5.0);

        Camera camera = new Camera(circle.calculerPoint3D(((frame()%(25*10)) )), Point3D.O0);

        camera.declareProperties();

        z().scene().cameraActive(camera);



        scene().cameraActive(camera);
    }

    @Override
    public void afterRender() {

    }

    public static void main(String[] args) {
        TestEarth testEarth = new TestEarth();
        testEarth.loop(true);
        testEarth.setMaxFrames(9*25*10);
        Thread thread = new Thread(testEarth);
        thread.start();
    }
}
