/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.testscopy.tests.tests2.balleclou;


import one.empty3.library.*;
import one.empty3.library.core.extra.BalleClous;
import one.empty3.library.core.testing.TestObjetSub;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author MD
 */
public class TestBalleClous extends TestObjetSub {
    private ITexture tc = new TextureCol(Color.red);
    private BalleClous ballec;

    public static void main(String[] args) {
        TestBalleClous th = new TestBalleClous();
        th.loop(true);
        th.setMaxFrames(1000);
        th.setGenerate(GENERATE_MOVIE | GENERATE_IMAGE | GENERATE_MODEL);
        new Thread(th).start();
    }

    @Override
    public void ginit() {

        z().setDisplayType(ZBufferImpl.SURFACE_DISPLAY_COL_TRI);
        z().setFORCE_POSITIVE_NORMALS(true);
        try {
            tc = new TextureImg(new ECBufferedImage(ImageIO.read(new File("samples/img/manu.jpg"))));
        } catch (IOException ex) {
            Logger.getLogger(TestBalleClous1.class.getName()).log(Level.SEVERE, null, ex);
        }
        ballec = new BalleClous(Point3D.O0, 1.0);
        int m, n;
        m = 5;
        n = 5;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                ballec.addPoint(new Point2D(1.0 * i / m, 1.0 * j / n));
            }
        ballec.texture(tc);
        scene().add(ballec);
        //scene().lumieres().add(new LumierePonctuelle(Point3D.Z, Color.YELLOW));
        Camera camera;
        camera = new Camera(new Point3D(0d, 0d, -2.0), new Point3D(0d, 0d, 0d));
        scene().cameraActive(camera);
    }

    @Override
    public void testScene() throws Exception {
        ballec.param(1./(frame()+1));
    }


}

