package one.empty3.testscopy.tests;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.*;
import one.empty3.library.core.testing.TestObjetSub;

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

        z().setDisplayType(ZBufferImpl.SURFACE_DISPLAY_TEXT_QUADS);

        z().texture(new ColorTexture(Color.BLACK));
        scene().texture(new ColorTexture(Color.BLACK));


        Scene scene = new Scene();

        scene().add(sphere);

        sphere.setIncrU(.005);
        sphere.setIncrV(.005);
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
        circle = sphere.getCircle();
        System.out.println("Planets:" + i + "/" + planetsImagesFiles.length);
    }

    @Override
    public void finit() throws Exception {
        if ((frame() %( FPS * SECONDS) == 1)) {
            incr();
        }


        Camera camera = new Camera(circle.calculerPoint3D
                (((frame()%(FPS * SECONDS)) /(1.0*FPS * SECONDS) )).mult(3.0),
                Point3D.O0, axe);

        /*
        Camera camera = new Camera(Point3D.O0,
                circle.calculerPoint3D
                        ((frame()%(FPS * SECONDS)) /(1.0*FPS * SECONDS)), axe);
*/
        camera.imposerMatrice(true);
        camera.imposerMatrice(
                new Matrix33(new Point3D[]  {
                        axe.prodVect(
                        camera.getEye().moins(camera().getLookat())),
                        axe,
                        camera.getEye().moins(camera().getLookat())}));

        z().scene().cameraActive(camera);



        scene().cameraActive(camera);
    }

    @Override
    public void afterRender() {

    }

    public static void main(String[] args) {
        TestEarth testEarth = new TestEarth();
        testEarth.loop(true);
        testEarth.setMaxFrames(9*FPS*SECONDS);
        Thread thread = new Thread(testEarth);
        thread.start();
    }
}
