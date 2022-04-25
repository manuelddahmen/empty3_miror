package one.empty3.testscopy.tests;

import one.empty3.library.Camera;
import one.empty3.library.Point3D;
import one.empty3.library.core.testing.TestObjetSub;

public class TestVoiture extends TestObjetSub {
    public void ginit() {
        scene().add(new Voiture());
        scene().cameraActive(new Camera());
        setMaxFrames(1);
        scene().cameraActive(new Camera(new Point3D(0.0, 0.0, -1000.0),
                new Point3D(0.0,0.0,0.0), Point3D.Y));
    }
    @Override
    public void finit() throws Exception {
        super.finit();
        System.out.println(scene());
    }
    public static void main(String [] args) {
        TestVoiture testVoiture = new TestVoiture();
        testVoiture.setGenerate(GENERATE_MODEL | GENERATE_IMAGE|GENERATE_MOVIE);
        testVoiture.setPublish(true);
        new Thread(testVoiture).start();
    }
}
