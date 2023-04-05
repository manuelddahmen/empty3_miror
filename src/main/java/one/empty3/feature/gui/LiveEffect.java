/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * Created by JFormDesigner on Fri Aug 20 16:53:54 CEST 2021
 */

package one.empty3.feature.gui;

import com.github.sarxos.webcam.Webcam;
import net.miginfocom.swing.MigLayout;
import one.empty3.feature.ClassSchemaBuilder;
import one.empty3.feature.ThreadEffectDisplay;
import one.empty3.feature.motion.DiffMotion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Manuel Dahmen
 */
public class LiveEffect extends JFrame {
    public ThreadEffectDisplay threadEffectDisplay;
    public Dimension[] viewSizes;
    private ClassSchemaBuilder main;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JComboBox comboBoxDimenisions;
    private JScrollPane scrollPane2;
    private JComboBox<String> comboBoxMotion;
    private JButton buttonStop1;
    private JScrollPane scrollPane1;
    private JPanel panel1;

    public LiveEffect() {
        initComponents();
        init();
    }

    public void init() {
        threadEffectDisplay
                = new ThreadEffectDisplay();
        threadEffectDisplay.setJpanel(panel1);
        threadEffectDisplay.motion = new LastFrameMotion();
        threadEffectDisplay.start();

        viewSizes = Webcam.getDefault().getViewSizes();

        threadEffectDisplay.motion = new DiffMotion();
        threadEffectDisplay.setMotionActive(true);
        threadEffectDisplay.setEffectActive(true);

        this.comboBoxDimenisions.setModel(new DefaultComboBoxModel(Webcam.getDefault().getViewSizes()));
        this.comboBoxDimenisions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                threadEffectDisplay.webcam
                        .setViewSize((Dimension) comboBoxDimenisions
                                .getItemAt(comboBoxDimenisions.getSelectedIndex()));
            }
        });

    }

    private void panel1ComponentResized(ComponentEvent e) {
        main.setMaxRes(Math.max(getWidth(), getHeight()));
    }

    private void comboBoxMotionItemStateChanged(ItemEvent e) {
        switch (comboBoxMotion.getSelectedIndex()) {
            case 0:
                //Aucun mouvement, effet
                threadEffectDisplay.motion = new LastFrameMotion();
                threadEffectDisplay.setMotionActive(false);
                threadEffectDisplay.setEffectActive(true);
                break;
            case 1:
                //Aucun mouvement, aucun effet
                threadEffectDisplay.motion = new LastFrameMotion();
                threadEffectDisplay.setMotionActive(false);
                threadEffectDisplay.setEffectActive(false);
                break;
            case 2:
                //Mouvement, effet
                threadEffectDisplay.motion = new DiffMotion();
                threadEffectDisplay.setMotionActive(true);
                threadEffectDisplay.setEffectActive(true);
                break;
            case 3:
                //Mouvement, aucun effet
                threadEffectDisplay.motion = new DiffMotion();
                threadEffectDisplay.setMotionActive(true);
                threadEffectDisplay.setEffectActive(false);
                break;
        }
    }

    private void comboBoxDimenisionsActionPerformed(ActionEvent e) {
        Dimension selectedItem = threadEffectDisplay.webcam.getViewSizes()[comboBoxDimenisions.getSelectedIndex()];
        comboBoxDimenisions.actionPerformed(e);
    }

    private void stop(ActionEvent e) {
        threadEffectDisplay.stop1();
        init();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        comboBoxDimenisions = new JComboBox();
        scrollPane2 = new JScrollPane();
        comboBoxMotion = new JComboBox<>();
        buttonStop1 = new JButton();
        scrollPane1 = new JScrollPane();
        panel1 = new JPanel();

        //======== this ========
        setTitle("Effect viewer");
        setMinimumSize(new Dimension(640, 480));
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "fill",
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
                        "[]"));

        //---- comboBoxDimenisions ----
        comboBoxDimenisions.setDoubleBuffered(true);
        comboBoxDimenisions.addActionListener(e -> comboBoxDimenisionsActionPerformed(e));
        contentPane.add(comboBoxDimenisions, "cell 0 0 5 1");

        //======== scrollPane2 ========
        {
            scrollPane2.setDoubleBuffered(true);

            //---- comboBoxMotion ----
            comboBoxMotion.setModel(new DefaultComboBoxModel<>(new String[]{
                    "Aucun mouvement, aucun effet",
                    "Aucun mouvement, effet",
                    "Mouvement diff, aucun effet",
                    "Mouvement diff, effet"
            }));
            comboBoxMotion.setDoubleBuffered(true);
            comboBoxMotion.addItemListener(e -> {
                comboBoxMotionItemStateChanged(e);
                comboBoxMotionItemStateChanged(e);
            });
            scrollPane2.setViewportView(comboBoxMotion);
        }
        contentPane.add(scrollPane2, "cell 0 0 5 1");

        //---- buttonStop1 ----
        buttonStop1.setText("STOP");
        buttonStop1.addActionListener(e -> stop(e));
        contentPane.add(buttonStop1, "cell 0 0 5 1");

        //======== scrollPane1 ========
        {
            scrollPane1.setDoubleBuffered(true);

            //======== panel1 ========
            {
                panel1.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        panel1ComponentResized(e);
                    }
                });
                panel1.setLayout(new MigLayout(
                        "fill,hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]",
                        // rows
                        "[]" +
                                "[]" +
                                "[]"));
            }
            scrollPane1.setViewportView(panel1);
        }
        contentPane.add(scrollPane1, "cell 0 1 5 4,dock center");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setFileIn(File fileOut) {
        try {
            if (fileOut != null) {
                BufferedImage read = ImageIO.read(fileOut);
                if (read != null) {
                    threadEffectDisplay.setImageIn(read);
                } else {
                    Logger.getAnonymousLogger().log(Level.INFO, "No image in set after ImageIO.read(fileOut)");
                }
            } else {
                Logger.getAnonymousLogger().log(Level.INFO, "No image in set after processing files fileOut==null");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public ClassSchemaBuilder getMainWindow() {
        return main;
    }

    public void setMainWindow(ClassSchemaBuilder classSchemaBuilder) {
        this.main = classSchemaBuilder;
        threadEffectDisplay.setMain(main);
        threadEffectDisplay.setDirectEffect(this);
        //main.setMaxRes(100);
    }
}
