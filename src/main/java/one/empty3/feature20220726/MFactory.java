/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;

import android.graphics.BufferedImage;
import android.graphics.BufferedImageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.MappedByteBuffer;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;

public class MFactory {
//
//    public static InterfaceMatrix getInstance(BufferedImage BufferedImage) {
//        return new M(BufferedImage);
//    }
//    public static InterfaceMatrix getInstance(BufferedImage BufferedImage, int resMax) {
//        return new MBufferedImage(BufferedImage, resMax);
//    }
//    public static InterfaceMatrix getInstance(int lines, int columns, boolean isBufferedImage) {
//        if(isBufferedImage) {
//            return new MBufferedImage(lines, columns);
//        } else {
//            return new M(lines, columns);
//        }
//
//    }
//
//    public static InterfaceMatrix getInstance(File in, boolean isBufferedImage) {
//        if(isBufferedImage)  {
//            FileInputStream fileInputStream = null;
//            try {
//                fileInputStream = new FileInputStream(in);
//                BufferedImage BufferedImage = BufferedImageFactory.decodeStream(fileInputStream);
//                return new MBufferedImage(BufferedImage);
//            } catch (FileNotFoundException e) {
//
//                e.printStackTrace();
//            }
//            return null;
//        } else {
//            BufferedImage read = ImageIO.read(in);
//            assert read != null;
//            return new PixM(read);
//        }
//    }
}
