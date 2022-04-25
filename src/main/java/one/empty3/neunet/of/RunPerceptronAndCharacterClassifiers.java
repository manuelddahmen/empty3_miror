package one.empty3.neunet.of;

import atlasgen.Action;
import atlasgen.CsvLine;
import atlasgen.CsvReader;
import one.empty3.neunet.HiddenLayer;
import one.empty3.neunet.InputLayer;
import one.empty3.neunet.Net;
import one.empty3.neunet.OutputLayer;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class RunPerceptronAndCharacterClassifiers {
    private static HashMap<String, String> options;

    public static void main(String[] args) throws Exception {
        int res = 14;
        options = new HashMap<>();
        for (String s : args) {
            String option;
            String value;
            if (s != null) {
                String[] split = s.split("=");
                if (split.length == 2 && split[0] != null && split[1] != null) {
                    option = split[0].substring(2);
                    value = split[1];
                    options.put(option, value);
                }
            }
        }
        if (options.get("directory") != null) {
            File directory = new File(options.get("directory"));
            if (directory.exists() && directory.isDirectory()) {
                System.out.println("New network");
                Net net = new Net();
                net.setInputLayer(new InputLayer(res, res));
                net.getHiddenLayerList().add(new HiddenLayer(res, res));
                net.getHiddenLayerList().add(new HiddenLayer(res, res));
                net.getHiddenLayerList().add(new HiddenLayer(res, res));
                net.getHiddenLayerList().add(new HiddenLayer(res, res));
                net.getHiddenLayerList().add(new HiddenLayer(res, res));
                net.getOutputLayerList().add(new OutputLayer(res, res));
                for (File image : Objects.requireNonNull(directory.listFiles())) {
                    if (net.getInputLayer().loadData(image)) {

                        System.out.println("Train network");
                        net.train();
                    }
                }
                System.out.println("Result: net");

                System.out.println(net.toString());
            } else
                System.out.println("Directory not found" + directory.getAbsolutePath());

        } else if (options.get("csv") != null) {
            if (options.get("csv") != null) {

                File directory = new File(options.get("csv"));
                CsvReader reader = new CsvReader(directory, "\t", "\n", false);
                if (directory.exists() && directory.isFile()) {
                    System.out.println("New network");
                    Net net = new Net();
                    net.setInputLayer(new InputLayer(res, res));
                    net.getHiddenLayerList().add(new HiddenLayer(res, res));
                    net.getHiddenLayerList().add(new HiddenLayer(res, res));
                    net.getHiddenLayerList().add(new HiddenLayer(res, res));
                    net.getHiddenLayerList().add(new HiddenLayer(res, res));
                    net.getHiddenLayerList().add(new HiddenLayer(res, res));
                    net.getOutputLayerList().add(new OutputLayer(res, res));
                    reader.setAction(new Action() {
                        @Override
                        public void init() {

                        }

                        @Override
                        public void processLine(CsvLine csvLine) {
                            try {
                                if (net.getInputLayer().loadData(new File(csvLine.getValue()[1]))) {

                                    System.out.println("Train network");
                                    net.train();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    System.out.println("Result: net");

                    System.out.println(net.toString());
                } else
                    System.out.println("Directory not found" + directory.getAbsolutePath());

            }

        }

    }
}
