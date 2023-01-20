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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.*;

public class DataModel {
    protected File file;
    private MorphUI morphUI;
    private BufferedImage[] bis = new BufferedImage[2];
    private File[] imagesFiles = new File[2];
    private StructureMatrix<Point3D>[] grids = new StructureMatrix[4];
    private HashMap<String, Object> properties = new HashMap<>();

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
            if (morphUI != null && morphUI.getImageControls1() != null && morphUI.getImageControls2() != null) {
                File outputZip = file;
                FileOutputStream fos = new FileOutputStream(outputZip);
                ZipOutputStream zipOut = new ZipOutputStream(fos);

                File tmp = writeTextTmp();
                System.out.println("Merci Stéphanie");

                Properties properties1 = new Properties();
                Properties properties2 = new Properties();
                Properties[] properties3 = {properties1, properties2};
                ImageControls[] controls = new ImageControls[]{morphUI.getImageControls1(), morphUI.getImageControls2()};
                for (int i = 0; i < controls.length; i++) {
                    ImageControls imageControls = controls[i];
                    Properties properties = properties3[i];
                    properties.put("xGrid", "" + morphUI.getImageControls1().getXgrid());
                    properties.put("yGrid", "" + morphUI.getImageControls1().getYgrid());
                    properties.put("resX", "" + morphUI.getImageControls1().getResX());
                    properties.put("resX", "" + morphUI.getImageControls1().getResY());
                }
                properties3[1].save(new FileOutputStream(tmp), "ImageControls1 values");
                saveFile(zipOut, fos, tmp, "params1.properties");
                tmp = writeTextTmp();
                properties3[1].save(new FileOutputStream(tmp), "ImageControls2 values");
                saveFile(zipOut, fos, tmp, "params2.properties");
                tmp = writeTextTmp();
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveObjectArray2d(File tmp, int xgrid) {
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
        ZipFile zipIn = null;
        ZipInputStream zipInputStream;
        try {
            zipIn = new ZipFile(dataFile);
            zipInputStream = new ZipInputStream(new FileInputStream(dataFile));
        } catch (ZipException ex) {
            ex.printStackTrace();
            return null;
        }
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
                if(name.toLowerCase().endsWith(".txt")) {
                    loadObjectString(loadZipEntry(name, structureMatrix, zipIn, zipEntry), structureMatrix);
                }
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
                    case "params1.properties", "param2.properties":
                        int c = 0;
                        if (name.equals("param2.properties"))
                            c = 1;
                        String s = loadZipEntry(name, null, zipIn, zipEntry);
                        Properties properties1 = new Properties();
                        properties1.load(new StringReader(s));
                        ImageControls[] controls = new ImageControls[]{morphUI.getImageControls1(), morphUI.getImageControls2()};
                        ImageControls imageControls = controls[c];
                        imageControls.setXgrid(Integer.parseInt((String) properties1.get("xGrid")));
                        imageControls.setYgrid(Integer.parseInt((String) properties1.get("yGrid")));
                        imageControls.setResX(Integer.parseInt((String) properties1.get("resX")));
                        imageControls.setResY(Integer.parseInt((String) properties1.get("resX")));

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

        for (int i = split.length - 1; i >= 0; i--) {
            System.out.println(split[i]);
        }

        int j = 0;
        int length = 0;
        int i=1;
        while(i<split.length) {
            try {
                length = Integer.parseInt(split[i]);
            } catch (NumberFormatException ex) {
                System.out.println("Value: (" + split[i] + ")");
                ex.printStackTrace();
            }
            i++;
            j = 0;
            structureMatrix.getData2d().add(new ArrayList<>());

            while (j < length && i<split.length) {
                double d1 = Double.parseDouble(split[i]);
                i++;
                double d2 = Double.parseDouble(split[i]);
                i++;
                double d3 = Double.parseDouble(split[i]);
                i++;
                structureMatrix.getData2d().get(structureMatrix.getData2d().size() - 1).add(new Point3D(d1, d2, d3));
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
