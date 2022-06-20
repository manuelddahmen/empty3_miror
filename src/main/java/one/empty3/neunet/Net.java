package one.empty3.neunet;

import one.empty3.feature.PixM;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;

public class Net<T extends Neuron> {
    private static double RESOLUTION = 14;
    private List<File> trainSet;
    private Layer<T> inputLayer;
    private List<Layer<T>> hiddenLayerList;
    private List<Layer<OutputNeuron>> outputLayerList;
    private PredictedResult<T> predictedResult;
    private TreeMap<Neuron, Neuron> layersOrder;

    public Net() {
        layersOrder = new TreeMap<>();
        outputLayerList = new ArrayList<>();
        hiddenLayerList = new ArrayList<>();
        trainSet = new ArrayList<>();
        RESOLUTION = 14;
    }

    public Layer<T> getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(Layer inputLayer) {
        this.inputLayer = inputLayer;
    }

    public List<Layer<T>> getHiddenLayerList() {
        return hiddenLayerList;
    }

    public void setHiddenLayerList(List<Layer<T>> hiddenNeuronList) {
        this.hiddenLayerList = hiddenNeuronList;
    }

    public List<Layer<OutputNeuron>> getOutputLayerList() {
        return outputLayerList;
    }

    public void setOutputLayerList(List<Layer<OutputNeuron>> outputNeuronList) {
        this.outputLayerList = outputNeuronList;
    }

    public List<File> getTrainSet() {
        return trainSet;
    }

    public void setTrainSet(List<File> trainSet) {
        this.trainSet = trainSet;
    }

    public PredictedResult<T> getPredictedResult() {
        return predictedResult;
    }

    public void setPredictedResult(PredictedResult<T> predictedResult) {
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


    public void train() throws IOException {
        int maxIterations = 1000;
        int t = 0;
            double errorGlobal = 0.0;

            while (t < maxIterations) {
                for (int n = 0; n < trainSet.size(); n++) {
                    PixM pixM = PixM.getPixM(ImageIO.read(trainSet.get(n)), RESOLUTION);
                    inputLayer.getNeurons().data2d.forEach(new Consumer<List<T>>() {
                        @Override
                        public void accept(List<T> ts) {
                            ts.forEach(new Consumer<T>() {
                                @Override
                                public void accept(T t) {
                                    t.setInputImage(pixM);
                                }
                            });
                        }
                    });
                    final double[] error = {0};

                    final double[] function = {0};
                    inputLayer.getNeurons().data2d.forEach(new Consumer<List<T>>() {
                        @Override
                        public void accept(List<T> ts) {
                            ts.forEach(new Consumer<T>() {
                                @Override
                                public void accept(T t) {
                                    function[0] += t.function();
                                    error[0] += t.error();
                                    t.updateW();
                                }
                            });
                        }
                    });
                    // Compute Xs through network

                }
                t++;


            }
     }

}
