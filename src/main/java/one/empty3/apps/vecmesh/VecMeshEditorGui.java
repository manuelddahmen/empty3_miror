/*
 * Created by JFormDesigner on Sat Dec 16 16:30:23 CET 2023
 */

package one.empty3.apps.vecmesh;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.FileChooserUI;

import net.miginfocom.swing.*;

/**
 * @author Manuel Dahmen
 * 18-12-2023
 */
public class VecMeshEditorGui extends JFrame {
    private File currentFile;

    public VecMeshEditorGui() {
        initComponents();

        setDefaultFile();
    }

    private void menuItemSave(ActionEvent e) {
        String text = getTextAreaCode().getText();
        int columns = getTextFieldRows();
        if(currentFile!=null&&currentFile.exists()) {
            try {
                PrintWriter fileOutputStream = new PrintWriter(currentFile);
                fileOutputStream.println(columns);
                fileOutputStream.println(text);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void setDefaultFile() {
        currentFile= new File("defaultCode.calcmath");
        getDefaultOrNewFileTextCode();
    }
    private String getDefaultOrNewFileTextCode() {
        if(!currentFile.exists()) {
            try {
                currentFile.createNewFile();
                PrintWriter printWriter = new PrintWriter(currentFile);
                printWriter.println("2");
                printWriter.println("((1,1),(1,1))");
                printWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            try (BufferedReader reader = Files.newBufferedReader(currentFile.toPath(), Charset.defaultCharset())){
                String line = null;
                String columnsString = reader.readLine();
                if(columnsString!=null)
                    textAreaColumsCount.setText(String.valueOf(Double.parseDouble(columnsString)));
                StringBuffer sb = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                getTextAreaCode().setText(sb.toString());
            }
        } catch (IOException|RuntimeException e) {
            e.printStackTrace();
        }
        return "null. cannot read or create file : "+currentFile.getAbsolutePath();
    }

    public String getDefaultCode() {
        return getTextAreaCode().getText();
    }

    private void menuItemSaveAs(ActionEvent e) {
        JFileChooser ui = new JFileChooser();
        ui.setDialogType(JFileChooser.SAVE_DIALOG);
        ui.setDialogTitle("Save as text");
        if (ui.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = ui.getSelectedFile();
            String text = getTextAreaCode().getText();

            int columns = getTextFieldRows();

            try {
                PrintWriter fileOutputStream = new PrintWriter(selectedFile);
                fileOutputStream.println(columns);
                fileOutputStream.println(text);

                fileOutputStream.close();

                currentFile = selectedFile;

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);

            }

        }
    }

    private void menuItemOpen(ActionEvent e) {
        JFileChooser ui = new JFileChooser();
        ui.setDialogType(JFileChooser.OPEN_DIALOG);
        ui.setDialogTitle("Load text");
        if (ui.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = ui.getSelectedFile();
            String text = getTextAreaCode().getText();

            int columns = getTextFieldRows();

            try (BufferedReader reader = Files.newBufferedReader(currentFile.toPath(), Charset.defaultCharset())){
                String line = "";
                String columnsString = reader.readLine();
                if(columnsString!=null)
                    textAreaColumsCount.setText(String.valueOf(Double.parseDouble(columnsString)));
                StringBuffer sb = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                getTextAreaCode().setText(sb.toString());
                reader.close();
            } catch (RuntimeException | IOException ex) {
                ex.printStackTrace();
            }

            currentFile = selectedFile;

        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - manuel dahmen
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.library.core.testing.Bundle");
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem6 = new JMenuItem();
        menu2 = new JMenu();
        menuItemRender = new JMenuItem();
        menuItem5 = new JMenuItem();
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        splitPane1 = new JSplitPane();
        scrollPane1 = new JScrollPane();
        textAreaCode = new JTextArea();
        panelGraphics = new JPanel();
        buttonBar = new JPanel();
        scrollPane2 = new JScrollPane();
        textAreaColumsCount = new JTextArea();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText(bundle.getString("VecMeshEditorGui.menu1.text"));

                //---- menuItem1 ----
                menuItem1.setText(bundle.getString("VecMeshEditorGui.menuItem1.text"));
                menuItem1.addActionListener(e -> menuItemOpen(e));
                menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText(bundle.getString("VecMeshEditorGui.menuItem2.text"));
                menuItem2.addActionListener(e -> menuItemSave(e));
                menu1.add(menuItem2);

                //---- menuItem3 ----
                menuItem3.setText(bundle.getString("VecMeshEditorGui.menuItem3.text"));
                menuItem3.addActionListener(e -> menuItemSaveAs(e));
                menu1.add(menuItem3);

                //---- menuItem6 ----
                menuItem6.setText(bundle.getString("VecMeshEditorGui.menuItem6.text"));
                menu1.add(menuItem6);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText(bundle.getString("VecMeshEditorGui.menu2.text"));

                //---- menuItemRender ----
                menuItemRender.setText(bundle.getString("VecMeshEditorGui.menuItemRender.text"));
                menu2.add(menuItemRender);

                //---- menuItem5 ----
                menuItem5.setText(bundle.getString("VecMeshEditorGui.menuItem5.text"));
                menu2.add(menuItem5);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);

        //======== dialogPane ========
        {
            dialogPane.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing.
            border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER
            ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font
            . BOLD ,12 ) ,java . awt. Color .red ) ,dialogPane. getBorder () ) ); dialogPane. addPropertyChangeListener(
            new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r"
            .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "fill,insets dialog,hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //======== splitPane1 ========
                {
                    splitPane1.setMinimumSize(new Dimension(640, 480));
                    splitPane1.setResizeWeight(0.5);

                    //======== scrollPane1 ========
                    {

                        //---- textAreaCode ----
                        textAreaCode.setMinimumSize(new Dimension(320, 480));
                        textAreaCode.setRows(20);
                        textAreaCode.setText(bundle.getString("VecMeshEditorGui.textAreaCode.text"));
                        scrollPane1.setViewportView(textAreaCode);
                    }
                    splitPane1.setLeftComponent(scrollPane1);

                    //======== panelGraphics ========
                    {
                        panelGraphics.setMinimumSize(new Dimension(640, 480));
                        panelGraphics.setLayout(new MigLayout(
                            "fill,hidemode 3",
                            // columns
                            "[fill]" +
                            "[fill]",
                            // rows
                            "[]" +
                            "[]" +
                            "[]"));
                    }
                    splitPane1.setRightComponent(panelGraphics);
                }
                contentPanel.add(splitPane1, "cell 0 0 3 4,dock center,wmin 800,hmin 600");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
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
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[button,fill]" +
                    "[button,fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]",
                    // rows
                    null));

                //======== scrollPane2 ========
                {

                    //---- textAreaColumsCount ----
                    textAreaColumsCount.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                    textAreaColumsCount.setText(bundle.getString("VecMeshEditorGui.textAreaColumsCount.text"));
                    scrollPane2.setViewportView(textAreaColumsCount);
                }
                buttonBar.add(scrollPane2, "cell 0 0 11 1");

                //---- okButton ----
                okButton.setText(bundle.getString("VecMeshEditorGui.okButton.text"));
                buttonBar.add(okButton, "cell 34 0");

                //---- cancelButton ----
                cancelButton.setText(bundle.getString("VecMeshEditorGui.cancelButton.text"));
                buttonBar.add(cancelButton, "cell 35 0");
            }
            dialogPane.add(buttonBar, BorderLayout.PAGE_END);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - manuel dahmen
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem6;
    private JMenu menu2;
    private JMenuItem menuItemRender;
    private JMenuItem menuItem5;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JSplitPane splitPane1;
    private JScrollPane scrollPane1;
    private JTextArea textAreaCode;
    private JPanel panelGraphics;
    private JPanel buttonBar;
    private JScrollPane scrollPane2;
    private JTextArea textAreaColumsCount;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public JTextArea getTextAreaCode() {
        return textAreaCode;
    }

    public int getTextFieldRows() {
        return (int)(Double.parseDouble(textAreaColumsCount.getText()));
    }

    public JPanel getPanelGraphics() {
        return panelGraphics;
    }
}
