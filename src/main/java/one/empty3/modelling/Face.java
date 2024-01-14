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

package one.empty3.modelling;

import one.empty3.feature.tryocr.Rectangle2;
import one.empty3.library.Point3D;
import one.empty3.library.RepresentableConteneur;
import one.empty3.library.Scene;
import one.empty3.library.core.export.STLExport;
import one.empty3.library.core.nurbs.SurfaceParametricPolygonalBezier;

import java.io.File;
import java.io.IOException;

public class Face extends RepresentableConteneur {
    private Rectangle2 rectangle2 = new Rectangle2(-1, -1, 2, 2);
    public Face() {
        Point3D[][] controls = new Point3D[10][12];
        for (int i = 0; i < controls.length; i++) {
            for (int j = 0; j < controls[i].length; j++) {
                controls[i][j] = new Point3D(
                        rectangle2.getX() + rectangle2.getW() * (1.0 * i / controls.length),
                        rectangle2.getY() + rectangle2.getH() * (1.0 * j / controls.length),
                        0.0
                );
            }
        }
        double[][] z = new double[][]{
                {
                        -1, 0, 0.5, 0, -1, -1
                }, {
                0.6, 0.6, 0.7, 0.5, 0.6, -0.6
        }, {
                0.6, 0.6, 0.8, 0.7, 0.6, -0.1

        }, {
                0.6, 0.6, 0.7, 0.6, 0.6, 0

        },{
                0.6, 0.7, 0.7, 0.7, 0, 0.1

        }, {
                0, 0.6, 0.7, 0.6, 0, 0.6

        },{
                -1, -1, -1, -1, -1, 1
        }
        };
        for (int i = 0; i < controls.length; i++) {
            for (int j = 0; j < controls[i].length; j++) {
                controls[i][j].set(2, -1.);
            }
        }
        for (int i = 3; i < controls.length - 3; i++) {
            for (int j = 3; j < controls[i].length - 4; j++) {
                double y = z[i][z[i].length-1];
                controls[i][j].set(2, z[i - 3][j - 3]);
                controls[i][j].set(1, y);
                //controls[i][j].set(0, )
            }
        }
        SurfaceParametricPolygonalBezier s = new SurfaceParametricPolygonalBezier(controls);

        Scene scene = new Scene();

        scene.add(s);
        File file = new File("c:\\users\\manue\\Desktop\\me3D-" + Math.random() + ".stl");
        try {
            STLExport.save(file, scene, false);
            {
                Process process = new ProcessBuilder("explorer", file.getAbsolutePath(), "\"").start();
                int errorCode = process.waitFor();
// adjust the error code for your use case, different programs migth not use 0 as success
                if (errorCode != 0) {
                    /*try (BufferedReader reader = process.errorReader(StandardCharsets.UTF_8)) {
                    }*/
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String [] args) {
        Face face = new Face();
    }
}
