/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.tests;

import one.empty3.library.Camera;
import one.empty3.library.Point3D;
import one.empty3.library.core.testing.TestObjetSub;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestVoiture extends TestObjetSub {
    public void ginit() {
        scene().add(new Voiture());
        setMaxFrames(1);
        scene().cameraActive(new Camera(Point3D.Z.mult(1), Point3D.O0));
    }

    @Override
    public void finit() throws Exception {
        super.finit();
        Logger.getAnonymousLogger().log(Level.INFO,""+ scene());
    }
    public static void main(String [] args) {
        TestVoiture testVoiture = new TestVoiture();
        testVoiture.setGenerate(GENERATE_MODEL | GENERATE_IMAGE|GENERATE_MOVIE);
        testVoiture.setPublish(true);
        new Thread(testVoiture).start();
    }
}
