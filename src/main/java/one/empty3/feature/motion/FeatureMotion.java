package one.empty3.feature.motion;

import one.empty3.feature.FeatureMatch;
import one.empty3.feature.PixM;
import one.empty3.library.Lumiere;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class FeatureMotion extends Motion {
    @Override
    public BufferedImage process(PixM frame1, PixM frame2) {
        FeatureMatch featureMatch = new FeatureMatch();

        List<double[]> match = featureMatch.match(frame1, frame2);

        BufferedImage bufferedImage = new BufferedImage(frame1.getColumns(), frame1.getLines(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < frame1.getColumns(); i++) {
            for (int j = 0; j < frame1.getLines(); j++) {
                for (int c = 0; c < frame1.getCompCount(); c++) {
                    bufferedImage.setRGB(i, j, Lumiere.getInt(frame1.getValues(i, j)));
                }
            }
        }
        for (int i = 0; i < match.size(); i++) {
            bufferedImage.setRGB((int) match.get(i)[0], (int) match.get(i)[1], Color.WHITE.getRGB());
        }
        return bufferedImage;
    }
}
