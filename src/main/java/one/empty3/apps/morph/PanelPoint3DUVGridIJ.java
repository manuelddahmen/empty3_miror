/*
 * Created by JFormDesigner on Wed Dec 28 14:10:32 CET 2022
 */

package one.empty3.apps.morph;

import java.beans.*;
import javax.swing.*;
import net.miginfocom.swing.*;
import one.empty3.library.Point3D;

/**
 * @author manue
 */
public class PanelPoint3DUVGridIJ extends JPanel {
    private ImageControls imageControls;

    class DataPoint {
        int i, j;
        Point3D point, uv;
    }

    public PanelPoint3DUVGridIJ() {
        initComponents();
    }

    public void setImageControls(ImageControls imageControls) {
        this.imageControls = imageControls;
    }
    public JLabel getLabel1() {
        return label1;
    }

    public JTextField getTextFieldI() {
        return textFieldI;
    }

    public JLabel getLabel3() {
        return label3;
    }

    public JTextField getTextFieldU() {
        return textFieldU;
    }

    public JLabel getLabel5() {
        return label5;
    }

    public JTextField getTextFieldX() {
        return textFieldX;
    }

    public JComboBox<String> getComboBox1() {
        return comboBox1;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public JTextField getTextFieldJ() {
        return textFieldJ;
    }

    public JLabel getLabel4() {
        return label4;
    }

    public JTextField getTextFieldV() {
        return textFieldV;
    }

    public JLabel getLabel7() {
        return label7;
    }

    public JTextField getTextFieldY() {
        return textFieldY;
    }

    private void textFieldIPropertyChange(PropertyChangeEvent e) {

    }

    private void textFieldJPropertyChange(PropertyChangeEvent e) {

    }

    private void textFieldUPropertyChange(PropertyChangeEvent e) {
        // TODO add your code here
    }

    private void textFieldVPropertyChange(PropertyChangeEvent e) {

    }

    private void textFieldXPropertyChange(PropertyChangeEvent e) {

    }

    private void textFieldYPropertyChange(PropertyChangeEvent e) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        label1 = new JLabel();
        textFieldI = new JTextField();
        label3 = new JLabel();
        textFieldU = new JTextField();
        label5 = new JLabel();
        textFieldX = new JTextField();
        comboBox1 = new JComboBox<>();
        label2 = new JLabel();
        textFieldJ = new JTextField();
        label4 = new JLabel();
        textFieldV = new JTextField();
        label7 = new JLabel();
        textFieldY = new JTextField();

        //======== this ========
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("i");
        add(label1, "cell 0 0");

        //---- textFieldI ----
        textFieldI.addPropertyChangeListener("changedI", e -> textFieldIPropertyChange(e));
        add(textFieldI, "cell 1 0");

        //---- label3 ----
        label3.setText("u");
        add(label3, "cell 2 0");

        //---- textFieldU ----
        textFieldU.addPropertyChangeListener("changedU", e -> textFieldUPropertyChange(e));
        add(textFieldU, "cell 3 0");

        //---- label5 ----
        label5.setText("x");
        add(label5, "cell 4 0");

        //---- textFieldX ----
        textFieldX.addPropertyChangeListener("changedX", e -> textFieldXPropertyChange(e));
        add(textFieldX, "cell 5 0");

        //---- comboBox1 ----
        comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
            "Update",
            "Delete row",
            "Delete column",
            "Insert row before",
            "Insert column before",
            "Insert row after",
            "Insert column after"
        }));
        add(comboBox1, "cell 7 0");

        //---- label2 ----
        label2.setText("j");
        add(label2, "cell 0 1");

        //---- textFieldJ ----
        textFieldJ.addPropertyChangeListener("changedJ", e -> textFieldJPropertyChange(e));
        add(textFieldJ, "cell 1 1");

        //---- label4 ----
        label4.setText("v");
        add(label4, "cell 2 1");

        //---- textFieldV ----
        textFieldV.addPropertyChangeListener("changedV", e -> textFieldVPropertyChange(e));
        add(textFieldV, "cell 3 1");

        //---- label7 ----
        label7.setText("y");
        add(label7, "cell 4 1");

        //---- textFieldY ----
        textFieldY.addPropertyChangeListener("changedY", e -> textFieldYPropertyChange(e));
        add(textFieldY, "cell 5 1");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JLabel label1;
    private JTextField textFieldI;
    private JLabel label3;
    private JTextField textFieldU;
    private JLabel label5;
    private JTextField textFieldX;
    private JComboBox<String> comboBox1;
    private JLabel label2;
    private JTextField textFieldJ;
    private JLabel label4;
    private JTextField textFieldV;
    private JLabel label7;
    private JTextField textFieldY;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
