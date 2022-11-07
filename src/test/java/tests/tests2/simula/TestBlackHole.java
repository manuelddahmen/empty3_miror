package tests.tests2.simula;

import one.empty3.feature.app.replace.java.awt.Color;
import one.empty3.library.*;
import one.empty3.library.core.physics.Bille;
import one.empty3.library.core.physics.Force;
import one.empty3.library.core.testing.TestObjetSub;

import java.util.ArrayList;
import java.util.Arrays;

public class TestBlackHole extends TestObjetSub {
    int X = 10;
    int Y = 10;
    int Z = 10;
    Bille[] billes = new Bille[X * Y * Z];
    Force f = new Force();

    public static void main(String[] args) {

        TestBlackHole ttn = new TestBlackHole();

        ttn.loop(true);
        ttn.setMaxFrames(1000);
        ttn.publishResult(true);

        new Thread(ttn).start();

    }

    public void ginit() {

        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                for (int k = 0; k < Z; k++) {

                    billes[k * Y * X + j * X + i] = new Bille();
                    billes[k * Y * X + j * X + i].position =
                            new Point3D(
                                    (double) ((i - X / 2) / 1f),
                                    (double) ((j - Y / 2) / 1f),
                                    (double) ((k - Z / 2) / 1f))
                                    .mult(Math.random() * 1000);
                    Point3D.random(1000.0);
                    billes[k * Y * X + j * X + i].color = new Color(1.0f * i
                            / X, 1.0f * j / Y, 1.0f * k / Z);
                    billes[k * Y * X + j * X + i].masse = 100000;
                    billes[k * Y * X + j * X + i].attraction = 10;
                    billes[k * Y * X + j * X + i].repulsion = 0.1;
                    billes[k * Y * X + j * X + i].amortissement = 0.2;
                    billes[k * Y * X + j * X + i].vitesse = Point3D.O0;
                }
            }

        }
        //f.setFusion(true);
        f.configurer(getArrayList(billes));

    }

    public ArrayList<Bille> getArrayList(Bille[] billes) {

        ArrayList<Bille> billes2 = new ArrayList<>(Arrays.asList(billes));

        return billes2;
    }

    public void finit() {
        scene().clear();
        scene().cameras().clear();

        f.calculer();
        f.setDistMinFusion(5 * f.getDistMin() / 4);


        RepresentableConteneur rc = new RepresentableConteneur();

        double distMin = f.getDistMin();

        for (int i = 0; i < X * Y; i++) {
            Representable r = new Sphere(new Axe(billes[i].position.moins(
                    Point3D.Y.mult(distMin)),
                    billes[i].position.plus(Point3D.Y.mult(distMin))), distMin);


            r.texture(new ColorTexture(billes[i].color));

            rc.add(r);
        }

        Camera camera = new Camera(
                f.centreMasse().plus(Point3D.Z.mult(-f.getDistMax() / 2)),
                f.centreMasse());

        scene().cameraActive(camera);
        camera(camera);
        scene().add(rc);

    }

}
