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

import java.awt.Color;

import one.empty3.feature.ConvHull;
import one.empty3.library.CopyRepresentableError;
import one.empty3.library.ITexture;
import one.empty3.library.MatrixPropertiesObject;
import one.empty3.library.Point3D;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TextureMorphMove extends ITexture {
    private static final int WHITE = Color.WHITE.getRGB();
    private EditPolygonsMappings editPanel;
    public int selectedPointNo = -1;
    protected DistanceAB distanceAB;
    private int GRAY = Color.GRAY.getRGB();
    private Class<? extends DistanceBezier2> distanceABclass;
    private Vector<Point3D> polyConvA;
    private Vector<Point3D> polyConvB;

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
            if (distanceAB.getClass().isAssignableFrom(DistanceBezier3.class))
                ;
            else if ((distanceAB.sAij == null || distanceAB.sBij == null) && !distanceAB.getClass().isAssignableFrom(DistanceBezier3.class)) {
                //Logger.getAnonymousLogger().log(Level.SEVERE, "DistanceAB .sAij or DistanceAB . sBij is null");
                return 0;
            }
            try {
                Point3D axPointInB = distanceAB.findAxPointInB(u, v);
                if (axPointInB != null) {

                    Point3D point3D = new Point3D(axPointInB.getX() * editPanel.image.getWidth(), axPointInB.getY() * editPanel.image.getHeight(), 0.0);

                    int x = (int) (Math.max(0, Math.min(point3D.getX(), (double) editPanel.image.getWidth() - 1)));
                    int y = (int) (Math.max(0, Math.min((point3D.getY()), (double) editPanel.image.getHeight() - 1)));



                    if (polyConvB == null || polyConvB.isEmpty() || polyConvA == null || polyConvA.isEmpty()) {
                        int rgb = editPanel.image.getRGB(x, y);
                        return rgb;
                    } else if (ConvHull.convexHullTestPointIsInside(polyConvB, new Point3D((double) x, (double) y, 0.0))) {
                        int rgb = editPanel.image.getRGB(x, y);
                        return rgb;
                    } else {
                        int rgb = editPanel.image.getRGB(x, y);
                        return rgb;
                    }
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        int x = (int) (Math.max(0, Math.min(u*((double) editPanel.image.getWidth() - 1),   (double) editPanel.image.getWidth() - 1)));
        int y = (int) (Math.max(0, Math.min(v* ((double) editPanel.image.getHeight() - 1) ,  (double) editPanel.image.getHeight() - 1)));
        int rgb = editPanel.image.getRGB(x, y);
        return rgb;
    }

    //public void setEditOPanel(EditPolygonsMappings editPolygonsMappings) {
    //    this.editPanel = editPolygonsMappings;
    //}

    public void setDistanceABclass(Class<? extends DistanceAB> distanceMap) {
        long timeStarted = System.nanoTime();
        try {
            if (distanceMap.isAssignableFrom(DistanceProxLinear1.class)) {
                distanceAB = new DistanceProxLinear1(editPanel.pointsInImage.values().stream().toList(),
                        editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                        new Dimension(editPanel.panelModelView.getWidth(),
                                editPanel.panelModelView.getHeight()), false, false);
            } else if (distanceMap.isAssignableFrom(DistanceProxLinear2.class)) {
                distanceAB = new DistanceProxLinear2(editPanel.pointsInImage.values().stream().toList(),
                        editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                        new Dimension(editPanel.panelModelView.getWidth(),
                                editPanel.panelModelView.getHeight()), false, false);
            } else if (distanceMap.isAssignableFrom(DistanceProxLinear3.class)) {
                distanceAB = new DistanceProxLinear3(editPanel.pointsInImage.values().stream().toList(),
                        editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                        new Dimension(editPanel.panelModelView.getWidth(),
                                editPanel.panelModelView.getHeight()), false, false);
            } else if (distanceMap.isAssignableFrom(DistanceProxLinear1.class)) {
                distanceAB = new DistanceProxLinear3(editPanel.pointsInImage.values().stream().toList(),
                        editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                        new Dimension(editPanel.panelModelView.getWidth(),
                                editPanel.panelModelView.getHeight()), false, true);
            } else if (distanceMap.isAssignableFrom(DistanceBezier3.class)) {
                distanceAB = new DistanceBezier3(editPanel.pointsInImage.values().stream().toList(),
                        editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                        new Dimension(editPanel.panelModelView.getWidth(),
                                editPanel.panelModelView.getHeight()), false, false);
            } else {
                distanceAB = new DistanceBB(editPanel.pointsInImage.values().stream().toList(),
                        editPanel.pointsInModel.values().stream().toList(), new Dimension(editPanel.panelPicture.getWidth(), editPanel.panelPicture.getHeight()),
                        new Dimension(editPanel.panelModelView.getWidth(),
                                editPanel.panelModelView.getHeight()));
            }
            editPanel.hasChangedAorB = true;

            if (distanceMap != null) {
                this.distanceABclass = (Class<? extends DistanceBezier2>) distanceMap;
                editPanel.iTextureMorphMove = this;
                editPanel.iTextureMorphMove.distanceAB = distanceAB;
                editPanel.hasChangedAorB = false;
                editPanel.distanceABClass = distanceABclass;
            } else {
                throw new NullPointerException("distanceMap is null in TextureMorphMove");
            }
        } catch (RuntimeException ex) {
            editPanel.hasChangedAorB = true;
            ex.printStackTrace();
        }
        long nanoElapsed = System.nanoTime() - timeStarted;
        Logger.getAnonymousLogger().log(Level.INFO, "Temps écoulé à produire l'object DistanceAB (" + distanceMap.getCanonicalName() +
                ") à : " + 10E-9 * nanoElapsed);
    }

    public void setConvHullAB() {

        Map<String, Point3D> hashMapA = Map.copyOf(editPanel.pointsInImage.entrySet().stream()
                .filter(entry -> entry != null && entry.getKey() != null && entry.getValue() != null
                        && entry.getValue().getX() != null && entry.getValue().getY() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        Map<String, Point3D> hashMapB = Map.copyOf(editPanel.pointsInModel.entrySet().stream()
                .filter(entry -> entry != null && entry.getKey() != null && entry.getValue() != null
                        && entry.getValue().getX() != null && entry.getValue().getY() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        Point3D[] aConv = new Point3D[hashMapA.size()];
        int i = 0;
        for (Map.Entry<String, Point3D> stringPoint3DEntry : hashMapA.entrySet()) {
            aConv[i] = stringPoint3DEntry.getValue();
            i++;
        }
        i = 0;
        Point3D[] bConv = new Point3D[hashMapB.size()];
        for (Map.Entry<String, Point3D> stringPoint3DEntry : hashMapA.entrySet()) {
            bConv[i] = stringPoint3DEntry.getValue();
            i++;
        }
        editPanel.iTextureMorphMove.setConvHullA(ConvHull.convexHull(aConv, aConv.length));
        editPanel.iTextureMorphMove.setConvHullB(ConvHull.convexHull(bConv, bConv.length));
    }

    public void setConvHullA(@NotNull Vector<Point3D> polyConvA) {
        this.polyConvA = polyConvA;
    }

    public void setConvHullB(@NotNull Vector<Point3D> polyConvB) {
        this.polyConvB = polyConvB;
    }
}