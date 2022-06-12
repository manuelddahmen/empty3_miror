package one.empty3.neunet;

import one.empty3.library.StructureMatrix;

public class Layer {
    private StructureMatrix<Neuron> neurons;

    public Layer(StructureMatrix<Neuron> neurons) {
        this.neurons = neurons;
    }

    public StructureMatrix<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(StructureMatrix<Neuron> neurons) {
        this.neurons = neurons;
    }
}
