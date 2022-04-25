package one.empty3.feature.gui;

import one.empty3.feature.PixM;
import one.empty3.feature.motion.Motion;

import java.awt.image.BufferedImage;

public class LastFrameMotion extends Motion {

    
    @Override
    public BufferedImage process(PixM frame1, PixM frame2) {
        return frame2.getImage();
    }
}
