/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
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

package javaAnd.awt.image.imageio;

//import android.graphics.BufferedImage;
//import android.graphics.BufferedImageFactory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javaAnd.awt.image.BufferedImage;

public class ImageIO {
    private static boolean isOverWrittable = true;

    public static java.awt.image.BufferedImage read(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            java.awt.image.BufferedImage BufferedImage2 = ImageIO.read(fileInputStream);
            fileInputStream.close();
            return BufferedImage2;
        } catch (Exception ex) {}
        return null;
    }

    private static java.awt.image.BufferedImage read(FileInputStream fileInputStream) {
        try {
            return javax.imageio.ImageIO.read(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean write(BufferedImage imageOut, String jpg, File out) {
        boolean result;
        if (!out.exists() || isOverWrittable()) {
            try {
                FileOutputStream fileOutputStream = null;
                fileOutputStream = new FileOutputStream(out);
                javax.imageio.ImageIO.write(imageOut.getBufferedImage(), jpg, out);
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    private static boolean isOverWrittable() {
        return isOverWrittable;
    }


    public static boolean write(java.awt.image.BufferedImage bufferedImage, String jpg, File out) {
        boolean result;
        if (!out.exists() || isOverWrittable()) {
            try {
                FileOutputStream fileOutputStream = null;
                fileOutputStream = new FileOutputStream(out);
                javax.imageio.ImageIO.write(bufferedImage, jpg, out);
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            result = true;
        } else {
            result = false;
        }

        return false;

    }
}
