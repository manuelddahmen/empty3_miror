/*__
 Global license :

 Microsoft Public Licence

 author Manuel Dahmen <manuel.dahmen@gmx.com>
 ***/


package tests.tests2.cadeau;

import one.empty3.library.*;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.library.core.tribase.TRISphere;

import java.awt.*;

/*__
 * @author Manuel Dahmen <manuel.dahmen@gmx.com>
 */
public class SphereCube12 extends TestObjetSub {
    private final double t0 = -1;
    private final double t1 = 1;
    double d = 90;
    private Sphere s;

    private Camera cam;

    public static void main(String[] args) {


        SphereCube12 sc = new SphereCube12();

        sc.setMaxFrames(500);

        sc.loop(true);

        new Thread(sc).start();


    }

    @Override
    public void ginit() {

        Cube c;

        //c.texture(new TextureCol(Color.RED));
        c = new Cube(d / 10, Point3D.O0);


        c.texture(new TextureCol(Color.BLUE));

        s = new Sphere(Point3D.X.mult(t0), d / 10);

        s.texture(new TextureCol(Color.YELLOW));

        scene().add(c);
        scene().add(s);

        double f = 3;
        cam = new Camera(s.getCircle().getCenter().mult(f), Point3D.O0);

        scene().cameraActive(cam);

        scene().lumieres().add(new LumierePonctuelle(Point3D.O0, Color.GREEN));
    }

    @Override
    public void finit() {

        double pc = 1.0 * frame() / getMaxFrames();

        double TT;
        TT = t0 + (t1 - t0) * pc;

        s.getCircle().setCenter(Point3D.X.mult(TT * d));

        cam.setLookat(s.getCircle().getCenter());
        cam.setEye(Point3D.Z.mult(d / 3));
        cam.calculerMatrice(Point3D.Y);
    }

}
