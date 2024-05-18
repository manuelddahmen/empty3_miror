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

package one.empty3.exp;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.feature.MultiLinkList;
import one.empty3.feature.PixM;
import one.empty3.feature.V;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.F;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Voronoi extends ProcessFile {
    private ArrayList<Point3D> points = new ArrayList<>();

    public void processTest() {
        try {
            PixM mIn = new PixM(2000, 2000);
            PixM mOut = new PixM(mIn.getColumns(), mIn.getLines());

            initPixMmIn(mIn);

            mOut = voronoi(mIn, mOut);
            BufferedImage image = mIn.getImage();
            ImageIO.write(image, "jpg", new File("results/voronoi.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initPixMmIn(PixM mIn) {
        for (int i = 0; i < mIn.getColumns(); i++) {
            for (int j = 0; j < mIn.getLines(); j++) {
                mIn.setP(i, j, new Point3D((double) i / mIn.getColumns(),
                        (double) j / mIn.getLines(), 0.0));

            }

        }
    }

    @Override
    public boolean process(File in, File out) {
        try {
            PixM mIn = new PixM(Objects.requireNonNull(ImageIO.read(in)));

            PixM mOut = new PixM(mIn.getColumns(), mIn.getLines());
            voronoi(mIn, mOut);
            BufferedImage image = mIn.getImage();
            ImageIO.write(image, "jpg", out);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void genRandomPoints(PixM in, int n) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            points.add(new Point3D((double) r.nextInt(in.getColumns()), (double) (r.nextInt(in.getLines())), 0.0));
        }
    }

    private PixM voronoi(PixM mIn, PixM mOut) {
        genRandomPoints(mIn, 20);
        for (int i = 0; i < mIn.getColumns(); i++) {
            for (int j = 0; j < mIn.getLines(); j++) {
                Point3D d = new Point3D((double) i, (double) j, 0.0);
                for (int k = 0; k < points.size(); k++) {
                    d = d.plus(mIn.getP(i, j).prodVect(points.get(k)));
                }
            }
        }
        return mOut.normalize(0, 1);
    }

    public static void main(String[] args) {
        Voronoi voronoi = new Voronoi();
        voronoi.processTest();
    }
}
