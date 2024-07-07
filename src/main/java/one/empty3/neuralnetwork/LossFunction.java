/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
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

    public static Matrix crossEntropy2(Matrix expected, Matrix actual) {
        Matrix result = actual.apply((index, value) -> {
            // Additional check to prevent NaN values when value = 0.0 or 1.0
            double epsilon = 1e-15;
            value = Math.max(Math.min(value, 1.0 - epsilon), epsilon);

            return -expected.get(index) * Math.log(value);
        }).sumColumns();

        return result;
    }

    public static Matrix crossEntropy2arrays(double[] expected, double[] actual, int dimX, int dimY) {
        Matrix actualMatrix = new Matrix(dimX, dimY);
        Matrix expectedMatrix = new Matrix(dimX, dimY);

        Matrix result = new Matrix(dimX, dimY, new Matrix.Producer() {
            @Override
            public double produce(int index) {
                // Additional check to prevent NaN values when value = 0.0 or 1.0
                double value = actual[index];
                double epsilon = 1e-15;
                value = Math.max(Math.min(value, 1.0 - epsilon), epsilon);

                return -expectedMatrix.get(index) * Math.log(value);
            }
        });
        return result;
    }
}
