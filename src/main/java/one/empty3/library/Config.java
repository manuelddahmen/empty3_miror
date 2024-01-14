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

package one.empty3.library;

import java.io.*;
import java.util.Properties;

public class Config {

    public static final int DIR_TMP = 0;
    public static final int DIR_TEST_OUTPUT = 1;
    public static final int DIR_MODELS = 2;
    public static final int DIR_TEXTURES = 3;
    private File fileDirectoryDefault;

    public Config() {
        initIfEmpty();
    }

    public boolean initIfEmpty() {
        Properties p = new Properties();
        Reader reader = null;
        File file1 = new File("empty3.config");
        if(!file1.exists()) {
            file1 = new File(System.getProperty("user.home")+File.separator+"empty3.config");
            if(!file1.exists()) {
                createConfig();
            }
        }
        try {
            reader = new FileReader(file1);
            p.load(reader);

            for (Object key : p.keySet()) {
                String value = p.get(key).toString();
                // if is file reference..
                if(key.equals("path")) {
                    fileDirectoryDefault = new File(value);
                    File path = fileDirectoryDefault.getParentFile();
                    if (path.exists() || path.mkdir()) {
                        return true;
                    } else {
                        System.err.println("Failed to make/find directory for " + path);
                        return false;
                    }
                }
            }

        } catch (IOException exp) {
            return false;
        }
        return false;
    }
    public boolean createConfig() {
        File file1 = new File(System.getProperty("user.home") + File.separator + "empty3.config");
        if(!file1.exists()) {
            try {
                file1.createNewFile();
                PrintWriter pw = new PrintWriter(file1);
                pw.println("folderoutput=c\\"+System.getProperty("user.home")+"\\EmptyCanvasTest");
                pw.println("path="+System.getProperty("user.home")+"\\EmptyCanvasTest");
                pw.close();
                return true;
            } catch (IOException ex) {
                return false;
            }
        } else return true;

    }
    public String allDefaultStrings() {
        StringBuilder sb = new StringBuilder();
        sb.append("folder.samples=");
        return sb.toString();
    }

    public File[] dirs(int type) {
        return new File[0];
    }

    public File getFileDirectoryDefault() {
        return fileDirectoryDefault;
    }

    public void setFileDirectoryDefault(File fileDirectoryDefault) {
        this.fileDirectoryDefault = fileDirectoryDefault;
    }

    public String getDefaultCodeVecMesh() {

        String s =  getFileDirectoryDefault()+File.separator+"defaultCode.calcmath";
        System.out.println(s);
        return s;
    }
}
