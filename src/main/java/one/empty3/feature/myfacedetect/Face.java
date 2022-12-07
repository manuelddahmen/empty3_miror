package one.empty3.feature.myfacedetect;

import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.io.ProcessFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class Face extends ProcessFile {
    private Rectangle rectangleEyeLeft;
    private Rectangle rectangleEyeRight;
    private Rectangle rectangleMouth;
    @Override
    public boolean process(File in, File out)  {
        PixM inPixM = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)), 300);

        inPixM.applyFilter(new WeightingFunction(100, 100));
        return false;
    }
}
