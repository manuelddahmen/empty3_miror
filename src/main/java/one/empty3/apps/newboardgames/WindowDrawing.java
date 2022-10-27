package one.empty3.apps.newboardgames;

import com.android.tools.r8.u.b.G;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import one.empty3.apps.newboardgames.boards.Grid;
import one.empty3.apps.opad.DarkFortressGUI;
import one.empty3.apps.opad.Drawer;
import one.empty3.apps.opad.JoglDrawer;
import one.empty3.library.Camera;

import javax.swing.*;
import java.awt.*;

public class WindowDrawing extends DarkFortressGUI {

    private GLCanvas canvas;
    private JFrame frame;
    JoglDrawerBoardGame joglDrawerBoardGame;
    GLCanvasBoardGame glCanvasBoardGame;
    JPanel jPanel;
    public WindowDrawing() {
        super(JoglDrawerBoardGame.class);

        joglDrawerBoardGame = new JoglDrawerBoardGame(this);
        glCanvasBoardGame = new GLCanvasBoardGame(this);
        joglDrawerBoardGame.setBoard(new Grid(10, 10, 5, 8));
        glCanvasBoardGame.setVisible(true);
        glCanvasBoardGame.setMinimumSize(new Dimension(800, 600));



        jPanel = new JPanel();
        jPanel.setMinimumSize(new Dimension(800, 600));
        jPanel.add(glCanvasBoardGame);




        add(jPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Animator animator = new Animator();
        glCanvasBoardGame.setAnimator(animator);
        glCanvasBoardGame.getAnimator().start();


        setMinimumSize(new Dimension(800, 600));
    }
    public static void main(String [] args) {
        WindowDrawing windowDrawing = new WindowDrawing();

    }

    public void setCamera(Camera camera) {
        joglDrawerBoardGame.setCamera(camera);
    }
}