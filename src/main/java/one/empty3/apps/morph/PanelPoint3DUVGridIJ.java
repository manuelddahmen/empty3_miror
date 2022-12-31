/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

/*
 * Created by JFormDesigner on Wed Dec 28 14:10:32 CET 2022
 */

package one.empty3.apps.morph;

import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

import com.android.tools.r8.graph.D;
import net.miginfocom.swing.*;
import one.empty3.library.CopyRepresentableError;
import one.empty3.library.LumiereElement;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

/**
 * @author manuel
 */
public class PanelPoint3DUVGridIJ extends JPanel {
    private ImageControls imageControls;
    private DataPoint dataPoint;

    public DataPoint getDataPoint() {
        return dataPoint;
    }

    public void loadDataPoint() {
        if(dataPoint==null)
            dataPoint = new DataPoint();
        getDataPoint().point = imageControls.getGrid().getData2d()
                .get(imageControls.getXgrid()).get(imageControls.getYgrid());
        getDataPoint().uv = new Point3D(imageControls.getGridUv().getData2d().get(
                imageControls.getXgrid()).get(imageControls.getYgrid()));
        getDataPoint().i = imageControls.getXgrid();
        getDataPoint().j = imageControls.getYgrid();

    }

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
        getTextFieldI().setText(""+Double.parseDouble(""+e.getOldValue()));
        saveDataPoint();
        getTextFieldI().setText(""+Double.parseDouble(""+e.getNewValue()));
        loadDataPoint();
    }
    private void textFieldJPropertyChange(PropertyChangeEvent e) {
        getTextFieldJ().setText(""+Double.parseDouble(""+e.getOldValue()));
        saveDataPoint();
        getTextFieldJ().setText(""+Double.parseDouble(""+e.getNewValue()));
        loadDataPoint();
    }

    private void saveDataPoint() {
        if(dataPoint!=null) {
            /*
            imageControls.getGrid().setElem(dataPoint.point, dataPoint.i, dataPoint.j);
            imageControls.getGridUv().setElem(dataPoint.uv, dataPoint.i, dataPoint.j);
        */
            imageControls.getGrid().getData2d().get(dataPoint.i).set(dataPoint.j, dataPoint.point);
            imageControls.getGridUv().getData2d().get(dataPoint.i).set(dataPoint.j, dataPoint.uv);
            try {
                if(checkBoxUv.isSelected()) {
                    imageControls.getGridUv().getData2d().get(dataPoint.i)
                            .set(dataPoint.j,
                            ((Point3D) dataPoint.point.copy()).multDot(
                                    new Point3D((double)imageControls.getResX(),
                                            (double)imageControls.getResY(), 0d)));
                }
            } catch (CopyRepresentableError e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void textFieldUPropertyChange(PropertyChangeEvent e) {
        try {
            dataPoint.uv = new Point3D((double) Double.parseDouble(getTextFieldU().getText()),
                    dataPoint.uv.getY(), dataPoint.uv.getZ());
            saveDataPoint();
        } catch (NumberFormatException ex) {

        }
    }

    private void textFieldVPropertyChange(PropertyChangeEvent e) {
        try {
            dataPoint.uv = new Point3D(dataPoint.uv.getX(),
                    (double) Double.parseDouble(getTextFieldU().getText()), dataPoint.uv.getZ());
            saveDataPoint();
        } catch (NumberFormatException ex) {

        }

    }

    private void textFieldXPropertyChange(PropertyChangeEvent e) {
        try {
            dataPoint.point = new Point3D((double) Double.parseDouble(getTextFieldX().getText()),
                    dataPoint.point.getY(), dataPoint.point.getZ());
            saveDataPoint();
        } catch (NumberFormatException ex) {

        }

    }

    private void textFieldYPropertyChange(PropertyChangeEvent e) {
        try {
            dataPoint.point = new Point3D( dataPoint.point.getX(),
                    (double) Double.parseDouble(getTextFieldY().getText()), dataPoint.point.getZ());
            saveDataPoint();
        } catch (NumberFormatException ex) {

        }

    }

    private void comboBoxAction(ActionEvent e) {
        ImageControls imageControls1 = imageControls.getMorphUI().getImageControls1();
        ImageControls imageControls2 = imageControls.getMorphUI().getImageControls2();
        ImageControls[] imageControlsArr = new ImageControls[]{
                imageControls1,
                imageControls2};
        switch (((JComboBox)(e.getSource())).getSelectedIndex()) {
            //Update
            case 0:
                saveDataPoint();
                break;
//            Delete row
            case 1:
                for(ImageControls imageControls : imageControlsArr) {
                    imageControls.getGrid().delete(
                            imageControls.getYgrid(), StructureMatrix.INSERT_ROW);
                    imageControls.getGridUv().delete(
                            imageControls.getYgrid(), StructureMatrix.INSERT_ROW);
                    imageControls.setYgrid(imageControls.getYgrid() - 1);
                }
                break;
//            Delete column
            case 2:
                for(ImageControls imageControls : imageControlsArr) {
                    imageControls.getGrid().delete(
                            imageControls.getXgrid(), StructureMatrix.INSERT_COL);
                    imageControls.getGridUv().delete(
                            imageControls.getXgrid(), StructureMatrix.INSERT_COL);
                    imageControls.setXgrid(imageControls.getXgrid() - 1);
                }
                break;
//            Insert row before
            case 3:
                for(ImageControls imageControls : imageControlsArr) {
                    imageControls.getGrid().insert(
                            imageControls.getYgrid(), StructureMatrix.INSERT_ROW, Point3D.O0);
                    imageControls.getGridUv().insert(
                            imageControls.getYgrid(), StructureMatrix.INSERT_ROW, Point3D.O0);
                }
                break;
//            Insert column before
            case 4:
                for(ImageControls imageControls : imageControlsArr) {
                    imageControls.getGrid().insert(
                            imageControls.getXgrid(), StructureMatrix.INSERT_COL, Point3D.O0);
                    imageControls.getGridUv().insert(
                            imageControls.getXgrid(), StructureMatrix.INSERT_COL, Point3D.O0);
                }
                break;
//            Insert row after
            case 5:
                for(ImageControls imageControls : imageControlsArr) {
                    imageControls.getGrid().insert(
                            imageControls.getYgrid(), StructureMatrix.INSERT_ROW, Point3D.O0);
                    imageControls.getGridUv().insert(
                            imageControls.getYgrid(), StructureMatrix.INSERT_ROW, Point3D.O0);
                }
                break;
//            Insert column after
            case 6:
                for(ImageControls imageControls : imageControlsArr) {
                    imageControls.getGrid().insert(
                            imageControls.getXgrid(), StructureMatrix.INSERT_COL, Point3D.O0);
                    imageControls.getGridUv().insert(
                            imageControls.getXgrid(), StructureMatrix.INSERT_COL, Point3D.O0);
                }
                break;
        }
        loadDataPoint();
    }

    public JCheckBox getCheckBoxUv() {
        return checkBoxUv;
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
        checkBoxUv = new JCheckBox();

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
        comboBox1.addActionListener(e -> comboBoxAction(e));
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

        //---- checkBoxUv ----
        checkBoxUv.setText("Text (u,v)");
        add(checkBoxUv, "cell 7 1");
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
    private JCheckBox checkBoxUv;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}