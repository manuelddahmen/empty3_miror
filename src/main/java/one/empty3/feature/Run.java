/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.feature;

import java.io.File;

public class Run extends Thread {
    static String[] args = new String[]{"originalHarris_PasteBlank"};

    @Override
    public void run() {
        String settingsPropertiesPath = "sets/";
        for (int i = 0; i < args.length; i++) {

            File file = new File(settingsPropertiesPath + File.separator + (args[i]));
            if (file.exists()) {
                FTPProcessFiles.settingsPropertiesPath = file.getAbsolutePath();
                FTPProcessFiles.loadArgsProps(FTPProcessFiles.settingsPropertiesPath);
                FTPProcessFiles.defaultProcess(file);
            }
        }
    }

    public static void main(String[] args0) {
        if (args0.length > 0)
            args = args0;
        new Run().start();
    }
}
