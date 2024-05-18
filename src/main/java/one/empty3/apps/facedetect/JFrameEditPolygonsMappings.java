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

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import net.miginfocom.swing.*;

/**
 * @author manue
 */
public class JFrameEditPolygonsMappings extends JFrame {
    public JFrameEditPolygonsMappings() {
        initComponents();
        setContentPane(new EditPolygonsMappings());
        setVisible(true);
    }

    private void menuItemLoadImage(ActionEvent e) {
        JFileChooser loadImage = new JFileChooser();
        loadImage.showOpenDialog(this);
        editPolygonsMappings1.loadImage(loadImage.getSelectedFile());
    }

    private void menuItemAdd3DModel(ActionEvent e) {
        JFileChooser add3DModel = new JFileChooser();
        add3DModel.showOpenDialog(this);
        editPolygonsMappings1.add3DModel(add3DModel.getSelectedFile());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Dahmen Manuel
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.library.core.testing.Bundle");
        menuBar1 = new JMenuBar();
        menu2 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        menu3 = new JMenu();
        editPolygonsMappings2 = new EditPolygonsMappings();
        editPolygonsMappings1 = new EditPolygonsMappings();

        //======== this ========
        setMinimumSize(new Dimension(640, 480));
        setFocusableWindowState(false);
        setEnabled(false);
        setVisible(true);
        setTitle(bundle.getString("JFrameEditPolygonsMappings.this.title"));
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,hidemode 3",
            // columns
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
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

                //---- menuItem2 ----
                menuItem2.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem2.text"));
                menu2.add(menuItem2);

                //---- menuItem3 ----
                menuItem3.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem3.text"));
                menu2.add(menuItem3);

                //---- menuItem4 ----
                menuItem4.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem4.text"));
                menuItem4.addActionListener(e -> menuItemAdd3DModel(e));
                menu2.add(menuItem4);

                //---- menuItem5 ----
                menuItem5.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem5.text"));
                menu2.add(menuItem5);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);

        //======== menu3 ========
        {
            menu3.setText(bundle.getString("JFrameEditPolygonsMappings.menu3.text"));
            menu3.setVisible(false);
        }
        contentPane.add(menu3, "cell 0 0");
        contentPane.add(editPolygonsMappings2, "cell 0 0 1 5");

        //---- editPolygonsMappings1 ----
        editPolygonsMappings1.setMinimumSize(new Dimension(300, 240));
        editPolygonsMappings1.setVisible(false);
        contentPane.add(editPolygonsMappings1, "cell 0 0 1 5");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Dahmen Manuel
    private JMenuBar menuBar1;
    private JMenu menu2;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JMenu menu3;
    private EditPolygonsMappings editPolygonsMappings2;
    private EditPolygonsMappings editPolygonsMappings1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public static void main(String[] args) {
        JFrameEditPolygonsMappings jFrameEditPolygonsMappings = new JFrameEditPolygonsMappings();
        jFrameEditPolygonsMappings.setVisible(true);
    }

}
