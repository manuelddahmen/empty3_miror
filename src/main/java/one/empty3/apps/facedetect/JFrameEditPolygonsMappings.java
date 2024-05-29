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
        editPolygonsMappings2 = new EditPolygonsMappings(this);
        setContentPane(editPolygonsMappings2);
        pack();
        setVisible(true);

        new Thread(editPolygonsMappings2).start();
    }

    private void menuItemLoadImage(ActionEvent e) {
        JFileChooser loadImage = new JFileChooser();
        loadImage.showOpenDialog(this);
        editPolygonsMappings2.loadImage(loadImage.getSelectedFile());
    }

    private void menuItemAdd3DModel(ActionEvent e) {
        JFileChooser add3DModel = new JFileChooser();
        add3DModel.showOpenDialog(this);
        editPolygonsMappings2.add3DModel(add3DModel.getSelectedFile());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.library.core.testing.Bundle");
        menuBar1 = new JMenuBar();
        menu2 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        editPolygonsMappings2 = new EditPolygonsMappings();
        menu3 = new JMenu();

        //======== this ========
        setMinimumSize(new Dimension(830, 600));
        setFocusableWindowState(false);
        setTitle(bundle.getString("JFrameEditPolygonsMappings.this.title"));
        setAutoRequestFocus(false);
        setMaximizedBounds(null);
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

        //---- editPolygonsMappings2 ----
        editPolygonsMappings2.setMinimumSize(new Dimension(800, 600));
        contentPane.add(editPolygonsMappings2, "cell 0 0");

        //======== menu3 ========
        {
            menu3.setText(bundle.getString("JFrameEditPolygonsMappings.menu3.text"));
            menu3.setVisible(false);
        }
        contentPane.add(menu3, "cell 0 0");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenu menu2;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private EditPolygonsMappings editPolygonsMappings2;
    private JMenu menu3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public static void main(String[] args) {
        JFrameEditPolygonsMappings jFrameEditPolygonsMappings = new JFrameEditPolygonsMappings();
    }

}
