package one.empty3.neunet.of;

import atlasgen.Action;
import atlasgen.CsvLine;
import atlasgen.CsvReader;
import one.empty3.neunet.HiddenNeuron;
import one.empty3.neunet.Net;
import one.empty3.neunet.Neuron;
import one.empty3.neunet.OutputNeuron;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunPerceptronAndCharacterClassifiers {
    private static HashMap<String, String> options;

    public static double and(double x1, double x2) {
        Neuron l = new Neuron(2);
        l.setInput(new double[] {0, 1});
        l.setW(new double[] {0.5, 0.5});
        double b = 0.5;
        double z = l.function();
        z+=b;
        double a = z > 0?1.0:0.0;

        System.out.printf("A:"+ a);
        return a;
    }

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
        if(args.length==0) {
            Neuron l = new Neuron(2);
            l.setInput(new double[] {0, 1});
            l.setW(new double[] {0.5, 0.5});
            l.setBias(-1);
            double b = 0.5;
            double z = l.function();
            z+=b;
            double a = z > 0?1.0:0.0;

            System.out.printf("A:"+ a);

            for (int i = 0; i <4; i++) {
                double x1 = i/2;
                double x2 = i%2;
                double output = and(x1, x2);

                System.out.printf("%d%d\t%d", x1, x2, output);
            }
        }
        if (options.get("directory") != null) {
            File directory = new File(options.get("directory"));
            if (directory.exists() && directory.isDirectory()) {
                Logger.getAnonymousLogger().log(Level.INFO, "New network");
                Net net = new Net();
                //net.setInputLayer(new InputNeuron(res, res));
                net.getHiddenLayerList().add(new HiddenNeuron(res*res));
                net.getHiddenLayerList().add(new HiddenNeuron(res*res));
                net.getHiddenLayerList().add(new HiddenNeuron(res*res));
                net.getHiddenLayerList().add(new HiddenNeuron(res*res));
                net.getHiddenLayerList().add(new HiddenNeuron(res*res));
                net.getOutputLayerList().add(new OutputNeuron(res*res));
                for (File image : Objects.requireNonNull(directory.listFiles())) {
                    //if (net.getInputLayer().loadData(image)) {

                        Logger.getAnonymousLogger().log(Level.INFO, "Train network");
                        net.train();
                    //}
                }
                Logger.getAnonymousLogger().log(Level.INFO, "Result: net");

                Logger.getAnonymousLogger().log(Level.INFO, net.toString());
            } else
                Logger.getAnonymousLogger().log(Level.INFO, "Directory not found" + directory.getAbsolutePath());

        } else if (options.get("csv") != null) {
            if (options.get("csv") != null) {

                File directory = new File(options.get("csv"));
                CsvReader reader = new CsvReader(directory, "\t", "\n", false);
                if (directory.exists() && directory.isFile()) {
                    Logger.getAnonymousLogger().log(Level.INFO, "New network");
                    Net net = new Net();
                    //net.setInputLayer(new InputNeuron(res, res));
                    net.getHiddenLayerList().add(new HiddenNeuron(res* res));
                    net.getHiddenLayerList().add(new HiddenNeuron(res* res));
                    net.getHiddenLayerList().add(new HiddenNeuron(res* res));
                    net.getHiddenLayerList().add(new HiddenNeuron(res* res));
                    net.getHiddenLayerList().add(new HiddenNeuron(res* res));
                    net.getOutputLayerList().add(new OutputNeuron(res* res));
                    reader.setAction(new Action() {
                        @Override
                        public void init() {

                        }

                        @Override
                        public void processLine(CsvLine csvLine) {
                            /*
                            try {
                                if (net.getInputLayer().loadData(new File(csvLine.getValue()[1]))) {

                                    Logger.getAnonymousLogger().log(Level.INFO, "Train network");
                                    net.train();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            */
                        }
                    });
                    Logger.getAnonymousLogger().log(Level.INFO, "Result: net");

                    Logger.getAnonymousLogger().log(Level.INFO, net.toString());
                } else
                    Logger.getAnonymousLogger().log(Level.INFO, "Directory not found" + directory.getAbsolutePath());

            }

        }

    }

}
