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

import one.empty3.feature.PixM;
import one.empty3.library.Polygon;
import one.empty3.library.*;
import one.empty3.library.core.testing.Resolution;
import one.empty3.library.core.testing.TestObjetStub;
import one.empty3.library.objloader.E3Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestHumanHeadTexturing extends TestObjetStub {
    private Rectangle rectangleFace;
    private BufferedImage trueFace;
    private BufferedImage jpgFile;
    private E3Model objFile;
    private EditPolygonsMappings editPolygonsMappings;

    public TestHumanHeadTexturing() {
    }

    public static TestHumanHeadTexturing restartAll(TestHumanHeadTexturing testHumanHeadTexturing) {
        if (testHumanHeadTexturing != null) {
            testHumanHeadTexturing.stop();
            return startAll(testHumanHeadTexturing.editPolygonsMappings, testHumanHeadTexturing.jpgFile, testHumanHeadTexturing.objFile);
        }
        return null;
    }

    public void setImageIn(PixM face) {
        this.trueFace = face.getImage();
    }

    @Override
    public void finit() {
        super.finit();
        z().setDisplayType(ZBufferImpl.DISPLAY_ALL);
        File intPart = new File("faceSkin.txt");
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(intPart);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (jpgFile != null && objFile != null) {
            E3Model e3Model = objFile;
            if (editPolygonsMappings != null && editPolygonsMappings.iTextureMorphImage != null
                    && editPolygonsMappings.distanceAB != null)
                e3Model.texture(editPolygonsMappings.iTextureMorphImage);
            else
                e3Model.texture(new ImageTexture(new ECBufferedImage(jpgFile)));
            scene().add(e3Model);
            if (scene().getObjets().data1d.size() >= 2) {
                scene().getObjets().delete(1);
            }
            printWriter.println("# Face elements without eyes month and nose");
        /*AtomicInteger i = new AtomicInteger(0);
        ((RepresentableConteneur) (scene().getObjets().getElem(0))).
                getListRepresentable().forEach(representable -> {
                    int r = (int) Math.min((i.get() / (256)) % 256, 255);
                    int g = (int) Math.min(i.get() % (256 * 256), 255);
                    int b = i.get() % 256;
                    Color def = new Color(r, g, b);
                    if ((g < 222 && b > 16) || i.get() <= 221) {
                        def = Color.BLACK;
                        printWriter.println(i);
                    } else {
                        def = Color.WHITE;
                    }
                    representable.setTexture(new ColorTexture(def));
                    i.getAndIncrement();
                });*/
            printWriter.flush();
            printWriter.close();

            Camera c = new Camera();
            c.getEye().setZ(c.getEye().getX() / 8);
            c.getEye().setX(0.0);
            c.calculerMatrice(Point3D.Y.mult(-1));
            camera(c);
            scene().cameraActive(c);
            c.setAngleYr(60, 1.0 * z().la() / z().ha());
         /*   mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Point point = e.getPoint();
                    int x = point.x;
                    int y = point.y;
                    point = new Point((int) (1.0 * x / getPanelDraw().getWidth() * img().getWidth()),
                            (int) (1.0 * y / getPanelDraw().getHeight() * img().getHeight()));
                    Representable elementRepresentable = z().ime.getElementRepresentable(point.x, point.y);
                    if (elementRepresentable instanceof Representable representable) {
                        ColorTexture colorTexture = new ColorTexture(Color.RED);
                        elementRepresentable.texture(colorTexture);
                        Logger.getAnonymousLogger().log(Level.INFO, "Nombre d'éléments dans la scène : " + scene().getObjets().data1d.size());
                        Logger.getAnonymousLogger().log(Level.INFO, "Nombre d'éléments dans la scène (1er objet): " + ((RepresentableConteneur) scene().getObjets().getElem(0)).getListRepresentable().size());
                        AtomicInteger tri = new AtomicInteger();
                        AtomicInteger quad = new AtomicInteger();
                        AtomicInteger polygon = new AtomicInteger();
                        ((RepresentableConteneur) scene().getObjets().getElem(0)).getListRepresentable().forEach(representableCompare -> {
                            if (representable instanceof TRI tr1 && representableCompare instanceof TRI tr2) {

                                tri.getAndIncrement();
                                if (tr1.getCentre().moins(tr2.getCentre()).norme() <= 0.0) {
                                    tr2.texture(colorTexture);
                                }
                            } else if (representable instanceof Quads quads1 && representableCompare instanceof Quads quads2) {
                                quad.incrementAndGet();
                                quads2.texture(colorTexture);
                            } else if (representable instanceof Polygon polygon1 && representableCompare instanceof Polygon polygon2) {
                                polygon.incrementAndGet();
                                if (polygon1.getIsocentre().moins(polygon2.getIsocentre()).norme() <= 0.0) {
                                    polygon2.texture(colorTexture);
                                    z().draw(polygon2);
                                }
                            }
                        });
                        Logger.getAnonymousLogger().log(Level.INFO, "Nombre de TRI     dans la scène (1er objet): " + tri);
                        Logger.getAnonymousLogger().log(Level.INFO, "Nombre de Polygon dans la scène (1er objet): " + polygon);
                        Logger.getAnonymousLogger().log(Level.INFO, "Nombre de Quad    dans la scène (1er objet): " + quad);
                    }
                    System.err.println("" + elementRepresentable + " or" + (elementRepresentable != null ? elementRepresentable.getClass().getName() : "null"));
                }
            };
            if (!isset && getPanelDraw() != null && img() != null) {
                getPanelDraw().addMouseListener(mouseAdapter);
                isset = true;
                Logger.getAnonymousLogger().log(Level.INFO, "mouse adapter added");
            }
            */
        }
    }

    @Override
    public void afterRender() {
        if (jpgFile != null && objFile != null) {

            rectangleFace = new Rectangle(img().getWidth(), img().getHeight(), 0, 0);
            // Step 2 cadrer les polygones
            ((RepresentableConteneur) scene().getObjets().getElem(0)).getListRepresentable().forEach(representable -> {
                if (representable instanceof Polygon p && representable.texture().getColorAt(0.5, 0.5) == Color.BLACK.getRGB()) {
                    p.getPoints().getData1d().forEach(point3D -> {
                        Point point = camera().coordonneesPoint2D(point3D, z());
                        Rectangle r2 = new Rectangle(rectangleFace.x, rectangleFace.y,
                                rectangleFace.width, rectangleFace.height);
                        if (point.getX() < rectangleFace.x) {
                            r2.x = point.x;
                        }
                        if (point.getY() < rectangleFace.y) {
                            r2.y = point.y;
                        }
                        if (point.getX() - rectangleFace.x > rectangleFace.width) {
                            r2.width = (int) (point.getX() - rectangleFace.x);
                        }
                        if (point.getY() - rectangleFace.y > rectangleFace.height) {
                            r2.height = (int) (point.getY() - rectangleFace.y);
                        }

                        rectangleFace = r2;
                    });
                }
            });
        }
    }


    public static TestHumanHeadTexturing startAll(EditPolygonsMappings editPolygonsMappings, BufferedImage jpg, E3Model obj) {
        Logger.getAnonymousLogger().log(Level.INFO, "Jpg Obj Mapping...");

        TestHumanHeadTexturing testHumanHeadTexturing = new TestHumanHeadTexturing();
        testHumanHeadTexturing.editPolygonsMappings = editPolygonsMappings;
        testHumanHeadTexturing.setGenerate(testHumanHeadTexturing.getGenerate() & (0xFFFFFFFF -
                (GENERATE_SAVE_IMAGE | TestObjetStub.GENERATE_MOVIE | TestObjetStub.GENERATE_MODEL)));
        testHumanHeadTexturing.setJpg(editPolygonsMappings.image);
        testHumanHeadTexturing.setObj(editPolygonsMappings.model);
        testHumanHeadTexturing.loop(true);
        testHumanHeadTexturing.setMaxFrames(Integer.MAX_VALUE);
        testHumanHeadTexturing.setPublish(false);
        testHumanHeadTexturing.setDimension(new Resolution(editPolygonsMappings.panelModelView.getWidth(), editPolygonsMappings.panelModelView.getHeight()));

        new Thread(testHumanHeadTexturing).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return testHumanHeadTexturing;
    }

    protected void setJpg(BufferedImage jpgFile) {
        this.jpgFile = jpgFile;
    }

    void setObj(E3Model objFile) {
        this.objFile = objFile;
    }

    public Rectangle getRectangleFace() {
        return rectangleFace;
    }

    public void setRectangleFace(Rectangle rectangleFace) {
        this.rectangleFace = rectangleFace;
    }

    public BufferedImage getTrueFace() {
        return trueFace;
    }

    public void setTrueFace(BufferedImage trueFace) {
        this.trueFace = trueFace;
    }
}
