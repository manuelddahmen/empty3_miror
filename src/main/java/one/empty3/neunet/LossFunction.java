package one.empty3.neunet;

import one.empty3.library.core.math.Matrix;

public class LossFunction {
    public static Matrix crossEntropy(Matrix expected, Matrix actual) {
        Matrix result = actual.apply((index, value) -> {
            return -expected.get(index) * Math.log(value);
        });
        Matrix result2 = result.sumColumns();

        result.forEach((row, col, index, value) -> {
            result.set(row, col,value/result2.get(row));
        });

        return result;
    }
}
