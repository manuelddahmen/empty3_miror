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

/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package atlasgen;

import one.empty3.library.ColorTexture;
import one.empty3.library.core.lighting.Colors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColoredMaps {

    public static void main(String[] args) {
        Logger.getAnonymousLogger().log(Level.INFO,
                "Colored Maps -- first pass Setting Rect Boundaries"
        );


        CsvReader csvReader = new CsvReader(new File("allCountries/allCountries.txt"),
                "" + '\t', "" + '\n', false);
        SetMinMax setMinMax = new SetMinMax();
        csvReader.setAction(setMinMax);
        csvReader.process();


        HashMap<String, SetMinMax.MyDim> myDims = setMinMax.getMyDims();

        myDims.forEach((s, dim) -> {
            Logger.getAnonymousLogger().log(Level.INFO, s);
            Logger.getAnonymousLogger().log(Level.INFO, dim.toString());
        });
        Logger.getAnonymousLogger().log(Level.INFO, "myDims size " + myDims.size());


        String serial = Seriald.newSerial();


        myDims.forEach((countryCode, myDim) -> {
            Logger.getAnonymousLogger().log(Level.INFO, 
                    "Colored Maps map " + countryCode
            );
            try {
                BufferedImage image = new BufferedImage(1800,
                        (int)
                                Ratio.imageHeight(myDim.latitudeExtend(),
                                        myDim.longitudeExtend(),
                                        0,
                                        1800), BufferedImage.TYPE_INT_RGB);
                Pixeler pixeler = new Pixeler(image);
                Graphics graphics = image.getGraphics();
                graphics.setColor(new Color(Color.TRANSLUCENT));
                graphics.fillRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);

                CsvReader csvReader1 = new CsvReader(new File("allCountries/allCountries.txt"),
                        "" + '\t', "" + '\n', false);
                csvReader1.setAction(new DrawOneCountryAction(
                        pixeler,
                        myDim,
                        new ColorTexture(Colors.random())
                ));
                csvReader1.process();
                ImageIO.write(pixeler.getImage(), "jpg", new File(
                        getColoredMaps(serial, countryCode)));

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        });
    }

    private static String getColoredMaps(String serial, String countryCode) {
        String fileStr1 = ".\\generated_Maps\\"
                + "\\map-" + serial + "\\" + countryCode;
        File file = new File(fileStr1);
        file.mkdirs();
        return fileStr1 + ".jpg";
    }
}
