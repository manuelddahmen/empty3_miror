package one.empty3.apps.vecmesh;

import one.empty3.library.*;
import one.empty3.library1.shader.Vec;
import one.empty3.library1.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library1.tree.AlgebricTree;
import one.empty3.library1.tree.TreeNodeEvalException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class VecMeshEditor implements Runnable {
    private final VecMeshEditorGui vecMeshEditorGui;

    public static void main(String [] strings) {
        VecMeshEditor vecMeshEditor = new VecMeshEditor();
        Thread thread = new Thread(vecMeshEditor);
        thread.start();
    }

    public VecMeshEditor() {
        vecMeshEditorGui = new VecMeshEditorGui();
        vecMeshEditorGui.setVisible(true);
    }


    public void run() {
        while(vecMeshEditorGui.isVisible()) {
            try {
                AlgebricTree algebricTree = new AlgebricTree(vecMeshEditorGui.getDefaultCode());
                algebricTree.construct();
                StructureMatrix<Double> eval = algebricTree.eval();

                if(eval!=null&&eval.getDim()==1 && !eval.getData1d().isEmpty()) {
                    Double[] doubles = new Double[eval.getData1d().size()];
                    VecHeightMap vecHeightMap = new VecHeightMap(new Sphere(Point3D.O0, 4),
                            new Vec(doubles), vecMeshEditorGui.getTextFieldRows());

                    ZBufferImpl zBuffer = new ZBufferImpl(640, 480);
                    Scene scene = new Scene();
                    scene.add(vecHeightMap);
                    zBuffer.scene(scene);
                    Camera camera = new Camera(Point3D.Z.mult(10), Point3D.O0, Point3D.Y);
                    scene.cameraActive(camera);
                    zBuffer.camera(camera);
                    zBuffer.draw();
                    ECBufferedImage ecBufferedImage = zBuffer.imageInvX();
                    JPanel panelGraphics = vecMeshEditorGui.getPanelGraphics();
                    Graphics graphics = panelGraphics.getGraphics();
                    graphics.drawImage(ecBufferedImage, 0, 0, panelGraphics.getWidth(), panelGraphics.getHeight(), null);
                }
            } catch (AlgebraicFormulaSyntaxException | TreeNodeEvalException | RuntimeException ignored) {
                ignored.printStackTrace();
            }
        }
    }

}
