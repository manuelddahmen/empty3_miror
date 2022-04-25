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
    private InputLayer inputLayer;
    private List<HiddenLayer> hiddenLayerList;
    private List<OutputLayer> outputLayerList;
    private PredictedResult predictedResult;
    private TreeMap<Layer, Layer> layersOrder;

    public Net() {
        layersOrder = new TreeMap<>();
        outputLayerList = new ArrayList<>();
        hiddenLayerList = new ArrayList<>();
        trainSet = new ArrayList<>();
        RESOLUTION = 14;
    }

    public InputLayer getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(InputLayer inputLayer) {
        this.inputLayer = inputLayer;
    }

    public List<HiddenLayer> getHiddenLayerList() {
        return hiddenLayerList;
    }

    public void setHiddenLayerList(List<HiddenLayer> hiddenLayerList) {
        this.hiddenLayerList = hiddenLayerList;
    }

    public List<OutputLayer> getOutputLayerList() {
        return outputLayerList;
    }

    public void setOutputLayerList(List<OutputLayer> outputLayerList) {
        this.outputLayerList = outputLayerList;
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

    public TreeMap<Layer, Layer> getLayersOrder() {
        return layersOrder;
    }

    public void setLayersOrder(TreeMap<Layer, Layer> layersOrder) {
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
                        for (int h = 0; h < hiddenLayerList.get(0).getSizeX(); h++) {
                            hiddenLayerList.get(0).getInput()[h] += function; // ??? et le
                        }
                    }
                    error += hiddenLayerList.get(0).error();
                    hiddenLayerList.get(0).updateW();
                    for (int i = 1; i < hiddenLayerList.size() - 1; i++) {
                        function = hiddenLayerList.get(i).function();
                        for (int h = 0; h < hiddenLayerList.get(h).getSizeX(); h++) {
                            hiddenLayerList.get(i + 1).getInput()[h] += function;
                        }
                        error += hiddenLayerList.get(i).error();
                        hiddenLayerList.get(i).updateW();
                    }

                    HiddenLayer hiddenLayerOut = hiddenLayerList.get(hiddenLayerList.size() - 1);
                    OutputLayer outputLayer = outputLayerList.get(0);
                    function = hiddenLayerOut.function();
                    error += outputLayerList.get(0).error();
                    hiddenLayerList.get(0).updateW();
                    for (int h = 1; h < hiddenLayerList.get(h).getSizeX(); h++) {
                        outputLayer.getInput()[h] += function;
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
            for (HiddenLayer hiddenLayer : hiddenLayerList) {
                s.append("\t");
                s.append("").append(hiddenLayer.getW()[i]);
            }
            for (OutputLayer outputLayer : outputLayerList) {
                s.append("\t");
                s.append("").append(outputLayer.getW()[i]);
            }
            s.append("\n");
        }
        return s.toString();
    }
}
