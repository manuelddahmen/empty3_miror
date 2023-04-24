/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.testscopy.tests;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.*;
import one.empty3.library.core.testing.TestObjetSub;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

public class TestPlanets extends TestObjetSub {
    public static final int SECONDS = 8;
    public static final int FPS = 25;
    private static final int TURNS = 2;
    private final File planets = new File("res\\img\\planets2");
    private File[] planetsImagesFiles;
    private int i = -1;
    private BufferedImage image;
    private Sphere sphere;
    private Logger logger;
    private Point3D axeVerticalVideo = Point3D.Y;
    private Point3D[] axeViseeVideo = new Point3D [] {Point3D.X, Point3D.Z};
    private Point3D[] axesSphereHorizontaux = new Point3D[] {Point3D.X, Point3D.Z};

    @Override
    public void ginit() {
        logger = Logger.getLogger(this.getClass().getCanonicalName());
        planetsImagesFiles = planets.listFiles();

        setMaxFrames(planetsImagesFiles.length*FPS*SECONDS*TURNS);

        z().ratioVerticalAngle();


        z().setDisplayType(ZBufferImpl.SURFACE_DISPLAY_TEXT_QUADS);

        z().texture(new ColorTexture(Color.BLACK));
        scene().texture(new ColorTexture(Color.BLACK));


        Camera c = new Camera(axeViseeVideo[1].mult(10), Point3D.O0, axeVerticalVideo);
        c.calculerMatrice(axeVerticalVideo);
        z().scene().cameraActive(c);
        scene().cameraActive(c);
        z().camera(c);
        camera(c);
        i = -1;

        frame = 801;
        i =  (frame() /( FPS * SECONDS))-1;
        incr();
       }

    @Override
    public void testScene() throws Exception {

    }

    @Override
    public void afterRenderFrame() {

    }

    public void incr() {
        i++;
        if (i < planetsImagesFiles.length)
            image = ImageIO.read(planetsImagesFiles[i]);
        else {
            i = 0;
        };

        System.out.println("Planets:" + i + "/" + planetsImagesFiles.length);
    }

    public void vecDirRotate(Point3D vecOrigX, Point3D vecOrigY, double ratio,
                             Point3D outX, Point3D outY) {
        outX.changeTo(vecOrigX.mult(Math.cos(2*Math.PI*ratio)).plus(
                vecOrigY.mult(Math.sin(2*Math.PI*ratio))));
        outY.changeTo(vecOrigX.mult(-Math.sin(2*Math.PI*ratio)).plus(
                vecOrigY.mult(Math.cos(2*Math.PI*ratio))));
    }

    @Override
    public void finit() throws Exception {
        sphere = new Sphere(new Axe(axeVerticalVideo.mult(1.0), axeVerticalVideo.mult(-1.0)), 2.0);

        sphere.setIncrU(.003);
        sphere.setIncrV(.003);
        scene().clear();
        scene().add(sphere);

        if ((frame() %( FPS * SECONDS) == 1)) {
            incr();
        }
        TextureImg textureImg = new TextureImg(new ECBufferedImage(image));
        sphere.texture(textureImg);

        double v = (frame() % (FPS * SECONDS)) / getaDouble();

        Circle circle = sphere.getCircle();
        circle.setVectZ(axeVerticalVideo);
        circle.getAxis().getElem().getP1().setElem(axeVerticalVideo.mult(1.0));
        circle.getAxis().getElem().getP2().setElem(axeVerticalVideo.mult(-1.0));
        circle.setVectX(axesSphereHorizontaux[0].mult(Math.cos(2*Math.PI*v))
                .plus(axesSphereHorizontaux[1].mult(-Math.sin(2*Math.PI*v))).norme1());
        circle.setVectY(axesSphereHorizontaux[0].mult(Math.sin(2*Math.PI*v))
                .plus(axesSphereHorizontaux[1].mult(Math.cos(2*Math.PI*v))).norme1());
        circle.setCalculerRepere1(true);
        sphere.setCircle(circle);
        System.out.println("Camera t : "+v);
    }

    private static double getaDouble() {
        return 1.0 * FPS * SECONDS;
    }

    @Override
    public void afterRender() {

    }

    public static void main(String[] args) {
        TestPlanets testPlanets = new TestPlanets();
        testPlanets.loop(true);
        testPlanets.setResolution(1920, 1080);
        Thread thread = new Thread(testPlanets);
        thread.start();
    }
}
