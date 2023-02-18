/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.feature;// Java program to perform a 2D FFT Inplace Given a Complex
// 2D Array

// Declare the needed libraries

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;
import one.empty3.library.core.math.Matrix;
import one.empty3.library.core.nurbs.F;
import one.empty3.library.core.nurbs.Fct1D_1D;

import java.io.File;
import java.io.IOException;

public class GFG extends ProcessFile {
    Fct1D_1D fct1D1D;
    // Now by taking the discrete function
    // This is the declaration of the function
    // This function includes 4 parameters
    // The parameters are the 4 matrices.
    static void discrete(double[][] input,
                         double[][] realOut,
                         double[][] imagOut) {

        // Height is the variable of data type int
        // the length of the input variable is stored in
        // variable height
        int height = input.length;

        // The input of the first index length is stored in
        // variable width
        int width = input[0].length;

        // Iterating the input till height stored in
        // variable y
        for (int y = 0; y < height; y++) {

            // Taking the input iterating till width in
            // variable x
            for (int x = 0; x < width; x++) {

                // Taking another variable y1 which will be
                // the continuation of
                // the variable y
                // This y1 will be iterating till height
                // This index of the variable starts at 0
                for (int y1 = 0; y1 < height; y1++) {

                    // This index x1 iterates till width
                    // This x1 is continuation of x
                    // The variables y1 and x1 are the
                    // continuation of summable of x and y
                    for (int x1 = 0; x1 < width; x1++) {

                        // realOut is the variable which
                        // lets us know the real output as
                        // we do the summation of exponential
                        // signal
                        // we get cos as real term and sin
                        // as imaginary term
                        // so taking the consideration of
                        // above properties we write the
                        // formula of real as
                        // summing till x and y and
                        // multiplying it with cos2pie
                        // and then dividing it with width
                        // *height gives us the real term
                        realOut[y][x]
                                += (input[y1][x1]
                                * Math.cos(
                                2 * Math.PI
                                        * ((1.0 * x * x1
                                        / width)
                                        + (1.0 * y * y1
                                        / height))))
                                / Math.sqrt(width * height);

                        // Now imagOut is the imaginary term
                        // That is the sine term
                        // This sine term can be obtained
                        // using sin2pie and then we divide
                        // it using width*height The
                        // formulae is same as real

                        imagOut[y][x]
                                -= (input[y1][x1]
                                * Math.sin(
                                2 * Math.PI
                                        * ((1.0 * x * x1
                                        / width)
                                        + (1.0 * y * y1
                                        / height))))
                                / Math.sqrt(width * height);
                    }

                    // Now we will print the value of
                    // realOut and imaginary outputn The
                    // ppoutput of imaginary output will end
                    // with value 'i'.
                    //System.out.println(realOut[y][x] + " +" + imagOut[y][x] + "i");
                }
            }
        }

    }

    /**
     * "Calculate the Fourier series coefficients up to the Nth harmonic"
     *
     * @param N
     * @return
     */
    public double[][] fourierSeries(double [] period, int N) {
        double[][] result = new double[N][2];
        int T = period.length;
        double [] t = new double[T];
        for(int i=0; i<T; i++)
            t[i] = i;
        double an=0, bn=0;
        for (int n = 0; n < N + 1; n++) {
            an += 2.0 / T * (period[n] * Math.cos(2 * Math.PI * n * t[n] / T));
            bn += 2.0 / T * (period[n] * Math.sin(2 * Math.PI * n * t[n] / T));
            result[n][0] = an;
            result[n][1] = bn;
        }
        return result;
    }

    public double reconstruct(int P, double[][]  anbn) {
        double result = 0.0;
        double[] t = new double[P];
        double a = 0, b;
        for (int n = 0; n < anbn.length; n++) {
            a = anbn[n][0];
            b = anbn[n][1];
            if (n == 0) {
                a = a / 2;
            }
            result = result + a * Math.cos(2 * Math.PI * n * t[n] / P)
                    + b * Math.sin(2 * Math.PI * n * t[n] / P);
        }
        return result;
    }
    @Override
    public boolean process(File in, File out) {
        PixM pix = new PixM(ImageIO.read(in));

        int sizeT = Math.max(pix.getColumns(), maxRes);
        int n = 5;
        double [] points = new double[sizeT];
        double[] t_period = new double[sizeT];


        for(int x=0; x<pix.getColumns(); x++) {
            for(int y=0; x<pix.getLines(); y++) {
                if(pix.luminance(x, y)>=0.5) {
                    t_period[x] = x;
                    points[x] = y;
                }
            }
        }



        double[] F = new double[sizeT];

        System.arraycopy(points, 0, F, 0, t_period.length);

        for(int i=0; i<t_period.length; i++) {

            double[][] anbn = fourierSeries(F, n);

            double reconstruct = reconstruct(t_period.length, anbn);

            pix.setValues(i, (int)reconstruct, 1, 1, 1);
        }

        try {
            ImageIO.write(pix.normalize(0,1).getImage(), "jpg", out);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean process2(File in, File out) {
        PixM inPix = PixM.getPixM(ImageIO.read(in), maxRes);

        int n = Math.max(inPix.getColumns(), inPix.getLines());

        double[][] inPix2 = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                inPix2[i][j] = inPix.luminance(i, j);
            }


        // Declaring the matrices in double datatype
        // Declaring the input variable where we take in the
        // input
        double[][] input = new double[n][n];

        input = inPix2;

        // Taking the matrices for real value
        double[][] realOut = new double[n][n];

        // Taking the matrices for imaginary output
        double[][] imagOut = new double[n][n];

        // Calling the function discrete
        discrete(input, realOut, imagOut);

        Matrix pixM1 = new Matrix(n, n, 1);
        Matrix pixM2 = new Matrix(n, n, 1);
        pixM1.forEach((row, col, index, value) -> pixM1.set(row, col, realOut[row][col]));
        pixM2.forEach((row, col, index, value) -> pixM2.set(row, col, imagOut[row][col]));

        Matrix multiply = pixM1.multiply(pixM2);

        try {
            ImageIO.write(multiply.normalize(0, 1).getImage(), "jpg", out);


            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
