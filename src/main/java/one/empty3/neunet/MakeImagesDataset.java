package one.empty3.neunet;

import atlasgen.CsvLine;
import atlasgen.CsvReader;
import atlasgen.CsvWriter;

import java.io.File;
import java.util.Arrays;

public class MakeImagesDataset {
    private final CsvWriter csvWriter;
    CsvReader csvReader;
    long lineId = 100000;

    public MakeImagesDataset(File output, File[] directories) {
        csvWriter = new CsvWriter("\n", "\t");
        csvWriter.openFile(output);
        csvWriter.writeLine(new String[]{"LineID", "Path", "Object location", "Labels"});
        for (int i = 0; i < directories.length; i++) {
            File directory = directories[i];
            browseDirectory(directory);
        }
        CsvLine csvLine;
        csvWriter.closeFile();
    }

    private void browseDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    browseDirectory(file);
                } else {
                    if (Arrays.asList("jpg", "png").contains(
                            file.getAbsolutePath()
                                    .substring(file.getAbsolutePath().length() - 3,
                                            file.getAbsolutePath().length()).toLowerCase()))
                        csvWriter.writeLine(new String[]{"" + (lineId++), file.getAbsolutePath(),
                                "{}", "{}"});
                }
            }
        }
    }

    public static void main(String[] args) {
        new MakeImagesDataset(new File("datasetMyImageCollectionManuelDahmen.input.csv"),
                new File[]{new File("../images/s"),
                        new File("../images/m"),
                        new File("../images/batiments"),
                        new File("../images/"),
                        new File("../images/out")});
    }
}
