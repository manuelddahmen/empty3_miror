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
 * Created by JFormDesigner on Sat May 18 12:25:12 CEST 2024
 */

package one.empty3.apps.facedetect;

import net.miginfocom.swing.MigLayout;
import one.empty3.library.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ResourceBundle;

/**
 * @author manue
 */
public class JFrameEditPolygonsMappings extends JFrame {

    private static final int EDIT_POINT_POSITION = 1;
    File lastDirectory;
    private Config config;
    Thread threadDisplay;
    private int mode = 0;

    public JFrameEditPolygonsMappings() {
        config = new Config();
        File fileDirectoryDefault = config.getDefaultFileOutput();
        if (fileDirectoryDefault == null)
            config.setDefaultFileOutput(new File("."));
        String lastDirectoryTmpStr = config.getMap().get("D3ModelFaceTexturing");
        if (lastDirectoryTmpStr == null) {
            lastDirectoryTmpStr = ".";
            config.getMap().put("D3ModelFaceTexturing", lastDirectoryTmpStr);
        }
        config.save();
        lastDirectory = new File(lastDirectoryTmpStr);
        if (!lastDirectory.exists())
            config.save();
        initComponents();
        editPolygonsMappings2 = new EditPolygonsMappings(this);
        //editPolygonsMappings2.testHumanHeadTexturing.setGenerate(editPolygonsMappings2.testHumanHeadTexturing);
        setContentPane(editPolygonsMappings2);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        threadDisplay = new Thread(editPolygonsMappings2);
        threadDisplay.start();
    }

    private void menuItemLoadImage(ActionEvent e) {
        JFileChooser loadImage = new JFileChooser();
        if (lastDirectory != null)
            loadImage.setCurrentDirectory(lastDirectory);
        if (loadImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            editPolygonsMappings2.loadImage(loadImage.getSelectedFile());
        }
        lastDirectory = loadImage.getCurrentDirectory();
    }

    private void menuItemAdd3DModel(ActionEvent e) {
        JFileChooser add3DModel = new JFileChooser();
        if (lastDirectory != null)
            add3DModel.setCurrentDirectory(lastDirectory);
        if (add3DModel.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            editPolygonsMappings2.add3DModel(add3DModel.getSelectedFile());
        lastDirectory = add3DModel.getCurrentDirectory();
    }

    private void menuItemLoadTxt(ActionEvent e) {
        JFileChooser loadImagePoints = new JFileChooser();
        if (lastDirectory != null)
            loadImagePoints.setCurrentDirectory(lastDirectory);
        if (loadImagePoints.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            editPolygonsMappings2.loadTxt(loadImagePoints.getSelectedFile());
        lastDirectory = loadImagePoints.getCurrentDirectory();
    }

    private void menuItemEditPointPosition(ActionEvent e) {
        editPolygonsMappings2.editPointPosition();
    }

    private void thisWindowClosing(WindowEvent e) {
        config.getMap().put("D3ModelFaceTexturing", lastDirectory.getAbsolutePath());
        config.save();
        try {
            editPolygonsMappings2.testHumanHeadTexturing.setMaxFrames(0);
            editPolygonsMappings2.isRunning = false;
            editPolygonsMappings2.testHumanHeadTexturing.stop();
            Thread.sleep(1000);
        } catch (InterruptedException | RuntimeException ex) {
            ex.printStackTrace();
        }
        super.dispose();
        System.exit(0);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.library.core.testing.Bundle");
        menuBar1 = new JMenuBar();
        menu2 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menu1 = new JMenu();
        menuItemMoveLinesUp = new JMenuItem();
        menuItemMoveLinesDown = new JMenuItem();
        menuItemMoveLinesLeft = new JMenuItem();
        menuItemMoveColumnsRight = new JMenuItem();
        menuItemMoveRectangle = new JMenuItem();
        editPolygonsMappings2 = new EditPolygonsMappings();
        menu3 = new JMenu();

        //======== this ========
        setMinimumSize(new Dimension(830, 600));
        setFocusableWindowState(false);
        setTitle(bundle.getString("JFrameEditPolygonsMappings.this.title"));
        setAutoRequestFocus(false);
        setMaximizedBounds(null);
        setIconImage(new ImageIcon("C:\\Users\\manue\\OneDrive\\Documents\\Downloads\\4out.jpg").getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,novisualpadding,hidemode 3",
            // columns
            "[fill]",
            // rows
            "[]"));

        //======== menuBar1 ========
        {

            //======== menu2 ========
            {
                menu2.setText(bundle.getString("JFrameEditPolygonsMappings.menu2.text"));

                //---- menuItem1 ----
                menuItem1.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem1.text"));
                menuItem1.addActionListener(e -> menuItemLoadImage(e));
                menu2.add(menuItem1);

                //---- menuItem3 ----
                menuItem3.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem3.text"));
                menuItem3.addActionListener(e -> menuItemLoadTxt(e));
                menu2.add(menuItem3);

                //---- menuItem2 ----
                menuItem2.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem2.text"));
                menu2.add(menuItem2);

                //---- menuItem4 ----
                menuItem4.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem4.text"));
                menuItem4.addActionListener(e -> menuItemAdd3DModel(e));
                menu2.add(menuItem4);
            }
            menuBar1.add(menu2);

            //======== menu1 ========
            {
                menu1.setText(bundle.getString("JFrameEditPolygonsMappings.menu1.text"));

                //---- menuItemMoveLinesUp ----
                menuItemMoveLinesUp.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveLinesUp.text"));
                menu1.add(menuItemMoveLinesUp);

                //---- menuItemMoveLinesDown ----
                menuItemMoveLinesDown.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveLinesDown.text"));
                menu1.add(menuItemMoveLinesDown);

                //---- menuItemMoveLinesLeft ----
                menuItemMoveLinesLeft.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveLinesLeft.text"));
                menu1.add(menuItemMoveLinesLeft);

                //---- menuItemMoveColumnsRight ----
                menuItemMoveColumnsRight.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveColumnsRight.text"));
                menu1.add(menuItemMoveColumnsRight);

                //---- menuItemMoveRectangle ----
                menuItemMoveRectangle.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveRectangle.text"));
                menu1.add(menuItemMoveRectangle);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //---- editPolygonsMappings2 ----
        editPolygonsMappings2.setMinimumSize(new Dimension(800, 600));
        contentPane.add(editPolygonsMappings2, "cell 0 0");

        //======== menu3 ========
        {
            menu3.setText(bundle.getString("JFrameEditPolygonsMappings.menu3.text"));
            menu3.setVisible(false);
        }
        contentPane.add(menu3, "cell 0 0");
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenu menu2;
    private JMenuItem menuItem1;
    private JMenuItem menuItem3;
    private JMenuItem menuItem2;
    private JMenuItem menuItem4;
    private JMenu menu1;
    private JMenuItem menuItemMoveLinesUp;
    private JMenuItem menuItemMoveLinesDown;
    private JMenuItem menuItemMoveLinesLeft;
    private JMenuItem menuItemMoveColumnsRight;
    private JMenuItem menuItemMoveRectangle;
    private EditPolygonsMappings editPolygonsMappings2;
    private JMenu menu3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public static void main(String[] args) {
        JFrameEditPolygonsMappings jFrameEditPolygonsMappings = new JFrameEditPolygonsMappings();
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(0);
    }
}
