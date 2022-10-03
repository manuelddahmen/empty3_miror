package one.empty3.apps.newboardgames;

import com.jogamp.opengl.awt.GLCanvas;
import one.empty3.apps.opad.DarkFortressGUI;

public class GLCanvasBoardGame extends GLCanvas {
    private JoglDrawerBoardGame drawer = null;
    private DarkFortressGUI darkFortressGUI = null;
    private Board board;

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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
