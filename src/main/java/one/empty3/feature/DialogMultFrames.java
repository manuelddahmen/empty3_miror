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

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author manue
 */
public class DialogMultFrames extends JDialog {
    List<ClassSchemaBuilder.DiagramElement> diagramElements;
    ClassSchemaBuilder.ClassElement currentElement = null;
    private TreeDiagram treeDiagram = null;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JComboBox comboBox1;
    private JButton buttonUpdate;
    private JLabel labelR;
    private JTextField textFieldR;
    private JLabel labelX;
    private JTextField textFieldX;
    private JButton buttonApply;
    private JLabel labelG;
    private JTextField textFieldG;
    private JLabel labelY;
    private JTextField textFieldY;
    private JLabel labelB;
    private JTextField textFieldB;
    private JLabel labelW;
    private JTextField textFieldW;
    private JLabel labelA;
    private JTextField textFieldA;
    private JLabel labelH;
    private JTextField textFieldH;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;

    public DialogMultFrames(Window owner) {
        super(owner);

        diagramElements = ((ClassSchemaBuilder) owner).getDiagramElements();

        ClassSchemaBuilder.DiagramElement current = ((ClassSchemaBuilder)owner).getSelectedElement();

        fillList();

        updateData(null);

        initComponents();
    }

    public void fillList() {
        /*int i = 0;
        TreeNodeDiagram head = treeDiagram.head;
        if(head!=null) {
            TreeNodeDiagram current = head;
            while(current!=null) {


            }
        }*/
    }

    private void updateData(ActionEvent e) {
        treeDiagram = new TreeDiagram(diagramElements);

        fillList();


    }

    private void applyChanges(ActionEvent e) {
        // TODO add your code here
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JPanel getDialogPane() {
        return dialogPane;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public JButton getButtonUpdate() {
        return buttonUpdate;
    }

    public JLabel getLabelR() {
        return labelR;
    }

    public JTextField getTextFieldR() {
        return textFieldR;
    }

    public JLabel getLabelX() {
        return labelX;
    }

    public JTextField getTextFieldX() {
        return textFieldX;
    }

    public JButton getButtonApply() {
        return buttonApply;
    }

    public JLabel getLabelG() {
        return labelG;
    }

    public JTextField getTextFieldG() {
        return textFieldG;
    }

    public JLabel getLabelY() {
        return labelY;
    }

    public JTextField getTextFieldY() {
        return textFieldY;
    }

    public JLabel getLabelB() {
        return labelB;
    }

    public JTextField getTextFieldB() {
        return textFieldB;
    }

    public JLabel getLabelW() {
        return labelW;
    }

    public JTextField getTextFieldW() {
        return textFieldW;
    }

    public JLabel getLabelA() {
        return labelA;
    }

    public JTextField getTextFieldA() {
        return textFieldA;
    }

    public JLabel getLabelH() {
        return labelH;
    }

    public JTextField getTextFieldH() {
        return textFieldH;
    }

    public JPanel getButtonBar() {
        return buttonBar;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.library.core.testing.Bundle");
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        comboBox1 = new JComboBox();
        buttonUpdate = new JButton();
        labelR = new JLabel();
        textFieldR = new JTextField();
        labelX = new JLabel();
        textFieldX = new JTextField();
        buttonApply = new JButton();
        labelG = new JLabel();
        textFieldG = new JTextField();
        labelY = new JLabel();
        textFieldY = new JTextField();
        labelB = new JLabel();
        textFieldB = new JTextField();
        labelW = new JLabel();
        textFieldW = new JTextField();
        labelA = new JLabel();
        textFieldA = new JTextField();
        labelH = new JLabel();
        textFieldH = new JTextField();
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
                    "[]" +
                    "[]"));
                contentPanel.add(comboBox1, "cell 0 1 4 1");

                //---- buttonUpdate ----
                buttonUpdate.setText(bundle.getString("DialogMultFrames.buttonUpdate.text"));
                buttonUpdate.addActionListener(e -> updateData(e));
                contentPanel.add(buttonUpdate, "cell 4 1");

                //---- labelR ----
                labelR.setText(bundle.getString("DialogMultFrames.labelR.text"));
                contentPanel.add(labelR, "cell 0 3");
                contentPanel.add(textFieldR, "cell 1 3");

                //---- labelX ----
                labelX.setText(bundle.getString("DialogMultFrames.labelX.text"));
                contentPanel.add(labelX, "cell 2 3");
                contentPanel.add(textFieldX, "cell 3 3");

                //---- buttonApply ----
                buttonApply.setText(bundle.getString("DialogMultFrames.buttonApply.text"));
                buttonApply.addActionListener(e -> applyChanges(e));
                contentPanel.add(buttonApply, "cell 4 3");

                //---- labelG ----
                labelG.setText(bundle.getString("DialogMultFrames.labelG.text"));
                contentPanel.add(labelG, "cell 0 4");
                contentPanel.add(textFieldG, "cell 1 4");

                //---- labelY ----
                labelY.setText(bundle.getString("DialogMultFrames.labelY.text"));
                contentPanel.add(labelY, "cell 2 4");
                contentPanel.add(textFieldY, "cell 3 4");

                //---- labelB ----
                labelB.setText(bundle.getString("DialogMultFrames.labelB.text"));
                contentPanel.add(labelB, "cell 0 5");
                contentPanel.add(textFieldB, "cell 1 5");

                //---- labelW ----
                labelW.setText(bundle.getString("DialogMultFrames.labelW.text"));
                contentPanel.add(labelW, "cell 2 5");
                contentPanel.add(textFieldW, "cell 3 5");

                //---- labelA ----
                labelA.setText(bundle.getString("DialogMultFrames.labelA.text"));
                contentPanel.add(labelA, "cell 0 6");
                contentPanel.add(textFieldA, "cell 1 6");

                //---- labelH ----
                labelH.setText(bundle.getString("DialogMultFrames.labelH.text"));
                contentPanel.add(labelH, "cell 2 6");
                contentPanel.add(textFieldH, "cell 3 6");
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
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
