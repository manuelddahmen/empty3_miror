package one.empty3.neunet;

import one.empty3.library.Point3D;

public class Neuron implements Comparable {
    private Net network;
    private int sizeX;
    private int sizeY;
    private int comps;
    private double[] w;
    protected double[] input;
    protected double[] output;
    protected double bias;
    public Neuron(int sizeX, int sizeY) {
        comps = 3;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        w = new double[sizeX * sizeY * comps];
        input = new double[sizeX * sizeY * comps];
        output = new double[sizeX * sizeY * comps];
        initW(1.0);
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double[] getInput() {
        return input;
    }

    public void setInput(double[] input) {
        this.input = input;
    }

    public double[] getOutput() {
        return output;
    }

    public void setOutput(double[] output) {
        this.output = output;
    }

    public Net getNetwork() {
        return network;
    }

    public void setNetwork(Net network) {
        this.network = network;
    }

    public int getComps() {
        return comps;
    }

    public void setComps(int comps) {
        this.comps = comps;
    }

    public double function() {
        return dot(input, w);
    }

    private double dot(double[] inputs, double[] w) {
        // Check colors components
        double res = 0.0;
        for (int i = 0; i < w.length; i++) {
            res += inputs[i] * w[i];
        }
        return res;
    }

    /***
     * Not implemented yet
     * @return error double
     */
    public double error() {
        double e = 0.0;
        for (int i = 0; i < w.length; i++) {
            e = e + Math.pow(output[i] - input[i] * w[i], 2);

        }
        return e;
    }

    /***
     * Not implemented yet
     * @return error double
     */
    public double error(double[] w) {
        double e = 0.0;
        for (int i = 0; i < w.length; i++) {
            e = e + Math.pow(output[i] - input[i] * w[i], 2);

        }
        return e;
    }

    /***
     * Not implemented yet
     */
    public void updateW() {
        double e = error();
        double w1 = 0.0, w2 = 0.0;
        for (int i = 0; i < w.length; i++) {
            w[i] = w[i] + e * (function() - network.getPredictedResult().getOutputValues()) * input[i];
        }
        return;
    }

    public double sigmoid(double[] x, double[] w) {
        double z = dot(x, w);
        return 1. / (1. + Math.exp(-z));
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Neuron && o.getClass().equals(getClass())) {
            Neuron o1 = (Neuron) o;
            if (o1.w.equals(w))
                return 0;
        }
        return -1;
    }

    public void initW(double random) {
        for (int i = 0; i < w.length; i++) {
            w[i] = (Math.random() - 0.5) * 2;
        }
    }
    public double activation() {
        return function()+bias >0?1:0;
    }
    public int ordPix(int x, int y, int comp) {
        return comp * sizeX * sizeY + sizeX * y + x;
    }

    public Point3D getPixelColorComponents(int i, int j) {
        return new Point3D(input[ordPix(i, j, 0)],
                input[ordPix(i, j, 1)], input[ordPix(i, j, 2)]);
    }


    public void setBias(double bias) {
        bias = bias;
    }

    public double getBias() {
        return bias;
    }
}
