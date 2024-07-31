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

import net.miginfocom.swing.MigLayout;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.Config;
import one.empty3.library.Point3D;
import one.empty3.library.Resolution;
import one.empty3.library.objloader.E3Model;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author manue
 */
public class JFrameEditPolygonsMappings extends JFrame {

    File lastDirectory;
    private final Config config;
    Thread threadDisplay;
    private int mode = 0;
    private int SELECT_POINT = 1;

    public JFrameEditPolygonsMappings() {
        initComponents();

        editPolygonsMappings2 = new EditPolygonsMappings(this);

        config = new Config();
        File fileDirectoryDefault = config.getDefaultFileOutput();
        if (fileDirectoryDefault == null)
            config.setDefaultFileOutput(new File("."));
        String lastDirectoryTmpStr = config.getMap().get("D3ModelFaceTexturing");
        if (lastDirectoryTmpStr == null) {
            lastDirectoryTmpStr = ".";
            config.getMap().put("D3ModelFaceTexturing", lastDirectoryTmpStr);
        }
        config.save();
        lastDirectory = new File(lastDirectoryTmpStr);
        if (!lastDirectory.exists())
            config.save();
        //editPolygonsMappings2.testHumanHeadTexturing.setGenerate(editPolygonsMappings2.testHumanHeadTexturing);
        setContentPane(editPolygonsMappings2);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        threadDisplay = new Thread(editPolygonsMappings2);
        threadDisplay.start();
    }

    private void menuItemLoadImage(ActionEvent e) {
        JFileChooser loadImage = new JFileChooser();
        if (lastDirectory != null)
            loadImage.setCurrentDirectory(lastDirectory);
        if (loadImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            editPolygonsMappings2.loadImage(loadImage.getSelectedFile());
        }
        lastDirectory = loadImage.getCurrentDirectory();
    }

    private void menuItemAdd3DModel(ActionEvent e) {
        JFileChooser add3DModel = new JFileChooser();
        if (lastDirectory != null)
            add3DModel.setCurrentDirectory(lastDirectory);
        if (add3DModel.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            editPolygonsMappings2.add3DModel(add3DModel.getSelectedFile());
        lastDirectory = add3DModel.getCurrentDirectory();
    }

    private void menuItemLoadTxt(ActionEvent e) {
        JFileChooser loadImagePoints = new JFileChooser();
        if (lastDirectory != null)
            loadImagePoints.setCurrentDirectory(lastDirectory);
        if (loadImagePoints.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            editPolygonsMappings2.loadTxt(loadImagePoints.getSelectedFile());
        lastDirectory = loadImagePoints.getCurrentDirectory();
    }

    private void menuItemEditPointPosition(ActionEvent e) {
        editPolygonsMappings2.editPointPosition();
    }

    private void thisWindowClosing(WindowEvent e) {
        config.getMap().put("D3ModelFaceTexturing", lastDirectory.getAbsolutePath());
        config.save();
        try {
            editPolygonsMappings2.testHumanHeadTexturing.setMaxFrames(0);
            editPolygonsMappings2.isRunning = false;
            editPolygonsMappings2.testHumanHeadTexturing.stop();
            Thread.sleep(1000);
        } catch (InterruptedException | RuntimeException ex) {
            ex.printStackTrace();
        }
        super.dispose();
        System.exit(0);
    }

    private void menuItemSelectPoint(ActionEvent e) {
        mode = SELECT_POINT;
        editPolygonsMappings2.selectPointPosition();
    }

    private void panelModelViewMouseClicked(ActionEvent e) {
    }

    private void menuItemSaveModifiedVertex(ActionEvent e) {
        JFileChooser saveImageDeformed = new JFileChooser();
        if (lastDirectory != null)
            saveImageDeformed.setCurrentDirectory(lastDirectory);
        if (saveImageDeformed.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            editPolygonsMappings2.saveTxtOut(saveImageDeformed.getSelectedFile());
        }
        lastDirectory = saveImageDeformed.getCurrentDirectory();
    }

    private void menuItemLoadModifiedVertex(ActionEvent e) {
        JFileChooser loadImageDeformed = new JFileChooser();
        if (lastDirectory != null)
            loadImageDeformed.setCurrentDirectory(lastDirectory);
        if (loadImageDeformed.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            editPolygonsMappings2.loadTxtOut(loadImageDeformed.getSelectedFile());
        }
        lastDirectory = loadImageDeformed.getCurrentDirectory();
    }

    public class SaveTexture {
        private final one.empty3.library.core.testing.Resolution resolution;
        private final E3Model model;
        private final BufferedImage image;

        public SaveTexture(@NotNull one.empty3.library.core.testing.Resolution resolution, @NotNull BufferedImage image, @NotNull E3Model model) {
            this.resolution = resolution;
            this.model = model;
            this.image = image;

        }

        public BufferedImage computeTexture() {
            TextureMorphMove iTextureMorphMoveImage = new TextureMorphMove();
            iTextureMorphMoveImage.setEditOPanel(editPolygonsMappings2);
            iTextureMorphMoveImage.image = image;
            iTextureMorphMoveImage.distanceAB = new DistanceProxLinear1(iTextureMorphMoveImage.pointsInImage.values().stream().toList(),
                    iTextureMorphMoveImage.pointsInModel.values().stream().toList(),
                    new Dimension(iTextureMorphMoveImage.image.getWidth(), iTextureMorphMoveImage.image.getHeight()),
                    new Dimension(resolution.x(), resolution.y())
            );
            BufferedImage imageOut = new BufferedImage(resolution.x(), resolution.y(), BufferedImage.TYPE_INT_ARGB);
            model.texture(iTextureMorphMoveImage);
            for (double u = 0; u < 1.0; u += 1.0 / resolution.x()) {
                for (double v = 0; v < resolution.y(); v += 1.0 / resolution.y()) {
                    Point3D uvFace = new Point3D(u, v, 0.0);
                    Point3D uvFace1 = model.findUvFace(u, v);
                    Point3D axPointInB = iTextureMorphMoveImage.distanceAB.findAxPointInB(u, v);
                    int colorAt = iTextureMorphMoveImage.getColorAt(uvFace.getX(), uvFace.getY());
                    imageOut.setRGB((int) (u * iTextureMorphMoveImage.image.getWidth()), (int) (v * iTextureMorphMoveImage.image.getHeight()), colorAt);
                }
                Logger.getAnonymousLogger().log(Level.INFO, String.format("Image column #" + ((int) (u * 100)) + "% : done"));
            }
            return imageOut;
        }
    }

    private void menuItemHD(ActionEvent e) {
        Runnable jpg = () -> {
            while (editPolygonsMappings2.iTextureMorphMoveImage.image == null || editPolygonsMappings2.iTextureMorphMoveImage.pointsInImage == null || editPolygonsMappings2.iTextureMorphMoveImage.pointsInModel == null
                    || editPolygonsMappings2.model == null) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            TextureMorphMove textureMorphMoveImage = new TextureMorphMove();
            textureMorphMoveImage.setEditOPanel(editPolygonsMappings2);
            textureMorphMoveImage.distanceAB = new DistanceProxLinear1(editPolygonsMappings2.iTextureMorphMoveImage.pointsInImage.values().stream().toList(),
                    editPolygonsMappings2.iTextureMorphMoveImage.pointsInModel.values().stream().toList(),
                    new Dimension(editPolygonsMappings2.iTextureMorphMoveImage.image.getWidth(),
                            editPolygonsMappings2.iTextureMorphMoveImage.image.getHeight()), new Dimension(Resolution.HD1080RESOLUTION.x(), Resolution.HD1080RESOLUTION.y())
            );
            E3Model model = editPolygonsMappings2.model;
            File defaultFileOutput = config.getDefaultFileOutput();
            SaveTexture saveTexture = new SaveTexture(Resolution.HD1080RESOLUTION, editPolygonsMappings2.iTextureMorphMoveImage.image, model);
            BufferedImage bufferedImage = saveTexture.computeTexture();
            ImageIO.write(bufferedImage, "jpg", new File(config.getMap().get("D3ModelFaceTexturing") + UUID.randomUUID() + ".jpg"));
            Logger.getAnonymousLogger().log(Level.INFO, "Smart generated HD image");
        };
        Thread thread = new Thread(jpg);
        thread.start();
    }

    private void menuItem4K(ActionEvent e) {
        // TODO add your code here
    }

    private void menuBar1FocusGained(FocusEvent e) {
        editPolygonsMappings2.notMenuOpen = false;
    }

    private void menuBar1FocusLost(FocusEvent e) {
        editPolygonsMappings2.notMenuOpen = true;
    }

    private void checkBoxMenuItem2(ActionEvent e) {
        if (e.getSource() instanceof JCheckBoxMenuItem r) {
            if (r.isSelected()) {
                editPolygonsMappings2.iTextureMorphMoveImage.distanceAB.typeShape = 0;
                editPolygonsMappings2.typeShape = 0;
            } else {
                editPolygonsMappings2.iTextureMorphMoveImage.distanceAB.typeShape = 1;
                editPolygonsMappings2.typeShape = 0;
            }
        }
        editPolygonsMappings2.hasChangedAorB = true;
    }

    private void checkBoxMenuItem1(ActionEvent e) {
        if (e.getSource() instanceof JCheckBoxMenuItem r) {
            if (r.isSelected()) {
                editPolygonsMappings2.iTextureMorphMoveImage.distanceAB.opt1 = true;
                editPolygonsMappings2.opt1 = true;
            } else {
                editPolygonsMappings2.iTextureMorphMoveImage.distanceAB.opt1 = true;
                editPolygonsMappings2.opt1 = true;
            }
        }
        editPolygonsMappings2.hasChangedAorB = true;
    }

    private void menuItemClassBezier2(ActionEvent e) {
        editPolygonsMappings2.iTextureMorphMoveImage.setDistanceABclass(DistanceBezier2.class);
        editPolygonsMappings2.distanceABClass = DistanceBezier2.class;
        editPolygonsMappings2.hasChangedAorB = true;
    }

    private void menuItem1DistanceBB(ActionEvent e) {
        editPolygonsMappings2.iTextureMorphMoveImage.setDistanceABclass(DistanceBB.class);
        editPolygonsMappings2.distanceABClass = DistanceBB.class;
        editPolygonsMappings2.hasChangedAorB = true;
    }

    private void menuItemLinearProx1(ActionEvent e) {
        editPolygonsMappings2.iTextureMorphMoveImage.setDistanceABclass(DistanceProxLinear1.class);
        editPolygonsMappings2.distanceABClass = DistanceProxLinear1.class;
        editPolygonsMappings2.hasChangedAorB = true;
    }

    private void menuItemLinearProx2(ActionEvent e) {
        editPolygonsMappings2.iTextureMorphMoveImage.setDistanceABclass(DistanceProxLinear2.class);
        editPolygonsMappings2.distanceABClass = DistanceProxLinear2.class;
        editPolygonsMappings2.hasChangedAorB = true;
    }

    private void menuItemLinearProx3(ActionEvent e) {
        editPolygonsMappings2.iTextureMorphMoveImage.setDistanceABclass(DistanceProxLinear3.class);
        editPolygonsMappings2.distanceABClass = DistanceProxLinear3.class;
        editPolygonsMappings2.hasChangedAorB = true;
    }

    private void optimizeGrid(ActionEvent e) {
        editPolygonsMappings2.iTextureMorphMoveImage.distanceAB.optimizeGrid = ((JCheckBoxMenuItem) (e.getSource())).getSelectedObjects() != null;
        editPolygonsMappings2.optimizeGrid = ((JCheckBoxMenuItem) (e.getSource())).getSelectedObjects() != null;
        editPolygonsMappings2.hasChangedAorB = true;
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.library.core.testing.Bundle");
        menuBar1 = new JMenuBar();
        menu2 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem8 = new JMenuItem();
        menuItem9 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menu1 = new JMenu();
        menuItemMoveLinesUp = new JMenuItem();
        menuItemMoveLinesDown = new JMenuItem();
        menuItemMoveLinesLeft = new JMenuItem();
        menuItemMoveColumnsRight = new JMenuItem();
        menuItemMoveRectangle = new JMenuItem();
        menuItem5 = new JMenuItem();
        menuItem6 = new JMenuItem();
        menuItem7 = new JMenuItem();
        menu4 = new JMenu();
        menuItem10 = new JMenuItem();
        menuItem11 = new JMenuItem();
        menu5 = new JMenu();
        checkBoxMenuItem2 = new JCheckBoxMenuItem();
        menuItem13 = new JCheckBoxMenuItem();
        checkBoxMenuItem1 = new JCheckBoxMenuItem();
        checkBoxMenuItemOptimizeGrid = new JCheckBoxMenuItem();
        menuItemDistanceBB = new JMenuItem();
        menuItemClassBezier2 = new JRadioButtonMenuItem();
        menuItemLinearProx1 = new JRadioButtonMenuItem();
        menuItemLinearProx2 = new JRadioButtonMenuItem();
        menuItemLinearProx3 = new JRadioButtonMenuItem();
        editPolygonsMappings2 = new EditPolygonsMappings();
        menu3 = new JMenu();

        //======== this ========
        setMinimumSize(new Dimension(830, 600));
        setTitle(bundle.getString("JFrameEditPolygonsMappings.this.title"));
        setMaximizedBounds(null);
        setIconImage(new ImageIcon("D:\\Current\\empty3-library-generic\\mite.png").getImage());
        setFocusTraversalPolicyProvider(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,novisualpadding,hidemode 3",
            // columns
            "[fill]",
            // rows
            "[]"));

        //======== menuBar1 ========
        {
            menuBar1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    menuBar1FocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    menuBar1FocusLost(e);
                }
            });

            //======== menu2 ========
            {
                menu2.setText(bundle.getString("JFrameEditPolygonsMappings.menu2.text"));

                //---- menuItem1 ----
                menuItem1.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem1.text"));
                menuItem1.addActionListener(e -> menuItemLoadImage(e));
                menu2.add(menuItem1);

                //---- menuItem3 ----
                menuItem3.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem3.text"));
                menuItem3.addActionListener(e -> menuItemLoadTxt(e));
                menu2.add(menuItem3);

                //---- menuItem8 ----
                menuItem8.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem8.text"));
                menuItem8.addActionListener(e -> menuItemSaveModifiedVertex(e));
                menu2.add(menuItem8);

                //---- menuItem9 ----
                menuItem9.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem9.text"));
                menuItem9.addActionListener(e -> menuItemLoadModifiedVertex(e));
                menu2.add(menuItem9);

                //---- menuItem2 ----
                menuItem2.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem2.text"));
                menu2.add(menuItem2);

                //---- menuItem4 ----
                menuItem4.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem4.text"));
                menuItem4.addActionListener(e -> menuItemAdd3DModel(e));
                menu2.add(menuItem4);
            }
            menuBar1.add(menu2);

            //======== menu1 ========
            {
                menu1.setText(bundle.getString("JFrameEditPolygonsMappings.menu1.text"));

                //---- menuItemMoveLinesUp ----
                menuItemMoveLinesUp.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveLinesUp.text"));
                menu1.add(menuItemMoveLinesUp);

                //---- menuItemMoveLinesDown ----
                menuItemMoveLinesDown.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveLinesDown.text"));
                menu1.add(menuItemMoveLinesDown);

                //---- menuItemMoveLinesLeft ----
                menuItemMoveLinesLeft.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveLinesLeft.text"));
                menu1.add(menuItemMoveLinesLeft);

                //---- menuItemMoveColumnsRight ----
                menuItemMoveColumnsRight.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveColumnsRight.text"));
                menu1.add(menuItemMoveColumnsRight);

                //---- menuItemMoveRectangle ----
                menuItemMoveRectangle.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemMoveRectangle.text"));
                menu1.add(menuItemMoveRectangle);

                //---- menuItem5 ----
                menuItem5.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem5.text"));
                menu1.add(menuItem5);

                //---- menuItem6 ----
                menuItem6.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem6.text"));
                menuItem6.setFocusable(true);
                menuItem6.setFocusCycleRoot(true);
                menuItem6.setFocusPainted(true);
                menuItem6.setFocusTraversalPolicyProvider(true);
                menuItem6.addActionListener(e -> {
			menuItemSelectPoint(e);
			panelModelViewMouseClicked(e);
		});
                menu1.add(menuItem6);

                //---- menuItem7 ----
                menuItem7.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem7.text"));
                menu1.add(menuItem7);
            }
            menuBar1.add(menu1);

            //======== menu4 ========
            {
                menu4.setText(bundle.getString("JFrameEditPolygonsMappings.menu4.text"));

                //---- menuItem10 ----
                menuItem10.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem10.text"));
                menuItem10.addActionListener(e -> menuItemHD(e));
                menu4.add(menuItem10);

                //---- menuItem11 ----
                menuItem11.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem11.text"));
                menuItem11.addActionListener(e -> menuItem4K(e));
                menu4.add(menuItem11);
            }
            menuBar1.add(menu4);

            //======== menu5 ========
            {
                menu5.setText(bundle.getString("JFrameEditPolygonsMappings.menu5.text"));

                //---- checkBoxMenuItem2 ----
                checkBoxMenuItem2.setText(bundle.getString("JFrameEditPolygonsMappings.checkBoxMenuItem2.text"));
                checkBoxMenuItem2.setSelected(true);
                checkBoxMenuItem2.addActionListener(e -> checkBoxMenuItem2(e));
                menu5.add(checkBoxMenuItem2);

                //---- menuItem13 ----
                menuItem13.setText(bundle.getString("JFrameEditPolygonsMappings.menuItem13.text"));
                menuItem13.setSelected(true);
                menu5.add(menuItem13);

                //---- checkBoxMenuItem1 ----
                checkBoxMenuItem1.setText(bundle.getString("JFrameEditPolygonsMappings.checkBoxMenuItem1.text"));
                checkBoxMenuItem1.addActionListener(e -> {
			checkBoxMenuItem1(e);
			checkBoxMenuItem1(e);
			checkBoxMenuItem1(e);
		});
                menu5.add(checkBoxMenuItem1);

                //---- checkBoxMenuItemOptimizeGrid ----
                checkBoxMenuItemOptimizeGrid.setText(bundle.getString("JFrameEditPolygonsMappings.checkBoxMenuItemOptimizeGrid.text"));
                checkBoxMenuItemOptimizeGrid.addActionListener(e -> optimizeGrid(e));
                menu5.add(checkBoxMenuItemOptimizeGrid);

                //---- menuItemDistanceBB ----
                menuItemDistanceBB.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemDistanceBB.text"));
                menuItemDistanceBB.addActionListener(e -> menuItem1DistanceBB(e));
                menu5.add(menuItemDistanceBB);

                //---- menuItemClassBezier2 ----
                menuItemClassBezier2.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemClassBezier2.text"));
                menuItemClassBezier2.addActionListener(e -> menuItemClassBezier2(e));
                menu5.add(menuItemClassBezier2);

                //---- menuItemLinearProx1 ----
                menuItemLinearProx1.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemLinearProx1.text"));
                menuItemLinearProx1.addActionListener(e -> menuItemLinearProx1(e));
                menu5.add(menuItemLinearProx1);

                //---- menuItemLinearProx2 ----
                menuItemLinearProx2.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemLinearProx2.text"));
                menuItemLinearProx2.addActionListener(e -> menuItemLinearProx2(e));
                menu5.add(menuItemLinearProx2);

                //---- menuItemLinearProx3 ----
                menuItemLinearProx3.setText(bundle.getString("JFrameEditPolygonsMappings.menuItemLinearProx3.text"));
                menuItemLinearProx3.addActionListener(e -> menuItemLinearProx3(e));
                menu5.add(menuItemLinearProx3);
            }
            menuBar1.add(menu5);
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
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(checkBoxMenuItem2);
        buttonGroup1.add(menuItem13);

        //---- buttonGroup2 ----
        var buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(menuItemClassBezier2);
        buttonGroup2.add(menuItemLinearProx1);
        buttonGroup2.add(menuItemLinearProx2);
        buttonGroup2.add(menuItemLinearProx3);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenu menu2;
    private JMenuItem menuItem1;
    private JMenuItem menuItem3;
    private JMenuItem menuItem8;
    private JMenuItem menuItem9;
    private JMenuItem menuItem2;
    private JMenuItem menuItem4;
    private JMenu menu1;
    private JMenuItem menuItemMoveLinesUp;
    private JMenuItem menuItemMoveLinesDown;
    private JMenuItem menuItemMoveLinesLeft;
    private JMenuItem menuItemMoveColumnsRight;
    private JMenuItem menuItemMoveRectangle;
    private JMenuItem menuItem5;
    private JMenuItem menuItem6;
    private JMenuItem menuItem7;
    private JMenu menu4;
    private JMenuItem menuItem10;
    private JMenuItem menuItem11;
    private JMenu menu5;
    private JCheckBoxMenuItem checkBoxMenuItem2;
    private JCheckBoxMenuItem menuItem13;
    private JCheckBoxMenuItem checkBoxMenuItem1;
    private JCheckBoxMenuItem checkBoxMenuItemOptimizeGrid;
    private JMenuItem menuItemDistanceBB;
    private JRadioButtonMenuItem menuItemClassBezier2;
    private JRadioButtonMenuItem menuItemLinearProx1;
    private JRadioButtonMenuItem menuItemLinearProx2;
    private JRadioButtonMenuItem menuItemLinearProx3;
    private EditPolygonsMappings editPolygonsMappings2;
    private JMenu menu3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public static void main(String[] args) {
        JFrameEditPolygonsMappings jFrameEditPolygonsMappings = new JFrameEditPolygonsMappings();
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(0);
    }
}
