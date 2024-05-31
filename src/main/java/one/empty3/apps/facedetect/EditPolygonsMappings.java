/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

/*
 * Created by JFormDesigner on Sat May 18 10:59:33 CEST 2024
 */

package one.empty3.apps.facedetect;

import com.google.common.util.concurrent.AtomicDouble;
import javaAnd.awt.image.imageio.ImageIO;
import net.miginfocom.swing.MigLayout;
import one.empty3.apps.morph.Main;
import one.empty3.library.*;
import one.empty3.library.objloader.E3Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author manue
 */
public class EditPolygonsMappings extends JPanel implements Runnable {
    private static final int EDIT_POINT_POSITION = 1;
    private int mode;
    private BufferedImage image;
    private E3Model model;
    protected TestHumanHeadTexturing testHumanHeadTexturing;
    /***
     * Contains named landkmarks points from @image
     */
    private HashMap<String, Point3D> pointsInImage = new HashMap<>();
    /***
     * contains (u,v) coordinates from project of @pointsInImage
     * projection is made with @E3Model.findUvFace(u, v)
     */
    private HashMap<String, Point3D> pointsInModel = new HashMap<>();
    protected boolean isRunning = true;
    private Point3D pFound = null;
    private String landmarkType;
    private double u;
    private double v;

    public EditPolygonsMappings(Window owner) {
        initComponents();

    }

    public EditPolygonsMappings() {

    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    private void panelModelViewMouseDragged(MouseEvent e) {
        //e.getSource()
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        var panel1 = new JPanel();
        splitPane1 = new JSplitPane();
        splitPane2 = new JSplitPane();
        panelPicture = new JPanel();
        panelModelView = new JPanel();
        scrollPane1 = new JScrollPane();
        textAreaTextOutput = new JTextArea();

        //======== this ========
        setPreferredSize(new Dimension(1280, 780));
        setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setMinimumSize(new Dimension(800, 600));
                contentPanel.setPreferredSize(new Dimension(0, 0));
                contentPanel.setLayout(new MigLayout(
                    "fill,hidemode 3",
                    // columns
                    "[fill]",
                    // rows
                    "[fill]"));

                //======== panel1 ========
                {
                    panel1.setMinimumSize(new Dimension(800, 600));
                    panel1.setLayout(new MigLayout(
                        "fill,hidemode 3",
                        // columns
                        "[fill]",
                        // rows
                        "[fill]"));

                    //======== splitPane1 ========
                    {
                        splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
                        splitPane1.setResizeWeight(0.5);

                        //======== splitPane2 ========
                        {
                            splitPane2.setResizeWeight(0.5);

                            //======== panelPicture ========
                            {
                                panelPicture.setMinimumSize(new Dimension(400, 300));
                                panelPicture.setPreferredSize(new Dimension(0, 0));
                                panelPicture.setLayout(new MigLayout(
                                    "fill,hidemode 3",
                                    // columns
                                    "[fill]" +
                                    "[fill]",
                                    // rows
                                    "[]" +
                                    "[]" +
                                    "[]"));
                            }
                            splitPane2.setLeftComponent(panelPicture);

                            //======== panelModelView ========
                            {
                                panelModelView.setMinimumSize(new Dimension(400, 300));
                                panelModelView.setPreferredSize(new Dimension(0, 0));
                                panelModelView.addMouseMotionListener(new MouseMotionAdapter() {
                                    @Override
                                    public void mouseDragged(MouseEvent e) {
                                        panelModelViewMouseDragged(e);
                                    }
                                });
                                panelModelView.setLayout(new MigLayout(
                                    "fill,hidemode 3",
                                    // columns
                                    "[fill]" +
                                    "[fill]",
                                    // rows
                                    "[]" +
                                    "[]" +
                                    "[]"));
                            }
                            splitPane2.setRightComponent(panelModelView);
                        }
                        splitPane1.setTopComponent(splitPane2);

                        //======== scrollPane1 ========
                        {
                            scrollPane1.setMinimumSize(new Dimension(180, 320));

                            //---- textAreaTextOutput ----
                            textAreaTextOutput.setMinimumSize(new Dimension(800, 300));
                            textAreaTextOutput.setPreferredSize(new Dimension(0, 0));
                            scrollPane1.setViewportView(textAreaTextOutput);
                        }
                        splitPane1.setBottomComponent(scrollPane1);
                    }
                    panel1.add(splitPane1, "cell 0 0");
                }
                contentPanel.add(panel1, "cell 0 0");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
        }
        add(dialogPane, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JSplitPane splitPane1;
    private JSplitPane splitPane2;
    private JPanel panelPicture;
    private JPanel panelModelView;
    private JScrollPane scrollPane1;
    private JTextArea textAreaTextOutput;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public void loadImage(File selectedFile) {
        this.image = ImageIO.read(selectedFile);
        if (image != null && testHumanHeadTexturing != null)
            testHumanHeadTexturing.setJpg(image);
    }

    public void run() {
        testHumanHeadTexturing = TestHumanHeadTexturing.startAll(this, image, model);
        while (isVisible() && isRunning) {
            try {
                // Display 3D scene
                BufferedImage image1 = testHumanHeadTexturing.getPicture();
                if (image1 != null) {
                    Graphics graphics = panelModelView.getGraphics();
                    if (graphics != null) {
                        graphics.drawImage(image1, 0, 0, panelModelView.getWidth(), panelModelView.getHeight(), null);
                        displayPointsOut(pointsInModel);
                    }
                }
                if (image != null) {
                    Graphics graphics = panelPicture.getGraphics();
                    if (graphics != null) {
                        graphics.drawImage(image, 0, 0, panelPicture.getWidth(), panelPicture.getHeight(), null);
                        displayPointsIn(pointsInImage);
                    }
                }
                Thread.sleep(100);
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (model != null) {
                testHumanHeadTexturing.setObj(model);
            }
            if (image != null) {
                testHumanHeadTexturing.setJpg(image);
            }
        }

    }

    private void displayPointsIn(HashMap<String, Point3D> points) {
        JPanel panelDraw = panelPicture;
        try {
            Thread.sleep(200);
            if (image != null && panelDraw != null) {
                Graphics graphics = panelDraw.getGraphics();
                if (graphics != null) {
                    // Display image
                    graphics.setColor(Color.BLACK);
                    points.forEach(new BiConsumer<String, Point3D>() {
                        @Override
                        public void accept(String s, Point3D point3D) {
                            Graphics graphics = panelDraw.getGraphics();
                            graphics.drawOval((int) (double) (point3D.getX() / image.getWidth() * panelDraw.getWidth()) - 1,
                                    (int) (double) (point3D.getY() / image.getHeight() * panelDraw.getHeight()) - 1, 3, 3);
                        }

                    });
                    // Display 3D scene
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void displayPointsOut(HashMap<String, Point3D> points) {
        JPanel panelDraw = panelModelView;
        if (image != null && panelDraw != null) {
            // Display image
            points.forEach(new BiConsumer<String, Point3D>() {
                @Override
                public void accept(String s, Point3D uvModel) {
                    Point3D uvFace = model.findUvFace(uvModel.getX(), uvModel.getY());
                    Point point = testHumanHeadTexturing.getZ().camera().coordonneesPoint2D(uvFace, testHumanHeadTexturing.getZ());
                    // +++ Model 3DObj : calculerPoint3D(u,v) +++
                    Graphics graphics = panelDraw.getGraphics();
                    graphics.setColor(Color.BLACK);
                    graphics.drawOval((int) (double) (point.getX() / testHumanHeadTexturing.getZ().la() * panelModelView.getWidth()) - 1,
                            (int) (double) (point.getY() / testHumanHeadTexturing.getZ().ha() * panelModelView.getHeight()) - 1,
                            3, 3);
                }

            });
        }

    }

    public void add3DModel(File selectedFile) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
            model = new E3Model(bufferedReader, true, selectedFile.getAbsolutePath());
            if (model != null && testHumanHeadTexturing != null)
                testHumanHeadTexturing.setObj(model);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTxt(File selectedFile) {
        pointsInImage = new HashMap<String, Point3D>();
        try {
            Scanner bufferedReader = new Scanner(new FileReader(selectedFile));
            String line = "";
            while (bufferedReader.hasNextLine()) {
                line = bufferedReader.nextLine();
                Point3D point = new Point3D();
                String landmarkType;
                double x;
                double y;
                if (!line.isEmpty()) {
                    if (Character.isLetter(line.charAt(0))) {
                        landmarkType = line;
                        // X
                        line = bufferedReader.nextLine();
                        x = Double.parseDouble(line);
                        // Y
                        line = bufferedReader.nextLine();
                        y = Double.parseDouble(line);
                        // Blank line
                        line = bufferedReader.nextLine();

                        pointsInImage.put(landmarkType, new Point3D(x, y, 0.0));
                    }
                }
            }
            pointsInModel = new HashMap<>();
            pointsInImage.forEach(new BiConsumer<String, Point3D>() {
                @Override
                public void accept(String s, Point3D point3D) {
                    Point3D copy = new Point3D(point3D);
                    pointsInModel.put(s, new Point3D(copy.get(0) / image.getWidth(), copy.get(1) / image.getHeight(), 0.0));
                }
            });
            Logger.getAnonymousLogger().log(Level.INFO, "Loaded {0} points in image", pointsInImage.size());
            bufferedReader.close();
        } catch (IOException | RuntimeException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void editPointPosition() {
        mode = EDIT_POINT_POSITION;
        panelPicture.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                if (image == null || model != null) {
                    Point3D[] pNear = new Point3D[]{new Point3D(1.0 * point.getX() * image.getWidth() / panelPicture.getWidth(),
                            1.0 * point.getY() * image.getHeight() / panelPicture.getHeight(), 0.)};
                    AtomicDouble distanceMin = new AtomicDouble(Double.MAX_VALUE);
                    pointsInImage.forEach(new BiConsumer<String, Point3D>() {
                        @Override
                        public void accept(String s, Point3D point3D) {
                            if (Point3D.distance(pNear[0], point3D) < distanceMin.get()) {
                                distanceMin.set(Point3D.distance(pNear[0], point3D));
                                pFound = point3D;
                                landmarkType = s;
                            }
                        }
                    });
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        panelModelView.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                if (image == null || model != null) {
                    int x = point.x;
                    int y = point.y;
                    ZBufferImpl.ImageMapElement ime = ((ZBufferImpl) testHumanHeadTexturing.getZ()).ime;
                    Point3D pointIme = null;
                    if (ime.checkCoordinates(x, y)) {
                        u = ime.getuMap()[x][y];
                        v = ime.getvMap()[x][y];
                        pointIme = ime.getElementPoint(x, y);
                    }
                    Point3D finalPointIme = pointIme;
                    pointsInModel.forEach(new BiConsumer<String, Point3D>() {
                        @Override
                        public void accept(String s, Point3D point3D) {
                            if (s.equals(landmarkType) && finalPointIme != null) {
                                pointsInModel.put(s, finalPointIme);
                            }
                        }
                    });
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
