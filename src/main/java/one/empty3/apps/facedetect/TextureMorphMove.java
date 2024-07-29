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

package one.empty3.apps.facedetect;

import javaAnd.awt.Color;
import one.empty3.library.CopyRepresentableError;
import one.empty3.library.ITexture;
import one.empty3.library.MatrixPropertiesObject;
import one.empty3.library.Point3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TextureMorphMove extends ITexture {
    private static final int WHITE = Color.WHITE.getRGB();
    private EditPolygonsMappings editPanel;
    public int selectedPointNo = -1;
    public HashMap<String, Point3D> pointsInModel = new HashMap<>();
    public HashMap<String, Point3D> pointsInImage = new HashMap<>();
    BufferedImage image;
    protected DistanceAB distanceAB;
    private int GRAY = Color.GRAY.getRGB();
    private Class<? extends DistanceBezier2> distanceABclass;

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }

    public TextureMorphMove() {
        super();
    }

    @Override
    public int getColorAt(double u, double v) {
        if (distanceAB != null && !distanceAB.isInvalidArray() && image != null) {
            try {
                Point3D axPointInB = distanceAB.findAxPointInB(u, v);
                Point3D point3D = new Point3D(axPointInB.getX() * image.getWidth(), axPointInB.getY() * image.getHeight(), 0.0);
                int rgb = image.getRGB((int) (Math.max(0, Math.min(point3D.getX(), (double) image.getWidth() - 1)))
                        , (int) (Math.max(0, Math.min((point3D.getY()), (double) image.getHeight() - 1))));
                return rgb;
            } catch (RuntimeException e) {
                return WHITE;
            }
        } else {
            return GRAY;
        }
    }

    public void setEditOPanel(EditPolygonsMappings editPolygonsMappings) {
        this.editPanel = editPolygonsMappings;
    }

    public void setDistanceABclass(Class<? extends DistanceBezier2> distanceApproxLinearClass) {
        Thread thread = new Thread(() -> {
            boolean isNotOk = true;
            while (isNotOk) {
                try {
                    if (distanceApproxLinearClass.isAssignableFrom(DistanceApproxLinearA.class)) {
                        distanceAB = new DistanceApproxLinearA(pointsInImage.values().stream().toList(),
                                pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                                new Dimension(editPanel.panelModelView.getWidth(),
                                        editPanel.panelModelView.getHeight()));
                        isNotOk = false;
                    } else if (distanceApproxLinearClass.isAssignableFrom(DistanceApproxLinear2.class)) {
                        distanceAB = new DistanceApproxLinear2(pointsInImage.values().stream().toList(),
                                pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                                new Dimension(editPanel.panelModelView.getWidth(),
                                        editPanel.panelModelView.getHeight()));
                        isNotOk = false;
                    } else if (distanceApproxLinearClass.isAssignableFrom(DistanceBezier2.class)) {
                        distanceAB = new DistanceBezier2(pointsInImage.values().stream().toList(),
                                pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                                new Dimension(editPanel.panelModelView.getWidth(),
                                        editPanel.panelModelView.getHeight()));
                        isNotOk = false;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.distanceABclass = (Class<? extends DistanceBezier2>) distanceApproxLinearClass;
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
}