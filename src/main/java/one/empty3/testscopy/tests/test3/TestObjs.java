package one.empty3.testscopy.tests.test3;

import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.library.objloader.E3Model;
import one.empty3.library.objloader.ModelLoaderOBJ;

import java.io.File;

public class TestObjs extends TestObjetSub {
    String directory = "resources/models/samples/";
    public TestObjs() {

    }

    public void finit() {
        testObjs();
    }

    public void testObjs() {
        String[] list = new File(directory).list();
        assert list != null;
        setMaxFrames(list.length);
        System.out.println("Obj directory. Number of files : " + list.length);
        scene().getObjets().getData1d().clear();
        int i = frame();
        if(i<list.length) {
            File file = new File(directory + list[i]);

            if (file.exists() && file.getAbsolutePath().toLowerCase().endsWith(".obj")) {

                E3Model e3Model = ModelLoaderOBJ.LoadModelE3(file.getAbsolutePath(), file.getAbsolutePath());

                scene().add(e3Model);

                System.out.println("Obj file added. "+file.getName());
            }
            else {
                System.out.println("Not a obj file");
            }
        }
    }
    public static void main(String [] args) {
        TestObjs testObjs = new TestObjs();
        new Thread(testObjs).start();
    }
}
