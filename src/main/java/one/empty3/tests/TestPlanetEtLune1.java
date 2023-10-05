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

package one.empty3.tests;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.*;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.testscopy.tests.tests2.balleclou.BalleClous2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

public class TestPlanetEtLune1 extends TestObjetSub {
    public static final int SECONDS = 30;
    public static final int FPS = 25;
    private static final int TURNS = 1;
    private static final int REAL_DAYS = 28;
    private final File planets = new File("res\\img\\planets2");
    private File earthFilename = new File(planets.getAbsolutePath()+
            File.separator+"_earth.jpg");
    private File moonFilename = new File(planets.getAbsolutePath()+
            File.separator+"8k_moon.jpg");
    private File sunFilename = new File(planets.getAbsolutePath()+
            File.separator+"8k_sun.jpg");
    private final double sunDistance = 150;
    private final double moonDistance = 0.384;
    private final double sunRealSize = 13927E6/2;
    private final double earthRealSize = (double) 12756 /2;
    private final double moonRealSize = 3475;
    private final double earthPeriod = 365.256;
    private final double moonPeriod = 655.7/24;
    private final double earthMass = 5.97;//E24
    private final double sunMass = 1.98847E6 ;
    private final double moonMass = 0.073;

    private int i = -1;
    private BufferedImage image;
    private Logger logger;
    private Point3D axeVerticalVideo = Point3D.Y;
    private Point3D[] axeViseeVideo = new Point3D[]{Point3D.X, Point3D.Z};
    private Point3D[] axesSphereHorizontaux = new Point3D[]{Point3D.X, Point3D.Z};
    private final int nBalles = 1;
    private Point3D[][] s;
    private Point3D[][] v;
    private int N = 20;
    private double V = 10;
    private double D = 10.0;
    private Sphere sun;
    private Sphere earth;
    private Sphere moon;

    private static double getaDouble() {
        return TURNS * FPS * SECONDS;
    }

    public static void main(String[] args) {
        TestPlanetEtLune1 testPlanets = new TestPlanetEtLune1();
        testPlanets.loop(true);
        testPlanets.setResolution(640, 400);
        Thread thread = new Thread(testPlanets);
        thread.start();
    }

    @Override
    public void ginit() {
        sun = new Sphere(new Point3D(0., 0.,sunDistance), sunRealSize);
        earth = new Sphere(new Point3D(0., 0.,0.), earthRealSize);
        moon = new Sphere(new Point3D(0., moonDistance, 0.0), moonRealSize);

        sun.texture(new ImageTexture(new ECBufferedImage(ImageIO.read(sunFilename))));
        earth.texture(new ImageTexture(new ECBufferedImage(ImageIO.read(earthFilename))));
        moon.texture(new ImageTexture(new ECBufferedImage(ImageIO.read(moonFilename))));

        logger = Logger.getLogger(this.getClass().getCanonicalName());
        setMaxFrames( FPS * SECONDS * REAL_DAYS);

        z().ratioVerticalAngle();


        z().setDisplayType(ZBufferImpl.SURFACE_DISPLAY_TEXT_QUADS);

        z().texture(new ColorTexture(Color.BLACK));
        scene().texture(new ColorTexture(Color.BLACK));


/*
        Camera c = new Camera(axeViseeVideo[1].mult((double)  earthRealSize*1.4),
                axeViseeVideo[1].mult((double)  earthRealSize*1.4)
                        .prodVect(axeVerticalVideo.mult(earthRealSize))
                        .mult(1./earthRealSize), axeVerticalVideo);
*/
        camera(c);
        i = -1;

        LumierePointSimple lumierePointSimple = new LumierePointSimple(Color.YELLOW, sun.getCircle().getCenter(), 100000);

        //scene().lumieres().add(lumierePointSimple);

        frame = 0;
    }

    public Point3D positions() {
        Point3D p = new Point3D();
        p.set(0, 1.0*(frame() / (FPS * SECONDS * TURNS)));
        p.set(1, 1.0*(frame() % (FPS * SECONDS * TURNS)));
        p.set(2, 1.0* FPS * SECONDS * TURNS);

        return p;
    }

    public void vecDirRotate(Point3D vecOrigX, Point3D vecOrigY, double ratio,
                             Point3D outX, Point3D outY) {
        outX.changeTo(vecOrigX.mult(Math.cos(2 * Math.PI * ratio)).plus(
                vecOrigY.mult(Math.sin(2 * Math.PI * ratio))));
        outY.changeTo(vecOrigX.mult(-Math.sin(2 * Math.PI * ratio)).plus(
                vecOrigY.mult(Math.cos(2 * Math.PI * ratio))));
    }

    @Override
    public void finit() throws Exception {
        Camera c = new Camera(axeViseeVideo[1].mult(Math.cos(2.0*Math.PI*frame()/getMaxFrames()))
                .plus(axeViseeVideo[0].mult(Math.sin(2.0*Math.PI*frame()/getMaxFrames())))
                .prodVect(axeVerticalVideo.mult(earthRealSize))
                .mult(6), axeVerticalVideo);
        c.calculerMatrice(axeVerticalVideo);
        c.setAngleY(Math.PI/3);
        c.setAngleX(Math.PI/3*z().la()/z().ha());
        z().scene().cameraActive(c);
        scene().cameraActive(c);
        z().camera(c);


        for(Sphere sphere :new Sphere[] {moon, sun, earth}) {
            sphere.setIncrU(.003);
            sphere.setIncrV(.003);
            scene().clear();
            scene().add(sphere);
        }

        double v = (frame() % (FPS * SECONDS)) / getaDouble();

        Circle circle = earth.getCircle();
        earth.setVectZ(axeVerticalVideo);
        double lat = 0.0;
        circle.getAxis().getElem().getP1().setElem(axeVerticalVideo.mult(1.0));
        circle.getAxis().getElem().getP2().setElem(axeVerticalVideo.mult(-1.0));
        earth.setVectX(axesSphereHorizontaux[0].mult(Math.cos(2 * Math.PI * v)
                        * Math.cos(Math.PI/2*lat))
                .plus(axesSphereHorizontaux[1].mult(-Math.sin(2 * Math.PI * v)
                        * Math.cos(Math.PI/2*lat))).norme1());
        earth.setVectY(axesSphereHorizontaux[0].mult(Math.sin(2 * Math.PI * v)
                        * Math.cos(Math.PI/2*lat))
                .plus(axesSphereHorizontaux[1].mult(Math.cos(2 * Math.PI * v)
                        * Math.cos(Math.PI/2*lat))).norme1());
        circle.setCalculerRepere1(true);
        earth.setCircle(circle);
        System.out.println("Camera t : " + v);

    }


}
