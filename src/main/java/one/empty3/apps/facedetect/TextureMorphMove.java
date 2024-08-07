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

public class TextureMorphMove extends ITexture {
    private static final int WHITE = Color.WHITE.getRGB();
    private EditPolygonsMappings editPanel;
    public int selectedPointNo = -1;
    protected DistanceAB distanceAB;
    private int GRAY = Color.GRAY.getRGB();
    private Class<? extends DistanceBezier2> distanceABclass;

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }

    public TextureMorphMove(EditPolygonsMappings editPanel, Class<? extends DistanceAB> distanceABclass) {
        super();
        this.editPanel = editPanel;
        this.setDistanceABclass(distanceABclass);
    }

    private TextureMorphMove() {
    }

    @Override
    public int getColorAt(double u, double v) {
        if (distanceAB != null && !distanceAB.isInvalidArray() && editPanel.image != null) {
            try {
                Point3D axPointInB = distanceAB.findAxPointInB(u, v);
                Point3D point3D = new Point3D(axPointInB.getX() * editPanel.image.getWidth(), axPointInB.getY() * editPanel.image.getHeight(), 0.0);
                int rgb = editPanel.image.getRGB((int) (Math.max(0, Math.min(point3D.getX(), (double) editPanel.image.getWidth() - 1)))
                        , (int) (Math.max(0, Math.min((point3D.getY()), (double) editPanel.image.getHeight() - 1))));
                return rgb;
            } catch (RuntimeException e) {
                return WHITE;
            }
        } else {
            return GRAY;
        }
    }

    //public void setEditOPanel(EditPolygonsMappings editPolygonsMappings) {
    //    this.editPanel = editPolygonsMappings;
    //}

    public void setDistanceABclass(Class<? extends DistanceAB> distanceMap) {
        Thread thread = new Thread(() -> {
            boolean isNotOk = true;
            while (isNotOk) {
                try {
                    if (distanceMap.isAssignableFrom(DistanceProxLinear1.class)) {
                        distanceAB = new DistanceProxLinear1(editPanel.pointsInImage.values().stream().toList(),
                                editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                                new Dimension(editPanel.panelModelView.getWidth(),
                                        editPanel.panelModelView.getHeight()), false, false);
                        isNotOk = false;
                    } else if (distanceMap.isAssignableFrom(DistanceProxLinear2.class)) {
                        distanceAB = new DistanceProxLinear2(editPanel.pointsInImage.values().stream().toList(),
                                editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                                new Dimension(editPanel.panelModelView.getWidth(),
                                        editPanel.panelModelView.getHeight()), false, false);
                        isNotOk = false;
                    } else if (distanceMap.isAssignableFrom(DistanceProxLinear3.class)) {
                        distanceAB = new DistanceProxLinear3(editPanel.pointsInImage.values().stream().toList(),
                                editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                                new Dimension(editPanel.panelModelView.getWidth(),
                                        editPanel.panelModelView.getHeight()), false, true);
                        isNotOk = false;
                    } else if (distanceMap.isAssignableFrom(DistanceBezier2.class)) {
                        distanceAB = new DistanceBezier2(editPanel.pointsInImage.values().stream().toList(),
                                editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                                new Dimension(editPanel.panelModelView.getWidth(),
                                        editPanel.panelModelView.getHeight()), false, false);
                        isNotOk = false;
                    } else {
                        distanceAB = new DistanceBB(editPanel.pointsInImage.values().stream().toList(),
                                editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                                new Dimension(editPanel.panelModelView.getWidth(),
                                        editPanel.panelModelView.getHeight()));
                        isNotOk = false;
                    }
                    if (distanceMap != null) {
                        this.distanceABclass = (Class<? extends DistanceBezier2>) distanceMap;
                        editPanel.iTextureMorphMove = this;
                        editPanel.iTextureMorphMove.distanceAB = distanceAB;
                        editPanel.hasChangedAorB = true;
                    } else
                        throw new NullPointerException("distanceMap is null in TextureMorphMove");
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }
}