/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
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
 * Created by JFormDesigner on Fri Apr 07 14:28:25 CEST 2023
 */

package one.empty3.feature;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author manue
 */
public class DialogMultFrames extends JDialog {
    public DialogMultFrames(Window owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.library.core.testing.Bundle");
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        comboBox1 = new JComboBox();
        button1 = new JButton();
        label1 = new JLabel();
        textField1 = new JTextField();
        label5 = new JLabel();
        textField5 = new JTextField();
        button2 = new JButton();
        label2 = new JLabel();
        textField2 = new JTextField();
        label6 = new JLabel();
        textField6 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label7 = new JLabel();
        textField7 = new JTextField();
        label4 = new JLabel();
        textField4 = new JTextField();
        label8 = new JLabel();
        textField8 = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "insets dialog,hidemode 3",
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
                    "[]"));
                contentPanel.add(comboBox1, "cell 0 0 4 1");

                //---- button1 ----
                button1.setText(bundle.getString("DialogMultFrames.button1.text"));
                contentPanel.add(button1, "cell 4 0");

                //---- label1 ----
                label1.setText(bundle.getString("DialogMultFrames.label1.text"));
                contentPanel.add(label1, "cell 0 2");
                contentPanel.add(textField1, "cell 1 2");

                //---- label5 ----
                label5.setText(bundle.getString("DialogMultFrames.label5.text"));
                contentPanel.add(label5, "cell 2 2");
                contentPanel.add(textField5, "cell 3 2");

                //---- button2 ----
                button2.setText(bundle.getString("DialogMultFrames.button2.text"));
                contentPanel.add(button2, "cell 4 2");

                //---- label2 ----
                label2.setText(bundle.getString("DialogMultFrames.label2.text"));
                contentPanel.add(label2, "cell 0 3");
                contentPanel.add(textField2, "cell 1 3");

                //---- label6 ----
                label6.setText(bundle.getString("DialogMultFrames.label6.text"));
                contentPanel.add(label6, "cell 2 3");
                contentPanel.add(textField6, "cell 3 3");

                //---- label3 ----
                label3.setText(bundle.getString("DialogMultFrames.label3.text"));
                contentPanel.add(label3, "cell 0 4");
                contentPanel.add(textField3, "cell 1 4");

                //---- label7 ----
                label7.setText(bundle.getString("DialogMultFrames.label7.text"));
                contentPanel.add(label7, "cell 2 4");
                contentPanel.add(textField7, "cell 3 4");

                //---- label4 ----
                label4.setText(bundle.getString("DialogMultFrames.label4.text"));
                contentPanel.add(label4, "cell 0 5");
                contentPanel.add(textField4, "cell 1 5");

                //---- label8 ----
                label8.setText(bundle.getString("DialogMultFrames.label8.text"));
                contentPanel.add(label8, "cell 2 5");
                contentPanel.add(textField8, "cell 3 5");
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

                //---- okButton ----
                okButton.setText(bundle.getString("DialogMultFrames.okButton.text"));
                buttonBar.add(okButton, "cell 0 0");

                //---- cancelButton ----
                cancelButton.setText(bundle.getString("DialogMultFrames.cancelButton.text"));
                buttonBar.add(cancelButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JComboBox comboBox1;
    private JButton button1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label5;
    private JTextField textField5;
    private JButton button2;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label6;
    private JTextField textField6;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label7;
    private JTextField textField7;
    private JLabel label4;
    private JTextField textField4;
    private JLabel label8;
    private JTextField textField8;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
