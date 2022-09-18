package one.empty3.neuralnetwork.of;

import atlasgen.Action;
import atlasgen.CsvLine;
import atlasgen.CsvReader;
import atlasgen.CsvWriter;
import one.empty3.neuralnetwork.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class NeuronsUI1 {
    private static JRootPane rootPane;
    private static JFrame frame;
    private String csv;
    private JButton parcourirButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JList rectangles;
    private JList labels;
    private JButton saveAsButton;
    private JButton reloadButton;
    private JButton saveButton;
    private JPanel imagePanel;
    private JButton suivantButton;
    private JButton precedentButton;
    private JList listImages;
    private int startIndex = 0;
    private int pageSize = 20;
    CsvLine csvLineSelected;

    private List<String[]> csvValues = new ArrayList<>();
    private CsvReader reader;

    public NeuronsUI1() {
        parcourirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(new File(csv));
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.showDialog(frame, "Select");
                if (chooser.getSelectedFile() != null) {
                    csv = chooser.getCurrentDirectory().getAbsolutePath();
                    populate();
                }

            }
        });
        listImages.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ListModel objects = listImages.getModel();
                Object elementAt = objects.getElementAt(listImages.getSelectedIndex());
                String idStr = (String) elementAt;
                reader = new CsvReader(new File(csv), "\t", "\n", false);
                reader.setAction(new Action() {
                    String id = idStr;

                    @Override
                    public void init() {

                    }

                    @Override
                    public void processLine(CsvLine csvLine) {
                        if (csvLine.getValue()[0].equals(id)) {
                            csvLineSelected = csvLine;
                        }
                    }
                });

            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CsvWriter writer = new CsvWriter("\n", "\t");
                writer.openFile(new File(csv));
            }
        });
    }

    private void populate() {
        Vector listData = new Vector();
        reader = new CsvReader(new File(csv), "\t", "\n", false);
        reader.setAction(new Action() {
            int i = 0;

            @Override
            public void init() {

            }

            @Override
            public void processLine(CsvLine csvLine) {
                i++;
                if (i >= startIndex && i < startIndex + pageSize) {
                    String s = csvLine.getValue()[0];
                    listData.add(s);
                }
            }
        });
        listImages.setListData(listData);
    }

    public static void main(String[] args) {


        frame = new JFrame("Test");
        rootPane = new Main().getRootPane();
        frame.setContentPane(rootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();


    }

}
