/*
 * Copyright (c) 2017-2022. Manuel Daniel Dahmen
 */

package one.empty3.testscopy.tests.tests2.simula;

import one.empty3.library.*;
import one.empty3.library.core.lighting.Colors;
import one.empty3.library.core.physics.Bille;
import one.empty3.library.core.physics.Force;
import one.empty3.library.core.testing.TestObjetSub;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestGravity2 extends TestObjetSub {
    int X = 3;
    int Y = 3;
    int Z = 3;
    Bille[] billes = new Bille[X * Y * Z];
    Force f = new Force();
    double vOriginal = 100.0;

    public static void main(String[] args) {

        TestGravity2 ttn = new TestGravity2();

        ttn.loop(true);
        ttn.setMaxFrames(10000);
        ttn.publishResult(true);
        ttn.setFileExtension("jpg");

        ttn.run();

    }

    public void ginit() {

        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                for (int k = 0; k < Z; k++) {

                    billes[k * Y * X + j * X + i] = new Bille();
                    billes[k * Y * X + j * X + i].position = new Point3D(
                            (i - X / 2.), (j - Y / 2.),
                            (k - Z / 2.)).mult(Math.random() * vOriginal);
                    billes[k * Y * X + j * X + i].vitesse = new Point3D(
                            (i - X / 2) / 1d, (j - Y / 2) / 1d,
                            (k - Z / 2) / 1d);
                    billes[k * Y * X + j * X + i].color = Colors.random();
                    billes[k * Y * X + j * X + i].masse = 1;
                    billes[k * Y * X + j * X + i].attraction = 100;
                    billes[k * Y * X + j * X + i].repulsion = 0.0;
                    billes[k * Y * X + j * X + i].amortissement = 0.0;
                }
            }

        }

        f.setDt(0.001);
        f.setG(1000.0);

        //f.configurer(billes);
    }

    public void testScene() {
        scene().clear();


        f.calculer();


        RepresentableConteneur rc = new RepresentableConteneur();

        //Polyhedron polyhedron = new Polyhedron();
        for (int i = 0; i < X * Y * Z; i++) {
            Point3D pA = billes[i].position.plus(Point3D.X);
            Point3D pB = billes[i].position.moins(Point3D.X);
            Axe axe = new Axe(pA, pB);
            Representable r = new Sphere(axe, f.dMin(i));

//            ((TRISphere) r).setMaxX(7);
//            ((TRISphere) r).setMaxY(7);

            r.texture(new TextureCol(billes[i].color));

            rc.add(r);

            //polyhedron.add(billes[i].position);
        }

        Camera camera = new Camera(f.centreMasse().plus(
                new Point3D(0d, 0d, -f.getDistMax() / 4d)), f.centreMasse());

        Logger.getAnonymousLogger().log(Level.INFO,""+ rc.getListRepresentable().size());

        scene().cameraActive(camera);

        // scene().add(rc);

        //scene().add(polyhedron);

    }

    @Override
    public void finit() {
    }

}
