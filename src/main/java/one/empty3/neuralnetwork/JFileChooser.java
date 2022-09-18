package one.empty3.neuralnetwork;

import java.io.File;

public class JFileChooser {
    File[] files;

    public JFileChooser() {
        javax.swing.JFileChooser jFileChooser = new javax.swing.JFileChooser();
        jFileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showDialog(null, "Choose file");
        if (jFileChooser.getSelectedFiles() != null) {
            this.files = jFileChooser.getSelectedFiles();
        }

    }


}