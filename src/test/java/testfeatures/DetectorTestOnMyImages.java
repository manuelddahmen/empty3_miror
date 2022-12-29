/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package testfeatures;

import one.empty3.feature.jviolajones.Detector;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class DetectorTestOnMyImages {
    @Test
    public void testDetector() throws IOException {
        for (File img : Objects.requireNonNull(new File("resources/img/faces/").listFiles())) {
            if (img.getName().endsWith("jpg") || img.getName().endsWith("png")) {
                try {
                    InputStream haarXml = Detector.class.getResourceAsStream("/haarcascade_frontalface_alt2.xml");

                    Detector detector = new Detector(haarXml);
                    List<Rectangle> res = null;
                    res = detector.getFaces(img.getAbsolutePath(), 1, 1.25f, 0.1f, 1, true);

                    if (res.size() == 0) {
                        System.out.printf("No face\n");
                    } else {
                        for (Rectangle re : res) {
                            System.out.printf("Face %d\n%d\n%d\n%d\n",
                                    re.x, re.y, re.width, re.height);

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
