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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.type.ArrayType;
import javax.swing.*;

import javaAnd.awt.image.imageio.ImageIO;
import net.miginfocom.swing.*;
import one.empty3.apps.morph.*;
import one.empty3.library.ECBufferedImage;
import one.empty3.library.Point3D;
import one.empty3.library.core.testing.Test;
import one.empty3.library.objloader.E3Model;

/**
 * @author manue
 */
public class EditPolygonsMappings extends JPanel implements Runnable {

    private BufferedImage image;
    private E3Model model;
    private TestHumanHeadTexturing testHumanHeadTexturing;

    public EditPolygonsMappings(Window owner) {
        initComponents();
    }

    public EditPolygonsMappings() {

    }

    public JPanel getContentPanel() {
        return contentPanel;
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
        testHumanHeadTexturing = TestHumanHeadTexturing.startAll(image, model);
        while (true) {
            try {
                Thread.sleep(200);
                if (image != null && panelPicture != null) {
                    // Display image
                    Graphics graphics = panelPicture.getGraphics();
                    graphics.drawImage(image, 0, 0, panelPicture.getWidth(), panelPicture.getHeight(), null);
                    // Display 3D scene
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                // Display 3D scene
                ECBufferedImage image1 = testHumanHeadTexturing.getZ().image();
                if (image1 != null) {
                    Graphics graphics = panelModelView.getGraphics();
                    graphics.drawImage(image1, 0, 0, panelModelView.getWidth(), panelModelView.getHeight(), null);
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }

            if (model != null) {
                testHumanHeadTexturing.setObj(model);
            }
            if (image != null) {
                testHumanHeadTexturing.setJpg(image);
            }
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
        HashMap<String, Point3D> points = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                Point3D point = new Point3D();
                String landmarkType;
                double x;
                double y;
                if (!line.isEmpty()) {
                    if (Character.isLetter(line.charAt(0))) {
                        landmarkType = line;
                        line = bufferedReader.readLine();
                        x = Double.parseDouble(line);
                        line = bufferedReader.readLine();
                        y = Double.parseDouble(line);

                        points.put(landmarkType, new Point3D(x, y, 0.0));
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException | RuntimeException ex) {
            throw new RuntimeException(ex);
        }

    }
}
