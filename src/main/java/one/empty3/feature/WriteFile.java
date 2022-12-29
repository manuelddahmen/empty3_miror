/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.feature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteFile {
    static int no = 1;
    static String directory = "./output/";

    public static void init() {

    }

    public static boolean writeNext(BufferedImage imageJpeg, String name) {
        writeNext(name, imageJpeg);
        return true;
    }

    public static boolean writeNext(String name, BufferedImage imageJpeg) {

        File n = new File(directory);

        n = new File(directory + File.separator + no + "-" + name + ".jpg");
        n.mkdirs();
        int i = n.getAbsolutePath().lastIndexOf('/');
        new File(n.getAbsolutePath().substring(0, i>=0?i:0)).mkdirs();
        try {

            no++;
            ImageIO.write(imageJpeg, "jpg", n);
            Logger.getLogger(WriteFile.class.toString()).log(Level.INFO, "file written: " + n.getAbsolutePath());
            return true;

        } catch (Exception ex) {

            ex.printStackTrace();
            return false;
        }

    }
}
