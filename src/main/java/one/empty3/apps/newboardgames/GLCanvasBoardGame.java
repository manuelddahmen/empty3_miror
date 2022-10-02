package one.empty3.apps.newboardgames;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.awt.GLCanvas;
import one.empty3.apps.opad.DarkFortressGUI;
import one.empty3.apps.opad.JoglDrawer;

import javax.swing.*;

public class GLCanvasBoardGame extends GLCanvas {
    private JoglDrawerBoardGame drawer = null;
    private DarkFortressGUI darkFortressGUI = null;
    public GLCanvasBoardGame(WindowDrawing frame) {
        super();
        drawer = new JoglDrawerBoardGame(frame);
    }
    @Override
    public void display() {
        drawer.display(this);
        //getGL().getGL2().glLoadMatrixf();
    }

    public void setDrawer(JoglDrawerBoardGame drawer) {
        this.drawer = drawer;
    }
}
