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

package one.empty3.feature;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import one.empty3.library1.shader.Vec;
import one.empty3.library1.tree.ListInstructions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * $
 * r=0
 * g=1
 * b=0
 */
public class CustomProcessFileRGB extends ProcessFile {
    private static TextDialog textDialog;
    ListInstructions listInstructions;

    public CustomProcessFileRGB() {
        if (textDialog == null || !textDialog.isActive()) {
            textDialog = new TextDialog();
            textDialog.setVisible(true);
        }
    }

    @Override
    public boolean process(File in, File out) {
        PixM pix = new PixM(maxRes == 0 ? 1280 : maxRes, maxRes == 0 ? (int) (1280. / 16 * 9) : maxRes);
        BufferedImage read;
        if (isImage(in)) {
            read = ImageIO.read(in);
            if (read == null) {
                return false;
            }
        } else {
            return false;
        }
        pix = PixM.getPixM(read, maxRes);
        try {
            HashMap<String, String> currentVecs = new HashMap<>();
            HashMap<String, Double> currentVars = new HashMap<>();

            for (int i = 0; i < pix.getColumns(); i++) {
                for (int j = 0; j < pix.getLines(); j++) {
                    double r, g, b, a;
                    double[] values = pix.getValues(i, j);
                    r = values[0];
                    g = values[1];
                    b = values[2];
                    double x = i;
                    double y = j;
                    Vec rgbValOrig = new Vec(r, g, b);

                    listInstructions = new ListInstructions();

                    listInstructions.setCurrentParamsValues(currentVars);
                    listInstructions.setCurrentParamsValuesVec(currentVecs);
                    listInstructions.addInstructions(textDialog.getText());
                    currentVars.put("r", r);
                    currentVars.put("g", r);
                    currentVars.put("b", r);
                    currentVars.put("x", x);
                    currentVars.put("y", y);
                    listInstructions.runInstructions();

                    currentVars = listInstructions.getCurrentParamsValues();

                    if (currentVars.get("r") != null &&
                            currentVars.get("g") != null &&
                            currentVars.get("b") != null &&
                            currentVars.get("x") != null &&
                            currentVars.get("y") != null) {
                        r = currentVars.get("r");
                        g = currentVars.get("g");
                        b = currentVars.get("b");
                        x = currentVars.get("x");
                        y = currentVars.get("y");
                    } else {
//                        r = 0.0;
//                        g = 0.0;
//                        b = 0.0;
//                        x = 0.0;
//                        y = 0.0;
                    }
                    pix.setValues(i, j, r, g, b);
                }
            }

            ImageIO.write(pix.getImage(), "jpg", out);

            return true;

        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    public ListInstructions getListInstructions() {
        return listInstructions;
    }

    public void setListInstructions(ListInstructions listInstructions) {
        this.listInstructions = listInstructions;
    }
}
