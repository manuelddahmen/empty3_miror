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

package one.empty3.apps.faceapp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import javaAnd.awt.image.imageio.ImageIO;
import net.miginfocom.swing.*;
import one.empty3.apps.morph.*;
import one.empty3.library.core.nurbs.F;
import one.empty3.library.objloader.E3Model;

/**
 * @author manue
 */
public class EditPolygonsMappings extends JPanel {

    private BufferedImage image;
    private E3Model model;

    public EditPolygonsMappings(Window owner) {
        initComponents();
    }

    public EditPolygonsMappings() {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Dahmen Manuel
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        var panel1 = new JPanel();
        splitPane1 = new JSplitPane();
        splitPane2 = new JSplitPane();
        panelPicture = new JPanel();
        panelModelView = new JPanel();
        scrollPane1 = new JScrollPane();
        textAreaTextOutput = new JTextArea();
        buttonBar = new JPanel();

        //======== this ========
        setPreferredSize(new Dimension(1280, 780));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax
        . swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing
        . border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .
        Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red
        ) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override
        public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName (
        ) )) throw new RuntimeException( ); }} );
        setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "fill,insets dialog,hidemode 3",
                    // columns
                    "[fill]",
                    // rows
                    "[fill]"));

                //======== panel1 ========
                {
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
                            scrollPane1.setViewportView(textAreaTextOutput);
                        }
                        splitPane1.setBottomComponent(scrollPane1);
                    }
                    panel1.add(splitPane1, "cell 0 0");
                }
                contentPanel.add(panel1, "cell 0 0");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    null));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        add(dialogPane, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Dahmen Manuel
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JSplitPane splitPane1;
    private JSplitPane splitPane2;
    private JPanel panelPicture;
    private JPanel panelModelView;
    private JScrollPane scrollPane1;
    private JTextArea textAreaTextOutput;
    private JPanel buttonBar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public void loadImage(File selectedFile) {
        this.image = ImageIO.read(selectedFile);
        // TODO
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                if (image != null) {
                    Graphics graphics = getGraphics();
                    graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void add3DModel(File selectedFile) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
            model = new E3Model(bufferedReader, true, selectedFile.getAbsolutePath());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
