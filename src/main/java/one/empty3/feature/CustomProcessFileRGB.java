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
import one.empty3.library.StructureMatrix;
import one.empty3.library1.shader.Vec;
import one.empty3.library1.tree.ListInstructions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
        textDialog = TextDialog.getInstance();
        textDialog.setVisible(true);
    }

    @Override
    public boolean process(File in, File out) {
        PixM pixIn = new PixM(maxRes == 0 ? 1280 : maxRes, maxRes == 0 ? (int) (1280. / 16 * 9) : maxRes);
        BufferedImage readIn;
        if (isImage(in)) {
            readIn = ImageIO.read(in);
            if (readIn == null) {
                return false;
            }
        } else {
            return false;
        }
        pixIn = PixM.getPixM(readIn, maxRes);
        PixM pix = pixIn;
        PixM pixOut = pixIn.copy();
        try {
            HashMap<String, String> currentVecs = new HashMap<>();
            HashMap<String, Double> currentVars = new HashMap<>();
            HashMap<String, StructureMatrix<Double>> currentVecsComputed = new HashMap<>();

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
                    listInstructions.setCurrentParamsValuesVecComputed(currentVecsComputed);
                    listInstructions.addInstructions(textDialog.getText());
                    currentVars.put("r", r);
                    currentVars.put("g", g);
                    currentVars.put("b", b);
                    currentVars.put("x", x);
                    currentVars.put("y", y);
                    currentVecs.put("rgb", (String.format("(%f,%f,%f)", r, g, b)));
                    listInstructions.runInstructions();

                    System.out.println(listInstructions.evaluate("(r,g,b"));

                    currentVecsComputed = listInstructions.getCurrentParamsValuesVecComputed();

                    r = currentVecsComputed.getOrDefault("r", (new StructureMatrix<Double>(0, Double.class).setElem(r))).getElem();
                    g = currentVecsComputed.getOrDefault("g", (new StructureMatrix<Double>(0, Double.class).setElem(g))).getElem();
                    b = currentVecsComputed.getOrDefault("b", (new StructureMatrix<Double>(0, Double.class).setElem(b))).getElem();
                    pixOut.setValues(i, j, r, g, b);
                }
            }

            ImageIO.write(pixOut.normalize(0, 1).getImage(), "jpg", out);

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
