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

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DataModel {
    private MorphUI morphUI;
    private File file;
    public DataModel(MorphUI mui) {
        this.morphUI = mui;
    }
    public void saveFile(File outputZip, File tmpFile, String filenameInZip) throws IOException {
        String sourceFile = filenameInZip;
        FileOutputStream fos = new FileOutputStream(outputZip);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        File fileToZip = tmpFile;
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(filenameInZip);
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        zipOut.close();
        fis.close();
        fos.close();
    }
    public File writeTextTmp() throws IOException {
        return File.createTempFile("tmp", "txt");
    }
    public void save() {
        if(file == null) {

        }else {
            try {
                File tmp = writeTextTmp();
                saveObjectString(tmp, morphUI.getImageControls1().getGrid().toString());
                saveFile(file, tmp, "gridXY1.txt");
                tmp = writeTextTmp();
                saveObjectString(tmp, morphUI.getImageControls1().getGrid().toString());
                saveFile(file, tmp, "gridXY2.txt");
                tmp = writeTextTmp();
                saveObjectString(tmp, morphUI.getImageControls1().getGrid().toString());
                saveFile(file, tmp, "gridUV1.txt");
                tmp = writeTextTmp();
                saveObjectString(tmp, morphUI.getImageControls1().getGrid().toString());
                saveFile(file, tmp, "gridUV2.txt");
                tmp = writeTextTmp();
                ImageIO.write(morphUI.getImageControls1().getImage(), "jpg", tmp);
                saveFile(file, tmp, "image1.bin");
                tmp = writeTextTmp();
                ImageIO.write(morphUI.getImageControls2().getImage(), "jpg", tmp);
                saveFile(file, tmp, "image2.bin");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveObjectString(File tmpGridXY1, String toString) throws IOException {
        ObjectOutputStream pw = new ObjectOutputStream(new FileOutputStream(tmpGridXY1));
        pw.writeObject(toString);
        pw.close();
    }

    public static DataModel load(File dataFile) throws IOException, ClassNotFoundException {
        DataModel dataModel = new DataModel(null);

        ZipFile zipIn = new ZipFile(dataFile);

        dataModel.morphUI = new MorphUI();

        while(zipIn.entries().hasMoreElements()) {
            ZipEntry zipEntry = zipIn.entries().nextElement();
            String name = zipEntry.getName();
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(dataFile));
            ObjectInputStream objectInputStream = new ObjectInputStream(zipInputStream);
            Object o = objectInputStream.readObject();
            switch (name) {
                case "gridXY1.txt":
                    dataModel.morphUI.getImageControls1().setGrid((StructureMatrix<Point3D>) o);
                    break;
                case "gridXY2.txt":
                    dataModel.morphUI.getImageControls2().setGrid((StructureMatrix<Point3D>) o);
                    break;
                case "gridUV1.txt":
                    dataModel.morphUI.getImageControls1().setGridUv((StructureMatrix<Point3D>) o);
                    break;
                case "gridUV2.txt":
                    dataModel.morphUI.getImageControls2().setGridUv((StructureMatrix<Point3D>) o);
                    break;

            }
        }

        return dataModel;
    }
}
