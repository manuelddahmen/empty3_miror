package one.empty3.neunet;
public class ActivationFunction {

    public double activation(Neuron neuron) {
        return neuron.sigmoid(neuron.getInput(), neuron.getW());
    }
}