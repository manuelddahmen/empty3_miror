package tests.tests2;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.CameraInPath;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.library.objloader.E3Model;
import one.empty3.library.objloader.ModelLoaderOBJ;

import java.awt.*;

public class TestObjTeapot extends TestObjetSub {
    private CameraInPath cameraInPath;

    public void ginit() {
        E3Model modelLoaderOBJ = ModelLoaderOBJ.LoadModelE3("resources/teapot.obj", "");
        modelLoaderOBJ.texture(new ColorTexture(Color.BLACK));
        scene().texture(new ColorTexture(Color.WHITE));
        scene().add(modelLoaderOBJ);
        scene().cameraActive(new Camera());
        scene().cameraActive().getEye().setZ(-5.);
        setPublish(true);
        setMaxFrames(100);
        cameraInPath = new CameraInPath(new Circle(new Axe(Point3D.Z, Point3D.Z.mult(-1.)), 10.), Point3D.Z);
        scene().cameraActive(cameraInPath);
    }

    public void finit() {
        cameraInPath.setT(1.0*frame()/getMaxFrames());
        cameraInPath.calculerMatrice(Point3D.Y);
        Point3D eye = cameraInPath.getEye();
        //scene().cameraActive().setEye(eye);
        System.out.println(eye);
        eye = scene().cameraActive().eye();
        System.out.println(eye);
        System.out.println(cameraInPath.getLookat());
        z.idzpp();
    }

    public static void main(String [] args) {
        TestObjTeapot testObjTeapot = new TestObjTeapot();
        new Thread(testObjTeapot).start();
    }
}
