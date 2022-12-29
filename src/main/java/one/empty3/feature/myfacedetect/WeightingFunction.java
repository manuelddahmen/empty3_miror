/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.feature.myfacedetect;

import one.empty3.feature.FilterPixM;
import one.empty3.feature.PixM;

public class WeightingFunction extends FilterPixM {
    private static final double MIN_START = 0.2;

    public WeightingFunction(int l, int c) {
        super(l, c);
        generatePixels();
    }

    @Override
    public double filter(double x, double y) {
        double ratioY = y / getLines();
        for (int c = 0; c < 3; c++) {
            switch (c) {
                case 0, 1, 2 -> {
                    //                   set((int) x, (int) y, ratioY <= MIN_START ? ratioY / MIN_START : (ratioY < 1 - MIN_START ? 1.0 :
                    //
                    return ratioY <= MIN_START ? ratioY / MIN_START : (ratioY < 1 - MIN_START ? 1.0 : ratioY * (1 - MIN_START));
                }
            }
        }
        return Double.NaN;
    }

    public void generatePixels() {
        for(int j = 0; j<lines; j++) {
            double percentsY = 1.0 * j / getLines();
            for(int i = 0; i<columns; i++) {
                for(int c=0; c<3; c++) {
                    setCompNo(c);
                    switch (c) {
                        case 0:
                        case 1:
                        case 2:
                            set(i, j, percentsY<=MIN_START?percentsY/MIN_START:(percentsY<1-MIN_START?1.0:
                                    percentsY*(1-MIN_START)));
                    }
                }
            }
        }
    }
}
