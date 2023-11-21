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

package tests.test3;

import one.empty3.library.BezierCubique2D;
import one.empty3.library.Point3D;
import one.empty3.library.core.testing.TestObjetSub;

public class TestBezierCubique2D extends TestObjetSub {
    @Override
    public void ginit() {
        super.ginit();
        BezierCubique2D bezierCubique2D = new BezierCubique2D();
        for(int i=-2; i<2; i++)
            for(int j=-2; j<2; j++)
                bezierCubique2D.setControle(i+2, j+2, new Point3D((double) i, (double)j, 0.0));
        scene().add(bezierCubique2D);

        camera().getEye().set(2, 4.0);
        camera().declareProperties();
    }

    @Override
    public void finit() throws Exception {
        super.finit();
    }

    public static void main(String[] args) {
        TestBezierCubique2D testBezierCubique2D = new TestBezierCubique2D();
        testBezierCubique2D.setMaxFrames(1);
        testBezierCubique2D.setGenerate(TestBezierCubique2D.GENERATE_MODEL|TestBezierCubique2D.GENERATE_IMAGE);
        testBezierCubique2D.loop(false);

        new Thread(testBezierCubique2D).start();
    }
}
