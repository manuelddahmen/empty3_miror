package one.empty3.neunet;

import one.empty3.library.core.math.Matrix;
import one.empty3.neunet.ActivationFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNeunet {
    @Test
    public void testFunctionAnd() {
        Net<Neuron> net = new Net<>();
        net.setInputLayer(new Layer<Neuron>(2, Neuron.class));
        net.getInputLayer().getNeurons().setElem(new Neuron(2), 0);

        net.getInputLayer().getNeurons().getElem(0).setW(new double[] {0.5, 0.5});

        net.getInputLayer().getNeurons().getElem(0).setActivationMethod(ActivationMethod.MinMax01);

        net.getInputLayer().getNeurons().getElem(0).setInput(new double[] {0.,0.});
        net.computeAll();
        assertEquals(0.0, net.getInputLayer().getNeurons().getElem(0).getOutput());
        net.getInputLayer().getNeurons().getElem(0).setInput(new double[] {0.,1.});
        net.computeAll();
        assertEquals(0.0, net.getInputLayer().getNeurons().getElem(0).getOutput());

        net.getInputLayer().getNeurons().getElem(0).setInput(new double[] {1.,0.});
        net.computeAll();
        assertEquals(0.0, net.getInputLayer().getNeurons().getElem(0).getOutput());

        net.getInputLayer().getNeurons().getElem(0).setInput(new double[] {1.,1.});
        net.computeAll();
        assertEquals(1.0, net.getInputLayer().getNeurons().getElem(0).getOutput());

        net.getInputLayer().getNeurons().getData1d().forEach(n -> System.out.println(n.getOutput()));




    }
    @Test
    public void testMatrices() {
        Matrix inputs = new Matrix(3, 3);
        Matrix weights = new Matrix(3, 3);
        Matrix biases = new Matrix(3, 1);


        Matrix modify = inputs.multiply(weights).modify((row, col, value) -> value + biases.get(row, 1));

        System.out.println(inputs);
        System.out.println(weights);
        System.out.println(biases);
        System.out.println(modify);
    }
}
