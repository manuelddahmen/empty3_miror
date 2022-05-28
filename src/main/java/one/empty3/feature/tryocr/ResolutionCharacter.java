package one.empty3.feature.tryocr;

import one.empty3.feature.Linear;
import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.ColorTexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class ResolutionCharacter {

    private static final int ADD_POINT_TO_RANDOM_CURVE = 0;
    private static final int ADD_RANDOM_CURVE = 1;
    private static final int DEL_RANDOM_CURVE = 2;
    private static final int ADD_CURVES = 3;
    private static final int MAX_ERRORS_ADD_CURVES = 4;
    private static final int MOVE_POINTS = 0;
    private static int SHAKE_SIZE = 100;
    private double dim = 14;
    private int shakeTimes;
    private PixM pixM;
    private double totalError;
    private int numCurves;
    private double errorDiff = 0.0;

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

    public void chanfrein(PixM input, PixM output, Color traceColor) {
        for (int i = 0; i < input.getColumns(); i++)
            for (int j = 0; j < input.getLines(); j++) {
                if (Arrays.equals(input.getValues(i, j),
                        (Lumiere.getRgb(traceColor)))) {
                    output.setValues(i, j, traceColor.getRed(), traceColor.getGreen(), traceColor.getBlue());

                } else {
                    int neighbors = 0;
                    boolean cont = true;
                    double distMax = (Math.max(input.getColumns(), input.getLines()));
                    for (int n = 1; n < distMax && cont; n++) {
                        for (int ii = 0; ii < n && cont; ii++)
                            for (int jj = 0; jj < n && cont; jj++) {
                                double[] values = input.getValues(i + ii, j + jj);
                                if (Arrays.equals(input.getValues(i, j),
                                        (Lumiere.getRgb(traceColor)))) {
                                    output.setValues(i, j, 1f * traceColor.getRed() / n, 1f * traceColor.getGreen() / n, 1f * traceColor.getBlue() / n);
                                    cont = true;
                                }
                            }
                    }
                }
            }
    }

    public void run(String[] args) {
        int epochs = 10000;
        int e = 1;
        BufferedImage read = ImageIO.read(new File("C:\\Users\\manue\\EmptyCanvasTest\\ocr\\AC_AC4part1dos2AC1fr3img1.jpg"));

        pixM = PixM.getPixM(read, 600);

        int step = 24;

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

        while (e < epochs) {
            double error0 = 0;
            totalError = 0;
            numCurves = 0;
            double currentError = 0;
            for (int i = 0; i < pixM.getColumns() - step; i += step)
                for (int j = 0; j < pixM.getLines() - step; j += step) {
             /*       State state = new State(pixM, i, j, step);
                    state.previousState = states[i][j];
                    states[i][j] = state;
*/


//                    if (states[i][j].lastErrors[ADD_CURVES] > MAX_ERRORS_ADD_CURVES
//                            && states[i][j].currentCurves.size() > 0) {
                    states[i][j].lastError = states[i][j].currentError;

                    //states[i][j].currentCurves.remove(states[i][j].currentCurves.get(0));
                    for (int s = 0; s < SHAKE_SIZE; s++) {
                        State states1 = states[i][j].copy();
                        shakeCurves(states1, MOVE_POINTS);
                        if ((currentError=states1.computeError()) < states[i][j].computeError()) {
                            states[i][j] = states1;
                        }

                    }
                    if (currentError > states[i][j].currentError) {
                        /// Errerur grandit
                        states[i][j] = states[i][j].previousState==null?new State(pixM, i, j, step):
                                states[i][j].previousState;
                    } else {
                        SHAKE_SIZE = SHAKE_SIZE / 2;
                        if (SHAKE_SIZE == 0)
                            SHAKE_SIZE = 2;
                    }

                    currentError += (states[i][j].currentError = states[i][j].computeError());
                    totalError += currentError;


                }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Taile de la trame :" + states.length + " , " + states[0].length);
            System.out.println("Erreur totale :" + (totalError - error0));
            errorDiff = totalError - error0;
            System.out.println("Nombre de courbes :" + numCurves);
            numCurves = 0;
            error0 = totalError;
            e++;
        }
            PixM globalOutput = pixM.copy();
        ColorTexture texture = new ColorTexture(Color.BLACK);
        for (int i = 0; i < pixM.getColumns() - step; i += step)
                for (int j = 0; j < pixM.getLines() - step; j += step) {
                    if(states[i][j]!=null) {
                        states[i][j].currentCurves.forEach(courbeParametriquePolynomialeBezier -> {
                            globalOutput.plotCurve(courbeParametriquePolynomialeBezier, texture);
                        });
                    }
                }
        try {
            ImageIO.write(globalOutput.getImage(), "jpg", new File("Output.test.jpg"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void shakeCurves(State state, int choice) {
        switch (choice) {
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
                    if (j < 4)
                        state.currentCurves.get(i).getCoefficients().data1d.set(j, Point3D.random(state.step).plus(state.xyz) );
                }
                break;
            case ADD_RANDOM_CURVE:
                //if (Math.random() < 0.1)
                //    return;
                if (state.currentCurves.size() > 5)
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
                //if (Math.random() > 0.1)
                //    return;
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
        public double currentError = 0.0;
        public int[] lastErrors = new int[3];
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
            xyz = Point3D.n(i + step / 2., j + step / 2., 0.);
            this.step = step;
        }

        public double computeError() {
            State state = this;
            PixM pError = state.backgroudImage;
            PixM inputCopy = input.copy();
            state.currentCurves.forEach(courbeParametriquePolynomialeBezier ->
                    {
                        pError.plotCurve(courbeParametriquePolynomialeBezier, new TextureCol(Color.BLACK));
                        numCurves++;
                    }
            );
            PixM copy = pError.copy();
            Linear linear = new Linear(inputCopy, pError, new PixM(input.getColumns(), input.getLines()));
            linear.op2d2d(new char[]{'-'}, new int[][]{{1, 0}}, new int[]{2});
            PixM diff = linear.getImages()[2];
            return diff.mean(0, 0, diff.getColumns(), diff.getLines())
                    / diff.getLines() / diff.getLines();

        }

        public State copy() {
            State copy = new State(this.input, (int)(double)this.xyz.get(0),
                    (int)(double)this.xyz.get(1), (int)(double)this.step);
            copy.currentError = currentError;
            copy.currentCurves = (ArrayList<CourbeParametriquePolynomialeBezier>) this.currentCurves.clone();
            copy.lastError = lastError;
            copy.step = step;
            copy.xyz = xyz;
            copy.backgroudImage = backgroudImage;
            copy.input = input;
            copy.dim = dim;
            copy.lastErrors = lastErrors;
            copy.textColor = textColor;
            copy.previousState = this;

            return copy;
        }
    }


}