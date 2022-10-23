package one.empty3.testscopy.tests;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.*;
import one.empty3.library.core.nurbs.CameraInPath;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.testing.TestObjet;
import one.empty3.library.core.testing.TestObjetSub;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestEarth extends TestObjet {
    private File planets = new File("res/img/planets");
    private File[] planetsImagesFiles;
    private int i = 0;
    private BufferedImage image;
    private Sphere sphere;


    public void ginit() {
        planetsImagesFiles = planets.listFiles();
        sphere = new Sphere(new Axe(Point3D.Y.mult(1.0), Point3D.Y.mult(-1)), 1.0);
        z().texture(new ColorTexture(Color.BLACK));
        scene().texture(new ColorTexture(Color.BLACK));
        scene().add(sphere);
    }

    @Override
    public void testScene() throws Exception {

    }

    @Override
    public void afterRenderFrame() {

    }



    @Override
    public void finit() throws Exception {
        if((frame()/25/10)%planets.length()==0) {
            i++;
            if(i<planets.length())
                image = ImageIO.read(planetsImagesFiles[i]);
            TextureImg textureImg = new TextureImg(new ECBufferedImage(image));
            sphere.texture(textureImg);


        }
        Point3D p1axe = Point3D.Z.mult(-2 * Math.cos(2 * Math.PI * (frame() / 10.))).plus(
                Point3D.X.mult(-2 * Math.sin(2 * Math.PI * (frame() / 10.))));
        Circle circle = new Circle(new Axe(p1axe, p1axe.mult(-1)), 1.0);

        CameraInPath camera = new CameraInPath(circle);

        camera.setT(1.0);

        scene().cameraActive(camera);
    }

    @Override
    public void afterRender() {

    }
}
