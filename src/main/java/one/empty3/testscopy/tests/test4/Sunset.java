/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.testscopy.tests.test4;

import one.empty3.library.Polygon;
import one.empty3.library.*;
import one.empty3.library.core.testing.TestObjetSub;

import java.awt.*;

public class Sunset extends TestObjetSub {
    private static final int VUE_1 = 30;
    private static final int FPS = 25;

    public static void main(String[] args) {
        Sunset sunset = new Sunset();
        sunset.loop(true);
        sunset.setMaxFrames(VUE_1*FPS);
        new Thread(sunset).start();
    }

    @Override
    public void finit() throws Exception {
        super.finit();

        if (frame() < VUE_1 * FPS) {
            //z().setDisplayType(Representable.DISPLAY_ALL);
            z().texture(new ColorTexture(Color.BLACK));
            Polygon polygon = new Polygon();
            StructureMatrix<Point3D> mat = new StructureMatrix<>(2, Point.class);
            mat.setElem(new Point3D(-10d, 0d, -10d), 0, 0);
            mat.setElem(new Point3D(10d, 0d, -10d), 1, 0);
            mat.setElem(new Point3D(10d, 0d, 10d), 1, 1);
            mat.setElem(new Point3D(-10d, 0d, 10d), 0, 1);
        /*polygon.setMatrix( new Point3D[][]{
                {mat.getElem(0, 0), mat.getElem(1, 0)}, {mat.getElem(1, 0), mat.getElem(1, 1)}
        });*/

            scene().clear();

            polygon.setPoints(
                    new Point3D[]{
                            mat.getElem(0, 0), mat.getElem(1, 0), mat.getElem(1, 1), mat.getElem(0, 1)
                    });

            polygon.texture(new ColorTexture(new Color(0f, 0.2f, 1.0f)));

            scene().add(polygon);
            Point3D eye = new Point3D(50.0, 0.2, 50.0);
            Point3D lookAt = new Point3D(0.0, 0.2, 50.0);
            Point3D pos = eye.plus(lookAt.moins(eye).mult(1.0*frame() / (25 * 30)));
            Camera camera = new Camera(pos, lookAt);
            //camera.calculerMatrice(Point3D.Y);
            scene().cameraActive(camera);
        }

    }
}
