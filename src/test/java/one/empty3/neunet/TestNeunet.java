package one.empty3.neunet;

import org.junit.Test;

public class TestNeunet {
    @Test
    public void testFunctionAnd() {
        Net<Neuron> net = new Net<>();
        net.getInputLayer().getNeurons().add(new Neuron(2));
        net.getInputLayer().getNeurons().getElem(0).setW(new double[] {0, 1});

        net.predict1();

        net.getInputLayer().getNeurons().getData1d().forEach(n -> System.out.println(n.getOutput()));
    }
}
