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
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DataModel {
    protected File file;
    private MorphUI morphUI;

    public DataModel(MorphUI mui) {
        this.morphUI = mui;
    }

    private BufferedImage [] bis = new BufferedImage[2];
    private File [] imagesFiles = new File[2];
    private StructureMatrix [] grids = new StructureMatrix[4];

    public void saveFile(ZipOutputStream zipOut, FileOutputStream fos,
                         File tmpFile, String filenameInZip) throws IOException {
        FileInputStream fis = new FileInputStream(tmpFile);
        String sourceFile = filenameInZip;
        ZipEntry zipEntry = new ZipEntry(filenameInZip);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        zipOut.putNextEntry(zipEntry);
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
            return ;
        }
        try {
            File outputZip = file;
            FileOutputStream fos = new FileOutputStream(outputZip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

                File tmp = writeTextTmp();
                saveObjectArray2d(tmp, morphUI.getImageControls1().getGrid());
                saveFile(zipOut,fos, tmp, "gridXY1.txt");
                tmp = writeTextTmp();
                saveObjectArray2d(tmp, morphUI.getImageControls1().getGrid());
                saveFile(zipOut,fos, tmp, "gridXY2.txt");
                tmp = writeTextTmp();
                saveObjectArray2d(tmp, morphUI.getImageControls1().getGridUv());
                saveFile(zipOut,fos, tmp, "gridUV1.txt");
                tmp = writeTextTmp();
                saveObjectArray2d(tmp, morphUI.getImageControls1().getGridUv());
                saveFile(zipOut,fos, tmp, "gridUV2.txt");
                tmp = writeTextTmp();
                ImageIO.write(morphUI.getImageControls1().getImage(), "jpg", tmp);
                saveFile(zipOut,fos, tmp, "image1.jpg");
                ImageIO.write(morphUI.getImageControls2().getImage(), "jpg", tmp);
                saveFile(zipOut,fos, tmp, "image2.jpg");

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
            grid.getData2d().forEach(point3DS -> {
                printWriter.println(point3DS.size());
                point3DS.forEach(point3D -> {
                    for (int i = 0; i < 3; i++) {
                        printWriter.println(point3D.get(i));
                    }
                });
            });
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveObjectString(File tmpGridXY1, Object toString) throws IOException {
        ObjectOutputStream pw = new ObjectOutputStream(new FileOutputStream(tmpGridXY1));
        pw.writeObject(toString);
        pw.close();
    }

    public DataModel load(File dataFile) throws IOException, ClassNotFoundException {
        DataModel dataModel = new DataModel(null);

        ZipFile zipIn = new ZipFile(dataFile);

        dataModel.morphUI = new MorphUI();
        while (zipIn.entries().hasMoreElements()) {
            ZipEntry zipEntry = zipIn.entries().nextElement();
            String name = zipEntry.getName();
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(dataFile));
            if (name.endsWith(".bin")) {
                File tmpFile = File.createTempFile("tmp_open_file", "jpg");
                byte[] bytes = zipInputStream.readAllBytes();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                        bytes);
                FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                BufferedImage read = javax.imageio.ImageIO.read(byteArrayInputStream);
                ImageIO.write(read, "jpg", tmpFile);
                switch (name) {
                    case "image1.jpg":
                        imagesFiles[0] = tmpFile;
                        bis[0] = read;
                        break;
                    case "image2.jpg":
                        imagesFiles[1] = tmpFile;
                        bis[1] = read;
                        break;
                }
            } else {
                StructureMatrix<Point3D> structureMatrix = new StructureMatrix<Point3D>(2, Point3D.class);
                loadObjectString(name, structureMatrix);
                switch (name) {
                    case "gridXY1.txt":
                        grids[0] =( structureMatrix);
                        break;
                    case "gridXY2.txt":
                        grids[1] =(structureMatrix);
                        break;
                    case "gridUV1.txt":
                        grids[2] =(structureMatrix);
                        break;
                    case "gridUV2.txt":
                        grids[3] =(structureMatrix);
                        break;
                }
            }
        }
        dataModel.morphUI.chooseFile1(imagesFiles[0], false);
        dataModel.morphUI.chooseFile2(imagesFiles[1], false);

        dataModel.morphUI.getImageControls1().setGrid(grids[0]);
        dataModel.morphUI.getImageControls2().setGrid(grids[1]);
        dataModel.morphUI.getImageControls1().setGridUv(grids[2]);
        dataModel.morphUI.getImageControls2().setGridUv(grids[3]);

        return dataModel;
    }

    private void loadObjectString(String name, StructureMatrix<Point3D> structureMatrix) {
        File file1 = new File(name);
        if (file1.canRead()) {
            try {
                FileInputStream inputStreamReader = new FileInputStream(file1);
                byte[] bytes = inputStreamReader.readAllBytes();
                String s = new String(bytes);
                String[] split = s.split("\n");

                int j = 0;
                int x = 0;
                for (int i = 1; i < split.length; i++) {
                    int length = 0;
                    String s1 = split[i];
                    if (i == j && i < split.length) {
                        length = Integer.parseInt(s1);
                        structureMatrix.getData2d().add(new ArrayList<>());
                        x++;
                    } else if (i < split.length) {
                        double d1 = Double.parseDouble(split[i++]);
                        double d2 = Double.parseDouble(split[i++]);
                        double d3 = Double.parseDouble(split[i++]);
                        structureMatrix.getData2d().get(x).add(new Point3D(d1, d2, d3));
                        j++;
                    }
                    i++;
                }

            } catch (FileNotFoundException e) {

                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
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
