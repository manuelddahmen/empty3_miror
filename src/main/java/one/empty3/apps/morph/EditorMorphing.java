package one.empty3.apps.morph;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class EditorMorphing extends JFrame{
    private double dimXnew = 20;
    private double dimYnew = 20;

    class Model {
        private File fileA;
        private File fileB;
        private Point3D[][] points3D;
        private int dimX;
        private int dimY;

        public Point3D[][] getPoints3D() {
            return points3D;
        }
        public void coverLine(int j, int dimX, Point3D [][] points3d_new) {
            for (int i = 0; i < this.dimX || (i < dimX && dimX < this.dimX); i++) {
                points3d_new[j][i] = myModel.getPoints3D()[j][i];
            }
            if (dimX > this.dimX) {
                for (int i = this.dimX; i < dimX; i++) {
                    points3d_new[j] = myModel.getPoints3D()[j];
                }
            }
        }

        public void setDimXY(int dimX, int dimY) {
            if(dimX!=this.dimX || dimY!=this.dimY) {
                Point3D[][] points3d_tmp = new Point3D[dimX][dimY];
                for(int j=0; j<this.dimY||(j<dimY && dimY<this.dimY); j++) {
                    points3d_tmp[j] = points3d_tmp[j];
                    coverLine(j, dimX, points3d_tmp);


                }
                if(dimY>this.dimY) {
                    for(int j=this.dimY; j<dimY; j++) {
                        points3d_tmp[j] = points3D[points3d_tmp.length-1];
                        coverLine(j, dimX, points3d_tmp);
                    }
                }
                myModel.points3D = points3d_tmp;
            }
        }

    }
    private Model myModel = new Model();
    private static String directory;
    private JButton goButton;
    private JFormattedTextField dimX;
    private JFormattedTextField dimY;
    private JButton addAtPointXButton;
    private JButton addAtPointYButton;
    private JButton deleteAtPointXButton;
    private JButton deleteAtPointYButton;
    private JPanel inputLeft;
    private JPanel inputRight;
    private JPanel preview;
    private JButton renderButton;
    private JTextField textField1;
    private JFormattedTextField a250FormattedTextField;
    private JButton parcourirA;
    private JButton parcourirB;
    private JButton saveButton;
    private JFrame jframe = this;


    public EditorMorphing() {
        parcourirA.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(jframe);
                fileDialog.setDirectory(EditorMorphing.directory==null?".":EditorMorphing.directory);
                fileDialog.setVisible(true);
                String file = fileDialog.getFile();
                if(file!=null);
                try {
                    ImageIO.read(new File(file));
                    myModel.fileA = new File(file);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        parcourirB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(jframe);
                fileDialog.setDirectory(EditorMorphing.directory==null?".":EditorMorphing.directory);
                fileDialog.setVisible(true);
                String file = fileDialog.getFile();
                if(file!=null);
                try {
                    ImageIO.read(new File(file));
                    myModel.fileB = new File(file);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                {
                    if(dimX.getText()!=null) {
                        try {
                            dimXnew = Double.parseDouble(dimX.getText());
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    if(dimY.getText()!=null) {
                        try {
                            dimYnew = Double.parseDouble(dimY.getText());
                         } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }

                    myModel.setDimXY((int)dimXnew, (int)dimYnew);
                }


            }
        });
    }

    public static void main(String[] args) {
        EditorMorphing editorMorphing = new EditorMorphing();

    }
}
