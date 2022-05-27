package one.empty3.feature.tryocr;

import one.empty3.feature.Linear;
import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ResolutionCharacter {

    private double dim = 14;
    private int shakeTimes;
    private PixM pixM;
    private double totalError;

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

    public void main(String[] args) {
        int epochs = 2000;
        int e = 1;
        BufferedImage read = ImageIO.read(new File("\"C:\\Users\\manue\\EmptyCanvasTest\\ocr\\AC_AC4part1dos2AC1fr3img1.jpg"));

        pixM = new PixM(read);

        int step = 3;

        while (e < epochs) {
            double error0 = 0;
            totalError=0;
            State states[][] = new State[pixM.getColumns() / step][pixM.getLines() / step];
            for (int i = 0; i < pixM.getColumns() - step; i += step)
                for (int j = 0; i < pixM.getLines() - step; i += step) {
                    states[i][j] = new State(pixM, i, j, step);
                }
            for (int i = 0; i < pixM.getColumns() - step; i += step)
                for (int j = 0; i < pixM.getLines() - step; i += step) {
                    states[i][j].currentCurves.add(
                            new CourbeParametriquePolynomialeBezier(
                                    new Point3D[]{
                                            FeatureLine.getFeatLine(
                                                    randomLine(), 0),
                                            FeatureLine.getFeatLine(
                                                    randomLine(), 1)}));


                }
            for (int i = 0; i < pixM.getColumns() - step; i += step)
                for (int j = 0; i < pixM.getLines() - step; i += step) {
                    State state = new State(pixM, i, j, step);
                    state.previousState = states[i][j];
                    states[i][j] = state;

                    double currentError = states[i][j].computeError();

                    states[i][j].currentCurves.add(
                            new CourbeParametriquePolynomialeBezier(
                                    new Point3D[]{
                                            FeatureLine.getFeatLine(
                                                    randomLine(), 0),
                                            FeatureLine.getFeatLine(
                                                    randomLine(), 1)}));


                    shakeCurves(states[i][j]);


                    totalError += currentError;

                    if(states[i][j].lastError>currentError) {
                        states[i][j] = states[i][j].previousState;
                    }else {
                        states[i][j] = states[i][j];
                    }
                }
            System.out.println(totalError-error0);
            error0 = totalError;
            e++;
        }
    }

    private void shakeCurves(State state) {
        int i = (int) (Math.random() * state.currentCurves.size());
        int j = (int) (Math.random() * state.currentCurves.get(i).getCoefficients().data1d.size());
        state.currentCurves.get(i).getCoefficients().setElem(Point3D.random(dim), j);
    }


    class StateAction {
        ArrayList<FeatureLine> beginWith;
        CourbeParametriquePolynomialeBezier curve;
        ArrayList<Point3D> moveXY;
        ArrayList<Point3D> added;
        ArrayList<Point3D> deleted;
    }

    class State {
        public State (PixM image, int i, int j, int step) {
            input = image.subImage(i, j, step, step);
            backgroudImage = image.subImage(
                    (int)(Math.random()*image.getColumns()),
                    (int)(Math.random()*image.getColumns()),
                    step, step);
        }


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

        public double computeError() {
            PixM pError  =backgroudImage;
            previousState.currentCurves.forEach( courbeParametriquePolynomialeBezier ->
                    {
                    pixM.plotCurve(courbeParametriquePolynomialeBezier, new TextureCol(Color.WHITE));
                    }
            );
            PixM copy = pError.copy();
            Linear linear = new Linear(pixM, pError,copy );
            linear.op2d2d(new char[]{'-'}, new int[][] {{0, 1}}, new int[]{2});
            return copy.mean(0, 0, copy.getColumns(), copy.getLines());
        }
    }


}