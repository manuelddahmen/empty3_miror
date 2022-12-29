/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.feature.facemorph;

import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.feature.myfacedetect.WeightingFunction;
import one.empty3.io.ProcessFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class RunFeatures extends ProcessFile {
    WeightingFunction function;
    @Override
    public boolean process(File in, File out) {
        PixM imageIn = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)), maxRes);

        double ratioFace = 1;
        double ratioXy = (1.0)*imageIn.getColumns()/imageIn.getLines();

        int filterMaxRes = 10;
        function = new WeightingFunction(filterMaxRes, (int)(filterMaxRes*ratioXy));

        PixM imageOut = imageIn.applyFilter(function);

        try {
            ImageIO.write(imageOut.normalize(0.,1.).getImage(), "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
