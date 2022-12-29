/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.feature;

import one.empty3.feature.motion.Motion;

import java.awt.image.BufferedImage;

public
class EffectMotion extends Motion {
    @Override
    public BufferedImage process(PixM frame1, PixM frame2) {
        return frame2.getImage();
    }
}
