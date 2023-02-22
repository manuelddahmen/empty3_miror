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

package one.empty3.feature.app.replace.javax.imageio;

//import android.graphics.BufferedImage;
//import android.graphics.BufferedImageFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageIO {
    public static java.awt.image.BufferedImage read(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            java.awt.image.BufferedImage BufferedImage2 = javax.imageio.ImageIO.read(fileInputStream);
            fileInputStream.close();
            return BufferedImage2;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(BufferedImage imageOut, String jpg, File out) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        // ???
        javax.imageio.ImageIO.write(imageOut, jpg, out);
        fileOutputStream.close();
        return false;
    }
    /*
    public static BufferedImage read(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedImage BufferedImage = BufferedImageFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            return BufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(BufferedImage imageOut, String jpg, File out) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        imageOut.compress(BufferedImage.CompressFormat.JPEG, 10, fileOutputStream);
        fileOutputStream.close();
        return false;
    }*/
}
