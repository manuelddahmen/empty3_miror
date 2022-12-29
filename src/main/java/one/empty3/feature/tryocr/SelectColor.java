/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.feature.tryocr;

import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.java.awt.Color;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.io.ProcessFile;

import java.io.File;
import java.io.IOException;

public class SelectColor extends ProcessFile {
    private Color color = new Color(255, 255, 255);

    @Override
    public boolean process(File in, File out) {
        PixM pixM = new PixM(ImageIO.read(in));

        PixM pixM2 = new PixM(pixM.getColumns(), pixM.getLines());

        float[] colorCompF = new float[4];

        colorCompF = color.getColorComponents(colorCompF);

        int[] col = new int[4];

        for (int c = 0; c < 4; c++) {
            col[c] = (int) (colorCompF[c] * 255f);
        }

        for (int i = 0; i < pixM.getColumns(); i++)
            for (int j = 0; j < pixM.getColumns(); j++) {
                for (int c = 0; c < 4; c++) {
                    pixM2.set(i, j, pixM.get(i, j) == (col[c] / 255.) ? 1 : 0);
                }
            }
        try {
            ImageIO.write(pixM2.getImage(), "jpg", out);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
