package one.empty3.library.core.math;

import java.util.Arrays;

public class Matrix {
    private static final String NUMBER_FORMAT = "%+12.5f";
    private static final double TOLERANCE = 0.000001;
    private final int cols;
    private final int rows;
    private double[] a;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        a = new double[rows * cols];
    }

    public Matrix(int rows, int cols, Producer producer) {
        this(rows, cols);
        for (int i = 0; i < a.length; i++) {
            a[i] = producer.produce(i);

        }
    }

    public Matrix apply(ValueProducer producer) {
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < a.length; i++) {
            result.a[i] = producer.produce(i, a[i]);
        }

        return result;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(String.format(NUMBER_FORMAT, a[index]));
                sb.append("\t");
                index++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Matrix multiply(Matrix m) {
        Matrix result = new Matrix(rows, m.cols);

        assert cols == m.rows : "Cannot multipy";
        int index = 0;
        for (int row = 0; row < result.rows; row++) {
            for (int col = 0; col < result.cols; col++) {
                result.a[index] = 0.0;
                for (int k = 0; k < cols; k++) {
                    result.a[row * result.cols + col] += get(row, k) * get(k, col);
                }
                index++;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix matrix = (Matrix) o;

        if (cols != matrix.cols) return false;
        if (rows != matrix.rows) return false;
        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i] - matrix.a[i]) > TOLERANCE)
                return false;

        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = cols;
        result = 31 * result + rows;
        result = 31 * result + Arrays.hashCode(a);
        return result;
    }

    public double get(int row, int col) {
        return a[row*cols+col];
    }
    public void set(int row, int col, double value) {
        a[row*cols+col] = value;
    }


    public interface Producer {
        double produce(int index);
    }

    public interface ValueProducer {
        double produce(int index, double value);
    }
    public interface RowColProducer {
        double produce(int row, int col, double value);
    }

    public Matrix modify(RowColProducer producer) {
        int index=0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                a[index] = producer.produce(row, col, a[index]);
                index++;
            }
        }
        return this;
    }
}
