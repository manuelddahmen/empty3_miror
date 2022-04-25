/*__
 * ect 27-08-17 Copyright.
 */

package tests.tests2.modeleStl;

import one.empty3.library.*;
import one.empty3.library.Polygon;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.library.stl_loader.IncorrectFormatException;
import one.empty3.library.stl_loader.ParsingErrorException;
import one.empty3.library.stl_loader.StlFile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class TestStl extends TestObjetSub {
    private BufferedReader reader;

    @Override
    public void ginit() {
        super.ginit();

        setResolution(1000, 1000);

        StlFile file = new StlFile();
        Scene load = new Scene();
        try {
            File file1 = new File("samples/stl/another_nude_girl-ascii.stl");
            load = file.load(file1.getAbsolutePath());
        } catch (IncorrectFormatException | IOException | ParsingErrorException e) {
            e.printStackTrace();
        }




        scene().add(load.getObjets().getElem(0));

        ITexture colorTexture0 = new ColorTexture(Color.MAGENTA);
        for (int i = 0; i < ((RepresentableConteneur) scene().getObjets().getElem(0)).getListRepresentable().size(); i++) {
            TRI t = (TRI)((RepresentableConteneur) scene().getObjets().getElem(0)).getListRepresentable().get(i);
            t.texture(colorTexture0);
        }
        load.getObjets().getElem(0).texture(colorTexture0);
        System.out.println(((RepresentableConteneur) scene().getObjets().getElem(0)).getListRepresentable().size());


        Sphere s = new Sphere(new Axe(new Point3D(0., -1., 0.),
                new Point3D(0., 1., 0.)), 10.0);
        s.texture(new ColorTexture(Color.PINK));

        scene().add(s);

        //scene().getObjets().getElem(0).texture(new ColorTexture(Color.BLACK));

        scene().cameraActive(new Camera());

       /* Camera camera = new Camera2Quad(
                z(), new Polygon(new Point3D[]{
                new Point3D(-500., -500., -8000.),
                new Point3D(500.,  -500., -8000.),
                new Point3D(500., 500., -8000.),
                new Point3D(-500.,  500., -8000.)},
                Color.BLUE),
                new Polygon(new Point3D[]{
                        new Point3D(-5000., -5000., 500.),
                        new Point3D(5000.,  -5000., 500.),
                        new Point3D(5000., 5000., 500.),
                        new Point3D(-5000.,  5000., 500.)},
                        Color.BLUE)
        );*/

        Camera camera = new Camera(Point3D.Z.mult(-1000), Point3D.O0, Point3D.Y);

        scene().cameraActive(camera);
    }

    @Override
    public void finit() throws Exception {
    }

    public static void main(String[] args) {
        TestStl stl = new TestStl();
        stl.setMaxFrames(1);
        new Thread(stl).start();
    }

}
