package one.empty3.feature.tryocr;

import one.empty3.feature.Linear;
import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ResolutionCharacter {

    private static final int ADD_POINT_TO_RANDOM_CURVE = 0;
    private static final int ADD_RANDOM_CURVE = 1;
    private static final int DEL_RANDOM_CURVE = 2;
    private static int SHAKE_SIZE = 100;
    private double dim = 14;
    private int shakeTimes;
    private PixM pixM;
    private double totalError;

    public static void main(String[] args) {
        new ResolutionCharacter().run(args);
    }

    public void addRandomCurves(State state) {
        CourbeParametriquePolynomialeBezier curve;
    }

    public void addRandomPosition(State state) {
        CourbeParametriquePolynomialeBezier curve;
    }

    public void addBeginEndPosition(State state) {
        CourbeParametriquePolynomialeBezier curve;
    }

    public void adaptOneCurve(State state) {

    }

    public void hideCurve(State state) {

    }

    public void showCurve(State state) {

    }

    public int randomLine() {
        int length = FeatureLine.featLine.length;
        return (int) (Math.random() * length);
    }

    public void chanfrein(PixM input, PixM output, State state, Color traceColor) {
        for (int i = 0; i < input.getColumns(); i++)
            for (int j = 0; j < input.getLines(); j++) {
                if (Arrays.equals(input.getValues(i, j),
                        (Lumiere.getRgb(traceColor)))) {
                    output.setValues(i, j, traceColor.getRed(), traceColor.getGreen(), traceColor.getBlue());

                } else {
                    int neighbors = 0;
                    boolean cont = true;
                    for (int n = 1; n < state.dim && !cont; n++) {
                        for (int ii = 0; ii < n && !cont; ii++)
                            for (int jj = 0; jj < n && !cont; jj++) {
                                double[] values = input.getValues(i + ii, j + jj);
                                if (Arrays.equals(input.getValues(i, j),
                                        (Lumiere.getRgb(traceColor)))) {
                                    output.setValues(i, j, 1f*traceColor.getRed() / n, 1f*traceColor.getGreen() / n, 1f*traceColor.getBlue() / n);
                                    cont = true;
                                }
                            }
                    }
                }
            }
    }

    public void run(String[] args) {
        int epochs = 100;
        int e = 1;
        BufferedImage read = ImageIO.read(new File("C:\\Users\\manue\\EmptyCanvasTest\\ocr\\AC_AC4part1dos2AC1fr3img1.jpg"));

        pixM = PixM.getPixM(read, 300);

        int step = 3;

        while (e < epochs) {
            double error0 = 0;
            totalError = 0;
            State[][] states = new State[pixM.getColumns()][pixM.getLines()];
            for (int i = 0; i < pixM.getColumns() - step; i += step)
                for (int j = 0; j < pixM.getLines() - step; j += step) {
                    states[i][j] = new State(pixM, i, j, step);
                }
            for (int i = 0; i < pixM.getColumns() - step; i += step)
                for (int j = 0; j < pixM.getLines() - step; j += step) {
                    states[i][j].currentCurves.add(
                            new CourbeParametriquePolynomialeBezier(
                                    new Point3D[]{
                                            FeatureLine.getFeatLine(
                                                    randomLine(), 0).multDot(Point3D.n(i + step / 2, j + step / 2, 0)),
                                            FeatureLine.getFeatLine(
                                                    randomLine(), 1).multDot(Point3D.n(i + step / 2, j + step / 2, 0))}));


                }
            for (int i = 0; i < pixM.getColumns() - step; i += step)
                for (int j = 0; j < pixM.getLines() - step; j += step) {
                    State state = new State(pixM, i, j, step);
                    state.previousState = states[i][j];
                    states[i][j] = state;

                    double currentError = states[i][j].computeError(states[i][j]);


                    for (int s = 0; s < SHAKE_SIZE; s++)
                        shakeCurves(states[i][j]);


                    totalError += currentError;

                    if (states[i][j].lastError > currentError) {
                        states[i][j] = states[i][j].previousState;
                    } else {
                        states[i][j] = states[i][j];
                        SHAKE_SIZE = SHAKE_SIZE / 2;
                        if (SHAKE_SIZE == 0)
                            SHAKE_SIZE = 2;
                    }
                }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(totalError - error0);
            error0 = totalError;
            e++;
        }
    }

    private void shakeCurves(State state) {
        switch ((int) Math.random() * 3) {
            case ADD_POINT_TO_RANDOM_CURVE:
                if (state.currentCurves.size() == 0)
                    state.currentCurves.add(new CourbeParametriquePolynomialeBezier());
                int i = (int) (Math.random() * state.currentCurves.size());

                int j = 0;
                if (state.currentCurves.get(i).getCoefficients().data1d.size() == 0) {
                    state.currentCurves.get(i).getCoefficients().setElem(Point3D.random(state.step).plus(state.xyz), 0);
                    j = 0;
                } else {
                    j = (int) (state.currentCurves.get(i).getCoefficients().data1d.size() * Math.random());
                    state.currentCurves.get(i).getCoefficients().setElem(Point3D.random(1.0).multDot(state.currentCurves.get(i).getCoefficients().getElem(j)), j);
                }
                break;
            case ADD_RANDOM_CURVE:
                if (Math.random() < 0.1)
                    return;
                state.currentCurves.add(
                        new CourbeParametriquePolynomialeBezier(
                                new Point3D[]{
                                        FeatureLine.getFeatLine(
                                                randomLine(), 0).multDot(Point3D.n(state.step, state.step, 0)).multDot(state.xyz),
                                        FeatureLine.getFeatLine(
                                                randomLine(), 1).multDot(Point3D.n(state.step, state.step, 0)).multDot(state.xyz)}));


                break;
            case DEL_RANDOM_CURVE:
                if (Math.random() < 0.1)
                    return;
                if (state.currentCurves.size() > 1)
                    //state.currentCurves.remove((int)(Math.random()*state.currentCurves.size()));
                    if (state.currentCurves.get(0).getCoefficients().data1d.size() > 0)
                        state.currentCurves.get(0).getCoefficients().delete(0);
                    else
                        state.currentCurves.remove(0);

                break;
        }

    }

    class StateAction {
        ArrayList<FeatureLine> beginWith;
        CourbeParametriquePolynomialeBezier curve;
        ArrayList<Point3D> moveXY;
        ArrayList<Point3D> added;
        ArrayList<Point3D> deleted;
    }

    class State {
        public Point3D xyz;
        public double step;
        ArrayList<CourbeParametriquePolynomialeBezier> resolvedCurved
                = new ArrayList<>();
        ArrayList<CourbeParametriquePolynomialeBezier> currentCurves
                = new ArrayList<>();
        double lastError = Double.NaN;
        State previousState;
        PixM input;
        PixM backgroudImage;
        Color textColor = Color.BLACK;
        int dim;

        public State(PixM image, int i, int j, int step) {
            input = image.subImage(i, j, step, step);
            backgroudImage = image.subImage(
                    (int) (Math.random() * image.getColumns()),
                    (int) (Math.random() * image.getColumns()),
                    step, step);
            xyz = Point3D.n(i + step / 2, j + step / 2, 0);
            this.step = step;
        }

        public double computeError(State state) {
            PixM pError = backgroudImage;
            PixM inputCopy = input.copy();
            state.currentCurves.forEach(courbeParametriquePolynomialeBezier ->
                    {
                        pError.plotCurve(courbeParametriquePolynomialeBezier, new TextureCol(Color.WHITE));
                    }
            );
            PixM copy = pError.copy();
            Linear linear = new Linear(inputCopy, pError, new PixM(input.getColumns(), input.getLines()));
            linear.op2d2d(new char[]{'-'}, new int[][]{{0, 1}}, new int[]{2});
            PixM diff = linear.getImages()[2];
            return diff.mean(0, 0, diff.getColumns(), diff.getLines());

        }
    }


}