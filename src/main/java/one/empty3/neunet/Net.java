package one.empty3.neunet;

import one.empty3.feature.PixM;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Net {
    private static double RESOLUTION = 14;
    private List<File> trainSet;
    private InputNeuron inputLayer;
    private List<HiddenNeuron> hiddenNeuronList;
    private List<OutputNeuron> outputNeuronList;
    private PredictedResult predictedResult;
    private TreeMap<Neuron, Neuron> layersOrder;

    public Net() {
        layersOrder = new TreeMap<>();
        outputNeuronList = new ArrayList<>();
        hiddenNeuronList = new ArrayList<>();
        trainSet = new ArrayList<>();
        RESOLUTION = 14;
    }

    public InputNeuron getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(InputNeuron inputLayer) {
        this.inputLayer = inputLayer;
    }

    public List<HiddenNeuron> getHiddenLayerList() {
        return hiddenNeuronList;
    }

    public void setHiddenLayerList(List<HiddenNeuron> hiddenNeuronList) {
        this.hiddenNeuronList = hiddenNeuronList;
    }

    public List<OutputNeuron> getOutputLayerList() {
        return outputNeuronList;
    }

    public void setOutputLayerList(List<OutputNeuron> outputNeuronList) {
        this.outputNeuronList = outputNeuronList;
    }

    public List<File> getTrainSet() {
        return trainSet;
    }

    public void setTrainSet(List<File> trainSet) {
        this.trainSet = trainSet;
    }

    public PredictedResult getPredictedResult() {
        return predictedResult;
    }

    public void setPredictedResult(PredictedResult predictedResult) {
        this.predictedResult = predictedResult;
    }

    public static double getRESOLUTION() {
        return RESOLUTION;
    }

    public static void setRESOLUTION(double RESOLUTION) {
        Net.RESOLUTION = RESOLUTION;
    }

    public TreeMap<Neuron, Neuron> getLayersOrder() {
        return layersOrder;
    }

    public void setLayersOrder(TreeMap<Neuron, Neuron> layersOrder) {
        this.layersOrder = layersOrder;
    }

    public void loadModel(File model) {

    }


    public void train() {
        int maxIterations = 1000;
        int t = 0;
        try {

            double error = 0.0;

            while (t < maxIterations) {
                for (int n = 0; n < trainSet.size(); n++) {
                    PixM pixM = PixM.getPixM(ImageIO.read(trainSet.get(n)), RESOLUTION);
                    inputLayer.setInputImage(pixM, null);
                    double function = inputLayer.function();
                    error += inputLayer.error();
                    inputLayer.updateW();
                    for (int i = 0; i < inputLayer.getSizeX(); i++) {
                        for (int h = 0; h < hiddenNeuronList.get(0).getSizeX(); h++) {
                            hiddenNeuronList.get(0).getInput()[h] += function; // ??? et le
                        }
                    }
                    error += hiddenNeuronList.get(0).error();
                    hiddenNeuronList.get(0).updateW();
                    for (int i = 1; i < hiddenNeuronList.size() - 1; i++) {
                        function = hiddenNeuronList.get(i).function();
                        for (int h = 0; h < hiddenNeuronList.get(h).getSizeX(); h++) {
                            hiddenNeuronList.get(i + 1).getInput()[h] += function;
                        }
                        error += hiddenNeuronList.get(i).error();
                        hiddenNeuronList.get(i).updateW();
                    }

                    HiddenNeuron hiddenNeuronOut = hiddenNeuronList.get(hiddenNeuronList.size() - 1);
                    OutputNeuron outputNeuron = outputNeuronList.get(0);
                    function = hiddenNeuronOut.function();
                    error += outputNeuronList.get(0).error();
                    hiddenNeuronList.get(0).updateW();
                    for (int h = 1; h < hiddenNeuronList.get(h).getSizeX(); h++) {
                        outputNeuron.getInput()[h] += function;
                    }

                }
                t++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < RESOLUTION * RESOLUTION * 3; i++) {
            s.append("\t");
            s.append("").append(inputLayer.getW()[i]);
            for (HiddenNeuron hiddenNeuron : hiddenNeuronList) {
                s.append("\t");
                s.append("").append(hiddenNeuron.getW()[i]);
            }
            for (OutputNeuron outputNeuron : outputNeuronList) {
                s.append("\t");
                s.append("").append(outputNeuron.getW()[i]);
            }
            s.append("\n");
        }
        return s.toString();
    }
}
