package one.empty3.neunet;

import one.empty3.library.core.math.Matrix;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNeunet {
    private static Random random;

    @Test
    public void testCrossEntropy() {
        double[] expectedValues = new double[]{1, 0, 0, 0, 0, 1, 0, 1, 0};
        Matrix expected = new Matrix(3, 3, i -> expectedValues[i]);

        System.out.println(expected);

        Matrix actual = new Matrix(3, 3, i -> 2 * i - 4).softmax();

        System.out.println(actual);

        Matrix result = LossFunction.crossEntropy(expected, actual);

        System.out.println(result);

        actual.forEach(((row, col, index, value) -> {
            double expectedValue = value;
            if (expectedValue >= 0.9) {

            }
        }));

    }

    @Test
    public void testFunctionAnd() {
        Net<Neuron> net = new Net<>();
        net.setInputLayer(new Layer<Neuron>(2, Neuron.class));
        net.getInputLayer().getNeurons().setElem(new Neuron(2), 0);

        net.getInputLayer().getNeurons().getElem(0).setW(new double[]{0.5, 0.5});

        net.getInputLayer().getNeurons().getElem(0).setActivationMethod(ActivationMethod.MinMax01);

        net.getInputLayer().getNeurons().getElem(0).setInput(new double[]{0., 0.});
        net.computeAll();
        assertEquals(0.0, net.getInputLayer().getNeurons().getElem(0).getOutput());
        net.getInputLayer().getNeurons().getElem(0).setInput(new double[]{0., 1.});
        net.computeAll();
        assertEquals(0.0, net.getInputLayer().getNeurons().getElem(0).getOutput());

        net.getInputLayer().getNeurons().getElem(0).setInput(new double[]{1., 0.});
        net.computeAll();
        assertEquals(0.0, net.getInputLayer().getNeurons().getElem(0).getOutput());

        net.getInputLayer().getNeurons().getElem(0).setInput(new double[]{1., 1.});
        net.computeAll();
        assertEquals(1.0, net.getInputLayer().getNeurons().getElem(0).getOutput());

        net.getInputLayer().getNeurons().getData1d().forEach(n -> System.out.println(n.getOutput()));


    }

    @Test
    public void testMatrices() {
        random = new Random();

        int numberOfInputs = 7;
        int numberNeurons = 6;
        int inputSize = 4;
        Matrix inputs = new Matrix(inputSize, numberOfInputs);
        Matrix weights = new Matrix(numberNeurons, inputSize);
        Matrix biases = new Matrix(numberNeurons, 1);

        inputs.modify((row, col, value) -> random.nextDouble());
        weights.modify((row, col, value) -> random.nextGaussian());
        biases.modify((row, col, value) -> random.nextGaussian());

        Matrix result1 = weights.multiply(inputs).modify((row, col, value) -> value + biases.get(row));
        Matrix result2 = weights.multiply(inputs).modify((row, col, value) -> value + biases.get(row))
                .modify((index, value) -> value > 0 ? value : 0.0);

        System.out.println(inputs);
        System.out.println(weights);
        System.out.println(biases);
        System.out.println(result1);
        System.out.println(result2);

        AtomicReference<Double> softmax = new AtomicReference<>(0.0);
        result2.forEach((index, value) -> {
            softmax.updateAndGet(v -> (double) (v + Math.exp(result2.get(index))));
            //assertTrue(value-r1<Matrix.DOUBLE_MIN);
        });
        result2.modify((index, value) -> Math.exp(value) / softmax.get());
        System.out.println(result2);
    }

    @Test
    public void testMatrices1() {
        random = new Random();

        int numberOfInputs = 7;
        int numberNeurons = 6;
        int inputSize = 4;
        Matrix inputs = new Matrix(inputSize, numberOfInputs);
        Matrix weights = new Matrix(numberNeurons, inputSize);
        Matrix biases = new Matrix(numberNeurons, 1);

        inputs.modify((row, col, value) -> random.nextDouble());
        weights.modify((row, col, value) -> random.nextGaussian());
        biases.modify((row, col, value) -> random.nextGaussian());

        Matrix result1 = weights.multiply(inputs).modify((row, col, value) -> value + biases.get(row));
        Matrix result2 = weights.multiply(inputs).modify((row, col, value) -> value + biases.get(row))
                .modify((index, value) -> value > 0 ? value : 0.0);

        System.out.println(inputs);
        System.out.println(weights);
        System.out.println(biases);
        System.out.println(result1);
        System.out.println(result2);

        AtomicReference<Double> softmax = new AtomicReference<>(0.0);
        result2.forEach((index, value) -> {
            softmax.updateAndGet(v -> (double) (v + Math.exp(result2.get(index))));
            //assertTrue(value-r1<Matrix.DOUBLE_MIN);
        });
        result2.modify((index, value) -> Math.exp(value) / softmax.get());
        System.out.println(result2);
    }
}
