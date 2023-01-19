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

package one.empty3.apps.morph;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DataModel {
    protected File file;
    private MorphUI morphUI;
    private BufferedImage[] bis = new BufferedImage[2];
    private File[] imagesFiles = new File[2];
    private StructureMatrix<Point3D>[] grids = new StructureMatrix[4];

    public DataModel(MorphUI mui) {
        this.morphUI = mui;
    }

    public void saveFile(ZipOutputStream zipOut, FileOutputStream fos,
                         File tmpFile, String filenameInZip) throws IOException {
        FileInputStream fis = new FileInputStream(tmpFile);

        ZipEntry zipEntry = new ZipEntry(filenameInZip);

        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        fis.close();
    }

    public File writeTextTmp() throws IOException {
        return File.createTempFile("tmp", "txt");
    }

    public void save(File file) {
        this.file = file;
        save();
    }

    public void save() {
        if (file == null) {
            return;
        }
        try {
            File outputZip = file;
            FileOutputStream fos = new FileOutputStream(outputZip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            File tmp = writeTextTmp();
            saveObjectArray2d(tmp, morphUI.getImageControls1().getGrid());
            saveFile(zipOut, fos, tmp, "gridXY1.txt");
            tmp = writeTextTmp();
            saveObjectArray2d(tmp, morphUI.getImageControls1().getGrid());
            saveFile(zipOut, fos, tmp, "gridXY2.txt");
            tmp = writeTextTmp();
            saveObjectArray2d(tmp, morphUI.getImageControls1().getGridUv());
            saveFile(zipOut, fos, tmp, "gridUV1.txt");
            tmp = writeTextTmp();
            saveObjectArray2d(tmp, morphUI.getImageControls1().getGridUv());
            saveFile(zipOut, fos, tmp, "gridUV2.txt");
            tmp = writeTextTmp();
            ImageIO.write(morphUI.getImageControls1().getImage(), "jpg", tmp);
            saveFile(zipOut, fos, tmp, "image1.jpg");
            ImageIO.write(morphUI.getImageControls2().getImage(), "jpg", tmp);
            saveFile(zipOut, fos, tmp, "image2.jpg");

            zipOut.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveObjectArray2d(File tmp, StructureMatrix<Point3D> grid) {
        try {
            PrintWriter printWriter = new PrintWriter(tmp);
            printWriter.println(grid.getData2d().size());
            for (List<Point3D> point3DS : grid.getData2d()) {
                printWriter.println(point3DS.size());
                for (Point3D point3D : point3DS) {
                    for (int i = 0; i < 3; i++) {
                        printWriter.println(point3D.get(i));
                    }
                }
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public DataModel load(File dataFile) throws IOException, ClassNotFoundException {
        ZipFile zipIn = new ZipFile(dataFile);
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(dataFile));
        file = dataFile;
        Iterator<? extends ZipEntry> entries = zipIn.entries().asIterator();
        while (entries.hasNext()) {
            ZipEntry zipEntry = entries.next();
            String name = zipEntry.getName();
            BufferedInputStream inputStream = new BufferedInputStream(zipIn.getInputStream(zipEntry));
            int i = 0;
            byte[] data = new byte[(int) zipEntry.getSize()];
            while (i < zipEntry.getSize()) {
                int read = inputStream.read(data, i, data.length);
                i += read;
            }
            if (name.endsWith(".jpg")) {
                File tmpFile = File.createTempFile("tmp_open_file_" + UUID.randomUUID(), ".jpg");
                FileOutputStream fileOutputStream2 = new FileOutputStream(tmpFile);
                fileOutputStream2.write(data);
                fileOutputStream2.close();
                BufferedImage read = ImageIO.read(tmpFile);
                if (read == null) {
                    Logger.getAnonymousLogger().log(Level.SEVERE, "Fichier image endommagé ou non reconnu" +
                            " ou erreur lors de la lecture du ZIP");
                    System.exit(-1);
                }
                switch (name) {
                    case "image1.jpg" -> {
                        imagesFiles[0] = tmpFile;
                        bis[0] = read;
                    }
                    case "image2.jpg" -> {
                        imagesFiles[1] = tmpFile;
                        bis[1] = read;
                    }
                }
            } else {
                StructureMatrix<Point3D> structureMatrix = new StructureMatrix<Point3D>(2, Point3D.class);
                loadObjectString(loadZipEntry(name, structureMatrix, zipIn, zipEntry), structureMatrix);
                switch (name) {
                    case "gridXY1.txt":
                        grids[0] = (structureMatrix);
                        break;
                    case "gridXY2.txt":
                        grids[1] = (structureMatrix);
                        break;
                    case "gridUV1.txt":
                        grids[2] = (structureMatrix);
                        break;
                    case "gridUV2.txt":
                        grids[3] = (structureMatrix);
                        break;
                }
            }
        }
        morphUI.chooseFile1(imagesFiles[0]);
        morphUI.chooseFile2(imagesFiles[1]);

        morphUI.getImageControls1().setGrid(grids[0]);
        morphUI.getImageControls2().setGrid(grids[1]);
        morphUI.getImageControls1().setGridUv(grids[2]);
        morphUI.getImageControls2().setGridUv(grids[3]);

        for (int i = 0; i < grids.length; i++) {
            System.out.println(grids[i].getData2d().toString());
        }
        for (int i = 0; i < imagesFiles.length; i++) {
            System.out.println(imagesFiles[i].getName());
        }

        return this;
    }

    private String loadZipEntry(String name, StructureMatrix<Point3D> structureMatrix, ZipFile zipIn, ZipEntry zipEntry) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(zipIn.getInputStream(zipEntry));
            int i = 0;
            byte[] data = new byte[(int) zipEntry.getSize()];
            while (i < zipEntry.getSize()) {
                int read = inputStream.read(data, i, data.length);
                i += read;
            }
            String s = new String(data, "UTF-8");
            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadObjectString(String txt, StructureMatrix<Point3D> structureMatrix) {
        String[] split = txt.split("\n");

        int j = 0;
        int x = 0;
        structureMatrix.getData2d().add(new ArrayList<>());
        for (int i = 1; i < split.length; i++) {
            int length = 0;
            String s1 = split[i];
            if (j == length-1) {
                x = 0;
                length = Integer.parseInt(s1);
                x++;
                j = 0;
                structureMatrix.getData2d().add(new ArrayList<>());
            } else {
                double d1 = Double.parseDouble(split[i++]);
                double d2 = Double.parseDouble(split[i++]);
                double d3 = Double.parseDouble(split[i++]);
                structureMatrix.getData2d().get(x).add(new Point3D(d1, d2, d3));
                j++;
            }
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
