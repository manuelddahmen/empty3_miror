/*
 * Created by JFormDesigner on Sat Dec 10 12:00:49 CET 2022
 */

package one.empty3.apps.morph;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LoggingPermission;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.IconView;

import android.util.Log;
import com.jgoodies.forms.factories.*;

import net.miginfocom.swing.*;
import one.empty3.library.*;

/**
 * @author Manuel Dahmen
 */
public class MorphUI extends JFrame {
    private File currentDirectory;
    private File image1;
    private File image2;
    private BufferedImage imageRead1;
    private BufferedImage imageRead2;

    private StructureMatrix<Point3D> grid1 = new StructureMatrix<Point3D>(2, Point3D.class);
    private StructureMatrix<Point3D> grid2 = new StructureMatrix<Point3D>(2, Point3D.class);

    public MorphUI() {


        initComponents();

//Ask for window decorations provided by the look and feel.
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    private void initGrids(StructureMatrix<Point3D> grid, BufferedImage imageRead) {
        try {
            if (imageRead != null) {
                int i1 = Integer.parseInt(textFieldResX.getText());
                int j1 = Integer.parseInt(textFieldResY.getText());
                for (int i = 0; i < i1; i++)
                    for (int j = 0; j < j1; j++) {
                        grid.setElem(Point3D.n(1.0 * i / i1 * imageRead.getWidth(), 1.0 * j / j1 * imageRead.getHeight(), 0d), i, j);
                    }
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void buttonLoadImage1(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return "image/jpg";
            }
        });
        if(currentDirectory != null && currentDirectory.exists()) {
            jFileChooser.setCurrentDirectory(currentDirectory);
        }

        if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
            this.image1 = jFileChooser.getSelectedFile();
            try {
                URL url = new URL("file:///" + image1.getAbsolutePath());
                imageRead1 = ImageIO.read(url);

                Component add = panel1.add(new JLabel(url.toString(),
                        (Icon) new ImageIcon(imageRead1),
                        JLabel.CENTER));
                pack();

                currentDirectory = jFileChooser.getCurrentDirectory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
        initGrids(grid1, imageRead1);
    }

    private void buttonLoadImage2(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return "image/jpg";
            }
        });
        if(currentDirectory != null && currentDirectory.exists()) {
            jFileChooser.setCurrentDirectory(currentDirectory);
        }

        if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
            this.image2 = jFileChooser.getSelectedFile();
            try {
                URL url = new URL("file:///" + image2.getAbsolutePath());
                imageRead2 = ImageIO.read(url);

                panel2.add(new JLabel(url.toString(),
                        (Icon) new ImageIcon(imageRead2),
                        JLabel.CENTER));
                pack();

                currentDirectory = jFileChooser.getCurrentDirectory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
        initGrids(grid2, imageRead2);

    }

    private void buttonGo(ActionEvent e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int seconds = Integer.parseInt(textFieldSeconds.getText());
                int fps = Integer.parseInt(textFieldFps.getText());
                if(imageRead1!=null &&imageRead2!=null && grid1!=null && grid2!=null) {
                    TextureMorphing textureMorphing = new TextureMorphing(imageRead1, imageRead2, fps * seconds);

                    try {
                        StructureMatrix<Point3D> copy = grid1.copy();

                        if (copy != null) {

                            Polygons polygons1 = new Polygons();
                            polygons1.setCoefficients(grid1);

                            Polygons polygons2 = new Polygons();
                            polygons2.setCoefficients(grid2);



                            int resX = 400;//imageRead1.getWidth();
                            int resY = 400;//imageRead1.getHeight();

                            ZBufferImpl zBuffer = new ZBufferImpl(resX, resY);

                            Camera camera = new Camera(Point3D.Z.mult(
                                    -Math.max(resX, resY)).plus(Point3D.X.mult(
                                    resX / 2)).plus(Point3D.Y.mult(resY)),
                                    Point3D.O0.plus(Point3D.X.mult(
                                            resX / 2)).plus(Point3D.Y.mult(resY)));


                            for (int frame = 0; frame < fps * seconds; frame++) {
                                Polygons polygons = new Polygons();
                                polygons.setCoefficients(copy);
                                polygons.texture(textureMorphing);

                                Scene scene = new Scene();
                                scene.add(polygons);

                                scene.cameraActive(camera);

                                double r = 1.0 * frame / fps / seconds;

                                zBuffer.scene(scene);

                                zBuffer.draw();

                                BufferedImage image = zBuffer.image2();

                                ImageIcon imageIcon = new ImageIcon(image);

                                JLabel jLabelResult = new JLabel(imageIcon);

                                if(panelResult.getComponents().length>0) {
                                    panelResult.remove(0);
                                }
                                panelResult.add(jLabelResult);

                                pack();

                                for (int x = 0; x < grid1.getData2d().size(); x++)
                                    for (int y = 0; y < grid1.getData2d().get(x).size(); y++) {
                                        copy.setElem(transitionPoint(grid1.getElem(x, y), grid2.getElem(x, y), r), x, y);
                                    }

                                Logger.getLogger(this.getClass().getSimpleName())
                                        .log(Level.INFO, "Image "+frame);
                            }
                        }

                    } catch(IllegalAccessException ex){
                        throw new RuntimeException(ex);
                    } catch(CopyRepresentableError ex){
                        throw new RuntimeException(ex);
                    } catch(InstantiationException ex){
                        throw new RuntimeException(ex);
                    }
                }

            }
        }).start();

    }

    private Point3D transitionPoint(Point3D elem, Point3D elem1, double r) {
        return elem.plus(elem.plus(elem1.moins(elem).mult(r)));
    }

    private void panelResultMouseClicked(MouseEvent e) {
        System.out.println("Click on image mouseClick");
    }

    private void panelResultMouseDragged(MouseEvent e) {
        System.out.println("Click on image= mouseDragged");
    }

    private void panelResultMouseMoved(MouseEvent e) {
        System.out.println("Click on image= mouseMoved");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
        menuBar1 = new JMenuBar();
        panel5 = new JPanel();
        label7 = new JLabel();
        textFieldResX = new JTextField();
        label8 = new JLabel();
        textFieldResY = new JTextField();
        button4 = new JButton();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panelResult = new JPanel();
        label1 = new JLabel();
        textFieldSeconds = new JTextField();
        label3 = new JLabel();
        textFieldFps = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        slider1 = new JSlider();
        radioButtonActive1 = new JRadioButton();
        radioButtonActive2 = new JRadioButton();
        panel4 = new JPanel();
        button5 = new JButton();
        button6 = new JButton();
        label4 = new JButton();
        label5 = new JButton();
        textFieldAddCol = new JTextField();
        textFieldDelCol = new JTextField();
        textFieldAddRow = new JTextField();
        textFieldDelRow = new JTextField();
        label6 = compFactory.createLabel("Bottom bar");

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));
        setJMenuBar(menuBar1);

        //======== panel5 ========
        {
            panel5.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]"));

            //---- label7 ----
            label7.setText("Grid X");
            panel5.add(label7, "cell 0 0");

            //---- textFieldResX ----
            textFieldResX.setText("10");
            panel5.add(textFieldResX, "cell 1 0");

            //---- label8 ----
            label8.setText("Grid Y");
            panel5.add(label8, "cell 2 0");

            //---- textFieldResY ----
            textFieldResY.setText("10");
            panel5.add(textFieldResY, "cell 3 0");
        }
        contentPane.add(panel5, "cell 0 0 2 1");

        //---- button4 ----
        button4.setText("GO");
        button4.addActionListener(e -> buttonGo(e));
        contentPane.add(button4, "cell 2 0");

        //======== panel1 ========
        {
            panel1.setMinimumSize(new Dimension(200, 200));
            panel1.setMaximumSize(new Dimension(400, 400));
            panel1.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        contentPane.add(panel1, "cell 0 1 1 3");

        //======== panel2 ========
        {
            panel2.setMaximumSize(new Dimension(400, 400));
            panel2.setMinimumSize(new Dimension(200, 200));
            panel2.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        contentPane.add(panel2, "cell 1 1 1 3");

        //======== panelResult ========
        {
            panelResult.setMaximumSize(new Dimension(400, 400));
            panelResult.setMinimumSize(new Dimension(200, 200));
            panelResult.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    panelResultMouseClicked(e);
                }
            });
            panelResult.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    panelResultMouseDragged(e);
                }
                @Override
                public void mouseMoved(MouseEvent e) {
                    panelResultMouseMoved(e);
                }
            });
            panelResult.setLayout(new MigLayout(
                "fill,hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        contentPane.add(panelResult, "cell 2 1 1 3");

        //---- label1 ----
        label1.setText("Seconds");
        contentPane.add(label1, "cell 3 1");

        //---- textFieldSeconds ----
        textFieldSeconds.setText("10");
        contentPane.add(textFieldSeconds, "cell 4 1");

        //---- label3 ----
        label3.setText("FPS");
        contentPane.add(label3, "cell 3 2");

        //---- textFieldFps ----
        textFieldFps.setText("25");
        contentPane.add(textFieldFps, "cell 4 2");

        //---- label2 ----
        label2.setText("text");
        contentPane.add(label2, "cell 3 3");
        contentPane.add(textField2, "cell 4 3");

        //---- button1 ----
        button1.setText("Load 1");
        button1.addActionListener(e -> buttonLoadImage1(e));
        contentPane.add(button1, "cell 0 4");

        //---- button2 ----
        button2.setText("Load 2");
        button2.addActionListener(e -> buttonLoadImage2(e));
        contentPane.add(button2, "cell 1 4");
        contentPane.add(slider1, "cell 2 4");

        //---- radioButtonActive1 ----
        radioButtonActive1.setText("Active");
        contentPane.add(radioButtonActive1, "cell 0 5");

        //---- radioButtonActive2 ----
        radioButtonActive2.setText("Active");
        contentPane.add(radioButtonActive2, "cell 1 5");

        //======== panel4 ========
        {
            panel4.setLayout(new MigLayout(
                "fill,hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]"));

            //---- button5 ----
            button5.setText("Add col");
            panel4.add(button5, "cell 0 0");

            //---- button6 ----
            button6.setText("Del col");
            panel4.add(button6, "cell 1 0");

            //---- label4 ----
            label4.setText("Add row");
            panel4.add(label4, "cell 2 0");

            //---- label5 ----
            label5.setText("Del row");
            panel4.add(label5, "cell 3 0");

            //---- textFieldAddCol ----
            textFieldAddCol.setText("1");
            panel4.add(textFieldAddCol, "cell 0 1");

            //---- textFieldDelCol ----
            textFieldDelCol.setText("1");
            panel4.add(textFieldDelCol, "cell 1 1");

            //---- textFieldAddRow ----
            textFieldAddRow.setText("1");
            panel4.add(textFieldAddRow, "cell 2 1");

            //---- textFieldDelRow ----
            textFieldDelRow.setText("1");
            panel4.add(textFieldDelRow, "cell 3 1");
        }
        contentPane.add(panel4, "cell 2 5");
        contentPane.add(label6, "cell 0 6 5 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path,
                                        String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JMenuBar menuBar1;
    private JPanel panel5;
    private JLabel label7;
    private JTextField textFieldResX;
    private JLabel label8;
    private JTextField textFieldResY;
    private JButton button4;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panelResult;
    private JLabel label1;
    private JTextField textFieldSeconds;
    private JLabel label3;
    private JTextField textFieldFps;
    private JLabel label2;
    private JTextField textField2;
    private JButton button1;
    private JButton button2;
    private JSlider slider1;
    private JRadioButton radioButtonActive1;
    private JRadioButton radioButtonActive2;
    private JPanel panel4;
    private JButton button5;
    private JButton button6;
    private JButton label4;
    private JButton label5;
    private JTextField textFieldAddCol;
    private JTextField textFieldDelCol;
    private JTextField textFieldAddRow;
    private JTextField textFieldDelRow;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
