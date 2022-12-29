/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package tests.tests2.simula;

import one.empty3.feature.app.replace.java.awt.Color;
import one.empty3.library.*;
import one.empty3.library.core.physics.Bille;
import one.empty3.library.core.physics.Force;
import one.empty3.library.core.testing.Resolution;
import one.empty3.library.core.testing.TestObjetSub;

import java.util.ArrayList;
import java.util.Arrays;

public class TestBlackHole extends TestObjetSub {
    int X = 5;
    int Y = 2;
    int Z = 1;
    Bille[] billes = new Bille[X * Y * Z];
    Force f = new Force();

    public static void main(String[] args) {

        TestBlackHole ttn = new TestBlackHole();
        ttn.setMaxFrames(20000);
        ttn.setDimension(new Resolution(320, 200));
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
                                    ((i - X / 2.)),
                                    ((j - Y / 2.)),
                                    ((k - Z / 2.)))
                                    .mult(Math.random() * 1E9);
                    billes[k * Y * X + j * X + i].color = new Color(1.0f * i
                            / X, 1.0f * j / Y, 1.0f * k / Z);
                    billes[k * Y * X + j * X + i].masse = Math.random()*10.0;
                    billes[k * Y * X + j * X + i].attraction = 100;
                    billes[k * Y * X + j * X + i].repulsion = 0.0;
                    billes[k * Y * X + j * X + i].amortissement = 0.0;
                    billes[k * Y * X + j * X + i].vitesse = Point3D.random(1E6);
                }
            }

        }
        f.setFusion(false);
        f.configurer(getArrayList(billes));

    }

    public ArrayList<Bille> getArrayList(Bille[] billes) {

        ArrayList<Bille> billes2 = new ArrayList<>(Arrays.asList(billes));

        return billes2;
    }

    public void finit() {
        scene().clear();
        scene().cameras().clear();


        for(int i = 0; i<10; i++) {
            f.calculer();
            //f.setDistMinFusion(5 * f.getDistMin() / 4);
        }

        RepresentableConteneur rc = new RepresentableConteneur();

        double distMin = f.getDistMin()*2;

        for (int i = 0; i < X * Y * Z; i++) {
            Representable r = new Sphere(new Axe(billes[i].position.moins(
                    Point3D.Y.mult(distMin)),
                    billes[i].position.plus(Point3D.Y.mult(distMin))),
                    distMin);


            r.texture(new ColorTexture(billes[i].color));

            rc.add(r);
        }

        Camera camera = new Camera(
                f.centreMasse().moins(
                        Point3D.Z.mult(f.getDistMax()*2)),
                f.centreMasse());

        scene().cameraActive(camera);
        camera(camera);
        scene().add(rc);

    }

}
