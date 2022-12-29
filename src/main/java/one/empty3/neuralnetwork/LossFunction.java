/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.neuralnetwork;

import one.empty3.library.core.math.Matrix;

public class LossFunction {
    public static Matrix crossEntropy(Matrix expected, Matrix actual) {
        Matrix result = actual.apply((index, value) -> {
            return -expected.get(index) * Math.log(value);
        }).sumColumns();

        return result;
    }
}
