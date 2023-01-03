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

package one.empty3.growth.test;/*
 * This file is part of Plants-Growth-2
 *     Foobar is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Foobar is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import one.empty3.growth.graphics.Rotation;
import one.empty3.library.ColorTexture;
import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;
import one.empty3.library.core.testing.TestObjetStub;
import one.empty3.library.core.tribase.TRISphere;

import java.awt.*;

public class TestRotation2 extends TestObjetStub {


    private Color random() {
        float r = Math.abs((float) Math.random());
        float g = Math.abs((float) Math.random());
        float b = Math.abs((float) Math.random());
        double n = Math.sqrt(r * r + g * g + b * b);
        return new Color((float) (r / n), (float) (g / n), (float) (b / n));
    }

    public void testScene() {
        double MAX = 200.0;
        double MAXCERLE = 10.;
        Point3D p0 = Point3D.Y;
        p0.texture(new ColorTexture(Color.BLACK));
        for (int axeNo = 0; axeNo < 10; axeNo++) {
            Point3D random = Point3D.random(10.);
            Point3D random2 = Point3D.random(10.);
            Color color1 = random();
            Color color2 = random();
            for (int i = 0; i < MAX; i++) {
                //Color color = CouleurOutils.couleurRatio(color1, color2, i/MAX);
                Rotation rotation = new Rotation(random, Point3D.Y, 2 * Math.PI * i / MAX);
                Point3D p = rotation.rotate(random2);
                p.texture(new ColorTexture(color1));
                LineSegment segmentDroite = new LineSegment(p0, p);
                segmentDroite.texture(new ColorTexture(color1));
                TRISphere triSphere = new TRISphere(p, 1.0);
                triSphere.texture(new ColorTexture(color1));
                scene().add(segmentDroite);
                p0 = p;
                camera().setEye(new Point3D(0., 0., -1000.0));
            }
        }
    }

    public static void main(String[] args) {
        TestRotation2 test = new TestRotation2();
        test.loop(false);
        new Thread(test).start();
    }
}
