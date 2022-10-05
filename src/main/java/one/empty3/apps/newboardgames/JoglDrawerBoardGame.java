package one.empty3.apps.newboardgames;

import com.jogamp.graph.geom.Triangle;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.awt.TextureRenderer;
import one.empty3.apps.opad.*;
import one.empty3.apps.opad.Timer;
import one.empty3.apps.opad.help.PiloteAuto;
import one.empty3.library.*;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomiale;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JoglDrawerBoardGame extends JoglDrawer {

    private Board board;
    private Camera camera;

    public JoglDrawerBoardGame(WindowDrawing darkFortressGUI) {
        super(darkFortressGUI);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    @Override
    public void display(GLAutoDrawable gLDrawable) {
        if (!wasAnimating) {
            animator.start();
            wasAnimating = true;
        }

        millis = System.currentTimeMillis();
        Logger.getAnonymousLogger().log(Level.INFO, "FPS " + 1000/((millis - millis0)));
        millis0 = millis;

        try {
            if (glu == null )
                glu = GLU.createGLU();

            gl = gLDrawable.getGL().getGL2();
            //glu = GLU.createGLU();
        } catch (Exception e) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return;
        }
        // Change to projection matrix.
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        glu.gluPerspective(60, 1.33, 0.01, 10.0);
        gl.glLoadIdentity();

        if(board!=null)
            camera = board.camera();
        

        if (mover!=null) {
            if(mover.getPlotter3D() != null && mover.getPlotter3D().isActive())
                camera = mover.getPositionMobile().calcCameraMobile();
            else
                camera = mover.getPositionMobile().calcCamera();
        }


        if(camera!=null) {
            Point3D pos = camera.getEye();
            Point3D dir = camera.getLookat().moins(pos).norme1();
            diff = dir.moins(pos).norme1();
            Point3D up = camera.getVerticale().getElem();


            Point3D posCam = pos;//.moins(dir.norme1());
            Point3D vertical = camera.getVerticale().getElem().norme1();
            Point3D vert2 = vertical.prodVect(dir).mult(-1);

            posCam = posCam.plus(camera.getLookat().moins(posCam).mult(-0.05));

            up = dir.prodVect(up.prodVect(dir));

            glu.gluLookAt(posCam.get(0), posCam.get(1), posCam.get(2),
                    dir.get(0), dir.get(1), dir.get(2),
                    up.get(0), up.get(1), up.get(2));

            if (toggleMenu.isDisplayScore())
                draw("Score :  " + mover.score(), Color.WHITE, glu, gl);
            if (toggleMenu.isDisplayEnergy())
                draw("Life :  " + mover.energy(), new Dimension(30, 10), Color.GREEN, glu, gl);


            drawToggleMenu(glu, gl);

            drawTrajectory(getPlotter3D(), glu, gl);


            for(int i=0; i<board.getSize2D().getX(); i++)
                for(int j=0; j<board.getSize2D().getY(); j++)
                    draw(board.cellAt(i, j), i, j);
        }


    }

    private void draw(Representable cellAt, int i, int j) {
        this.draw((RepresentableConteneur) cellAt, glu, gl);
    }


}
