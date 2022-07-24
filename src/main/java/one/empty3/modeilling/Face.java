package one.empty3.modeilling;

import one.empty3.feature.tryocr.Rectangle2;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;
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
                0, 0.5, 0.7, 0.5, 0, -0.3
        }, {
                0, 0.4, 0.8, 0.4, 0, -0.1

        }, {
                0, 0.6, 0.7, 0.6, 0, 0

        },{
                0, 0.2, 0.3, 0.2, 0, 0.1

        }, {
                0, 0.6, 0.7, 0.6, 0, 0.12

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
            for (int j = 3; j < controls[i].length - 3; j++) {
                double y = z[i][z[i].length-1];
                controls[i][j].set(2, z[i - 3][j - 3]);
                controls[i][j].set(1, y);

            }
        }
        SurfaceParametricPolygonalBezier s = new SurfaceParametricPolygonalBezier(controls);

        Scene scene = new Scene();

        scene.add(s);

        try {
            STLExport.save(new File("c:\\users\\manue\\Desktop\\me3D.stl"), scene, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String [] args) {
        Face face = new Face();
    }
}
