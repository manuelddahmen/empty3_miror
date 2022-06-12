package one.empty3.neunet;

public class HiddenNeuron extends Neuron {
    private Dimension dimensionZ;

    public HiddenNeuron(int sizeX, int sizeY) {
        super(sizeX, sizeY);
    }

    /***
     * Brut calculus
     * @param dw
     * @return
     */
    public double[] gradient(double dw) {
        double h = error();
        double[] g = new double[getW().length];
        for (int i = 0; i < getW().length; i++) {
            double[] wa = getW().clone();
            wa[i] = getW()[i] + dw;
            double a = error(wa);
            g[i] = (a - h) / dw;
        }
        return g;
    }

    public double[] updateDescend(double e, double dw) {
        double[] wa = getW().clone();
        double[] g = gradient(dw);
        for (int i = 0; i < getW().length; i++) {
            wa[i] = getW()[i] - e * g[i];
        }
        return wa;
    }

    public double[] learn(double dw, double e, double n) {
        for (int i = 0; i < n; i++) {
            setW(updateDescend(e, dw));
            System.out.println(error());
        }
        return getW();
    }
}
