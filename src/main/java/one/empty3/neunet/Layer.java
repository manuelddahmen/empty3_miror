package one.empty3.neunet;

import one.empty3.library.StructureMatrix;


public class Layer <T extends Neuron>{
    private int size;
    private int lengthX;
    private StructureMatrix<T> neurons;
    private int dimensions;

    public Layer(int lengthX, int size, Class<T> t) {
        this.size = size;
        this.lengthX = lengthX;
        neurons = new StructureMatrix<>(1, t);
        for (int i = 0; i < size; i++) {
            try {
                neurons.setElem(t.newInstance(), i);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Layer(int lengthX, Class<T> t) {
        neurons = new StructureMatrix<>(2, t);
        lengthX = lengthX;
    }

    public Layer(StructureMatrix<T> neurons) {
        this.neurons = neurons;
    }

    public StructureMatrix<T> getNeurons() {
        return neurons;
    }

    public void setNeurons(StructureMatrix<T> neurons) {
        this.neurons = neurons;
    }
}
