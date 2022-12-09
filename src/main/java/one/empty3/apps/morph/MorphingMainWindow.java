package one.empty3.apps.morph;

import javax.swing.*;

public class MorphingMainWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel panel1;
    private JToolBar toolBar1;
    private JButton goButton;
    private JFormattedTextField dimX;
    private JSplitPane splitPane1;
    private JPanel inputLeft;
    private JButton parcourirA;
    private JPanel panel2;
    private JPanel inputRight;
    private JButton parcourirB;
    private JPanel preview;
    private JTextField textField1;
    private JFormattedTextField dimY;
    private JButton addAtPointXButton;
    private JButton addAtPointYButton;
    private JButton deleteAtPointXButton;
    private JButton deleteAtPointYButton;
    private JButton renderButton;
    private JLabel intermediatesLabel;
    private JFormattedTextField a250FormattedTextField;
    private JButton saveButton;
    private JPanel panel3;

    public MorphingMainWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        MorphingMainWindow dialog = new MorphingMainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
