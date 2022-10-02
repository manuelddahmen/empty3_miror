package one.empty3.apps.newboardgames;

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

    public WindowDrawing() {
        super(JoglDrawerBoardGame.class);
        frame = new JFrame("Game");
        frame.setMinimumSize(new Dimension(800, 600));

        JoglDrawerBoardGame joglDrawerBoardGame = new JoglDrawerBoardGame(this);
        GLCanvasBoardGame glCanvasBoardGame = new GLCanvasBoardGame(this);
        joglDrawerBoardGame.setGlcanvas(glCanvasBoardGame);
        joglDrawerBoardGame.setBoard(new Grid(10, 10, 5, 8));
        glCanvasBoardGame.setVisible(true);
        glCanvasBoardGame.setMinimumSize(new Dimension(800, 600));



        JPanel jPanel = new JPanel();
        jPanel.setMinimumSize(new Dimension(800, 600));
        jPanel.add(glCanvasBoardGame);




        frame.add(jPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Animator animator = new Animator();
        glCanvasBoardGame.setAnimator(animator);
        glCanvasBoardGame.getAnimator().start();


        setMinimumSize(new Dimension(800, 600));

        setVisible(true);

        joglDrawerBoardGame.display(glCanvasBoardGame);


    }
    public static void main(String [] args) {
        WindowDrawing windowDrawing = new WindowDrawing();

    }

    public void setCamera(Camera camera) {
        ((GLCanvas)canvas).display();
    }
}
