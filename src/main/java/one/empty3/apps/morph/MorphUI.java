/*
 * Created by JFormDesigner on Sat Dec 10 12:00:49 CET 2022
 */

package one.empty3.apps.morph;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.IconView;

import net.miginfocom.swing.*;

/**
 * @author Manuel Dahmen
 */
public class MorphUI extends JFrame {
    private File currentDirectory;
    private File image1;
    private File image2;
    private BufferedImage imageRead1;
    private BufferedImage imageRead2;

    public MorphUI() {
//Ask for window decorations provided by the look and feel.
        JFrame.setDefaultLookAndFeelDecorated(true);

        initComponents();


    }

    private void buttonLoadImage1(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return "image/jpg";
            }
        });
        if(currentDirectory != null && currentDirectory.exists()) {
            jFileChooser.setCurrentDirectory(currentDirectory);
        }

        if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
            this.image1 = jFileChooser.getSelectedFile();
            try {
                URL url = new URL("file:///" + image1.getAbsolutePath());
                imageRead1 = ImageIO.read(url);

                Component add = panel1.add(new JLabel(url.toString(),
                        (Icon) new ImageIcon(imageRead1),
                        JLabel.CENTER));
                pack();

                currentDirectory = jFileChooser.getCurrentDirectory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }

    }

    private void buttonLoadImage2(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return "image/jpg";
            }
        });
        if(currentDirectory != null && currentDirectory.exists()) {
            jFileChooser.setCurrentDirectory(currentDirectory);
        }

        if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
            this.image2 = jFileChooser.getSelectedFile();
            try {
                URL url = new URL("file:///" + image1.getAbsolutePath());
                imageRead2 = ImageIO.read(url);

                Component add = panel2.add(new JLabel(url.toString(),
                        (Icon) new ImageIcon(imageRead2),
                        JLabel.CENTER));
                pack();

                currentDirectory = jFileChooser.getCurrentDirectory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        button4 = new JButton();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        label1 = new JLabel();
        textFieldSeconds = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        slider1 = new JSlider();
        panel4 = new JPanel();
        button5 = new JButton();
        button6 = new JButton();
        label4 = new JLabel();
        label5 = new JLabel();
        textField4 = new JTextField();
        textField5 = new JTextField();
        textField6 = new JTextField();
        textField7 = new JTextField();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,hidemode 3",
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

        //---- button4 ----
        button4.setText("GO");
        contentPane.add(button4, "cell 2 0");

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        contentPane.add(panel1, "cell 0 1");

        //======== panel2 ========
        {
            panel2.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        contentPane.add(panel2, "cell 1 1");

        //======== panel3 ========
        {
            panel3.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        contentPane.add(panel3, "cell 2 1");

        //---- label1 ----
        label1.setText("Seconds");
        contentPane.add(label1, "cell 3 1");

        //---- textFieldSeconds ----
        textFieldSeconds.setText("10");
        contentPane.add(textFieldSeconds, "cell 4 1");

        //---- label3 ----
        label3.setText("FPS");
        contentPane.add(label3, "cell 3 2");

        //---- textField3 ----
        textField3.setText("25");
        contentPane.add(textField3, "cell 4 2");

        //---- label2 ----
        label2.setText("text");
        contentPane.add(label2, "cell 3 3");
        contentPane.add(textField2, "cell 4 3");

        //---- button1 ----
        button1.setText("Load 1");
        button1.addActionListener(e -> buttonLoadImage1(e));
        contentPane.add(button1, "cell 0 4");

        //---- button2 ----
        button2.setText("Load 2");
        contentPane.add(button2, "cell 1 4");
        contentPane.add(slider1, "cell 2 4");

        //======== panel4 ========
        {
            panel4.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));

            //---- button5 ----
            button5.setText("Add col");
            panel4.add(button5, "cell 0 0");

            //---- button6 ----
            button6.setText("Del col");
            panel4.add(button6, "cell 1 0");

            //---- label4 ----
            label4.setText("Add line");
            panel4.add(label4, "cell 2 0");

            //---- label5 ----
            label5.setText("Del line");
            panel4.add(label5, "cell 3 0");

            //---- textField4 ----
            textField4.setText("1");
            panel4.add(textField4, "cell 0 1");

            //---- textField5 ----
            textField5.setText("1");
            panel4.add(textField5, "cell 1 1");

            //---- textField6 ----
            textField6.setText("1");
            panel4.add(textField6, "cell 2 1");

            //---- textField7 ----
            textField7.setText("1");
            panel4.add(textField7, "cell 3 1");
        }
        contentPane.add(panel4, "cell 2 5");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path,
                                        String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JButton button4;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel label1;
    private JTextField textFieldSeconds;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label2;
    private JTextField textField2;
    private JButton button1;
    private JButton button2;
    private JSlider slider1;
    private JPanel panel4;
    private JButton button5;
    private JButton button6;
    private JLabel label4;
    private JLabel label5;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
