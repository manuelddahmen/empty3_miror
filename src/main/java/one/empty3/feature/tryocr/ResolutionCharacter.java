package one.empty3.feature.tryocr;

import one.empty3.feature.Linear;
import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.feature.shape.Rectangle;
import one.empty3.library.ITexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ResolutionCharacter {

    public static final int XPLUS = 0;
    public static final int YPLUS = 1;
    public static final int XINVE = 2;
    public static final int YINVE = 3;
    private static final int ADD_POINT_TO_RANDOM_CURVE = 0;
    private static final int ADD_RANDOM_CURVE = 2;
    private static final int DEL_RANDOM_CURVE = 3;
    private static final int ADD_CURVES = 4;
    private static final int MAX_ERRORS_ADD_CURVES = 5;
    private static final int MOVE_POINTS = 1;
    private static final int BLANK = 0;
    private static final int CHARS = 1;
    private static int SHAKE_SIZE = 20;
    final int epochs = 100;
    int step = 1;// Searched Characters size.
    private double dim = 14;
    private int shakeTimes;
    private PixM pixM;
    private PixM bgAll;
    private double totalError;
    private int numCurves;
    private double errorDiff = 0.0;
    private PixM globalInputBgAl;
    private PixM input;
    private int stepMax = 60;
    private int charMinWidth = 7;

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
                if (Arrays.equals(input.getValues(i, j), (Lumiere.getRgb(traceColor)))) {
                    output.setValues(i, j, traceColor.getRed(), traceColor.getGreen(), traceColor.getBlue());

                } else {
                    int neighbors = 0;
                    boolean cont = true;
                    double[] cl = Lumiere.getRgb(traceColor);
                    double distMax = (Math.max(input.getColumns(), input.getLines()));
                    for (int n = 1; n < distMax && cont; n++) {
                        for (int ii = 0; ii < n && cont; ii++)
                            for (int jj = 0; jj < n && cont; jj++) {
                                double[] values = input.getValues(i + ii, j + jj);
                                if (Arrays.equals(input.getValues(i, j), cl)) {
                                    output.setValues(i, j, 1f * traceColor.getRed() / n, 1f * traceColor.getGreen() / n, 1f * traceColor.getBlue() / n);
                                    cont = true;
                                }
                            }
                    }
                }
            }
    }

    public void run(String[] args) {
        int e = 1;
        BufferedImage read = ImageIO.read(new File("C:\\Users\\manue\\EmptyCanvasTest\\ocr\\AC_AC4part1dos2AC1fr3img1.jpg"));

        assert read != null;
        pixM = input = new PixM(read);//PixM.getPixM(read, 750);
        globalInputBgAl = pixM.copy().replaceColor(new double[]{0, 0, 0}, new double[]{1, 1, 1}, 0.7);
        bgAll = globalInputBgAl;


        System.out.println(input.getCompCount());

        State[][] states = new State[pixM.getColumns()][pixM.getLines()];
        for (int i = 0; i < pixM.getColumns() - step; i += step)
            for (int j = 0; j < pixM.getLines() - step; j += step) {
                PixM inputIJ = pixM.subImage(i, j, step, step);
                PixM backgroundImageIJ = bgAll.subImage(i, j, step, step);

                states[i][j] = new State(inputIJ, backgroundImageIJ, i, j, step);
            }

        /*for (int i = 0; i < pixM.getColumns() - step; i += step)
            for (int j = 0; j < pixM.getLines() - step; j += step) {
                states[i][j].currentCurves.add(
                        new CourbeParametriquePolynomialeBezier(
                                new Point3D[]{
                                        FeatureLine.getFeatLine(
                                                randomLine(), 0).multDot(Point3D.n(i + step / 2, j + step / 2, 0)),
                                        FeatureLine.getFeatLine(
                                                randomLine(), 1).multDot(Point3D.n(i + step / 2, j + step / 2, 0))}));


            }
*/
        PixM globalOutputOrig = input.copy();
        PixM globalOutputBgAl = globalInputBgAl.copy();
        ITexture texture = new TextureCol(Color.BLACK);
        double error0 = 0;
        totalError = 0;
        double erreurMoyenne = 1.0;
        while (e < epochs) {
            totalError = 0;
            numCurves = 0;
            errorDiff = 0;
            for (int i = 0; i < pixM.getColumns() - step; i += step) {
                for (int j = 0; j < pixM.getLines() - step; j += step) {
                   /* double cE = 0.0;
                    SHAKE_SIZE = 50;
                    double currentError = states[i][j].computeError();
                    states[i][j].currentError = states[i][j].computeError();
                    for (int s = 0; s < SHAKE_SIZE; s++) {
                        if (erreurMoyenne < states[i][j].currentError) {
                            State states1 = states[i][j].copy();
                            int operation = (int) (Math.random() * 4);
                            shakeCurves(states1, operation);
                            if (states1.computeError() < states[i][j].computeError()) {
                                State tmp = states[i][j];
                                states1.previousState = tmp;
                                states[i][j] = states1;
                            } else {
                                // NO MODIFICATION OR WORSE MODIFICATION
                            }
                        }
                    }
                    double newCurrentError = states[i][j].computeError();
                    cE = currentError;

                    numCurves += states[i][j].currentCurves.size();
                    states[i][j].lastError = states[i][j].currentError;
                    states[i][j].currentError = currentError;

                    totalError += currentError;
                    errorDiff += (newCurrentError-currentError);
                */
                    if (Arrays.equals(input.getValues(i, j), new double[]{1, 1, 1})) {
                        int w, h, ii, ij;
                        ii = i;
                        ij = j;
                        w = 2;
                        h = 2;
                        boolean wB = false;
                        boolean hB = false;
                        boolean fail = false;
                        boolean hBout = false;
                        boolean wBout = false;
                        while (!fail && ii + w < input.getColumns() && ij + h < input.getLines() && (!(w > stepMax || h > stepMax)) && (!hBout || !wBout)) {
                            boolean[] v = testRectIs(input, ii, ij, w, h, new double[]{1, 1, 1});
                            if (!v[XPLUS]) {
                                fail = true;

                            }
                            if (!v[YINVE]) {
                                fail = true;
                            }


                            if (v[YPLUS] && hB) {
                                hBout = true;
                            } else if (!v[YPLUS] && hB) {
                                h++;
                            } else if (!v[YPLUS] && !hB) {
                                hB = true;
                            } else if (v[YPLUS] && !hB) h++;

                            if (v[XINVE] && wB) {
                                wBout = true;
                            } else if (!v[XINVE] && wB) {
                                w++;
                            } else if (!v[XINVE] && !wB) {
                                wB = true;
                            } else if (v[XINVE] && !wB) w++;

                            /*if (hBout && !wBout)
                                w++;
                            if (wBout && !hBout)
                                h++;

                             */
                        }
                        boolean succeded = false;
                        if (fail) {
                            if (Arrays.equals(testRectIs(input, ii, ij, w - 1, h, new double[]{1, 1, 1}), new boolean[]{true, true, true, true})) {
                                w = w - 1;
                                succeded = true;
                            }
                            if (Arrays.equals(testRectIs(input, ii, ij, w, h - 1, new double[]{1, 1, 1}), new boolean[]{true, true, true, true})) {
                                h = h - 1;
                                succeded = true;
                            }
                        }

                        succeded = (hBout && wBout) || succeded;
                        if (Arrays.equals(testRectIs(input, ii, ij, w, h, new double[]{1, 1, 1}), new boolean[]{true, true, true, true}) || succeded) {
                            System.err.println("// Le test a passé");
                            System.err.printf("ResolutionCharacter occurrence of rect %d,%d,%d,%d", i, j, w, h);
                            Rectangle rectangle = new Rectangle(i, j, w, h);

                            rectangle.texture(texture);
                            rectangle.setIncrU(1. / (2 * w + 2 * h));
                            globalOutputOrig.plotCurve(rectangle, texture);
                            recognize(globalOutputOrig, i, j, w, h).forEach(System.out::println);

                        }
                    }
                }
            }
//            System.out.println("Epoch : " + e + "/" + epochs);
//            System.out.println("Taile de la trame :" + states.length + " , " + states[0].length);
//            System.out.println("Différence d'erreur epoch :" + errorDiff);
//            System.out.println("Erreur totale :" + totalError);
//            System.out.println("Nombre de courbes :" + numCurves);
//            numCurves = 0;
//            error0 = totalError;
//            erreurMoyenne = errorDiff;
//
//
//            e++;
            break;
        }
        for (int i = 0; i < pixM.getColumns() - step; i += step)
            for (int j = 0; j < pixM.getLines() - step; j += step) {
                if (states[i][j] != null) {
                    states[i][j].currentCurves.forEach(courbeParametriquePolynomialeBezier -> {
                        globalOutputBgAl.plotCurve(courbeParametriquePolynomialeBezier, texture);
                    });
                }
            }
        Rectangle rectangle = new Rectangle(10, 10, globalOutputBgAl.getColumns() - 20, globalOutputBgAl.getLines() - 20);
        rectangle.texture(texture);
        rectangle.setIncrU(1. / globalOutputBgAl.getColumns() / globalOutputBgAl.getLines());
        globalOutputOrig.plotCurve(rectangle, texture);
        try {
            ImageIO.write(input.getImage(), "jpg", new File("1Input.backgroundTextCleared.jpg"));
            ImageIO.write(globalInputBgAl.getImage(), "jpg", new File("2Input.backgroundTextCleared.jpg"));
            ImageIO.write(globalOutputOrig.getImage(), "jpg", new File("3Output.original.jpg"));
            ImageIO.write(globalOutputBgAl.getImage(), "jpg", new File("4Output.backgroundTextCleared.jpg"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean[] testRectIs(PixM input, int x, int y, int w, int h, double[] color) {
        boolean[] w0h1w2h3 = new boolean[4];

        w0h1w2h3[0] = true;
        for (int i = x; i <= x + w; i++)
            if (!Arrays.equals(input.getValues(i, y), color)) w0h1w2h3[0] = false;
        w0h1w2h3[1] = true;
        for (int j = y; j <= y + h; j++)
            if (!Arrays.equals(input.getValues(x, j), color)) w0h1w2h3[1] = false;
        w0h1w2h3[2] = true;
        for (int i = x + w; i >= x; i--)
            if (!Arrays.equals(input.getValues(i, y), color)) w0h1w2h3[2] = false;
        w0h1w2h3[3] = true;
        for (int j = y + h; j >= y; j--)
            if (!Arrays.equals(input.getValues(x, j), color)) w0h1w2h3[3] = false;
        return w0h1w2h3;
    }

    private void shakeCurves(State state, int choice) {
        switch (choice) {
            case ADD_POINT_TO_RANDOM_CURVE:
                if (state.currentCurves.size() == 0) state.currentCurves.add(new CourbeParametriquePolynomialeBezier());
                int i = (int) (Math.random() * state.currentCurves.size());

                int j = 0;
                if (state.currentCurves.get(i).getCoefficients().data1d.size() == 0) {
                    state.currentCurves.get(i).getCoefficients().setElem(Point3D.random(state.step).plus(state.xyz).to2DwoZ().get3D(), 0);
                    j = 0;
                } else {
                    j = (int) (state.currentCurves.get(i).getCoefficients().data1d.size() * Math.random());
                    if (j < 4)
                        state.currentCurves.get(i).getCoefficients().data1d.set(j, Point3D.random(state.step).plus(state.xyz).to2DwoZ().get3D());
                }
                break;
            case MOVE_POINTS:
                if (state.currentCurves.size() == 0) state.currentCurves.add(new CourbeParametriquePolynomialeBezier());
                i = (int) (Math.random() * state.currentCurves.size());

                j = 0;
                if (state.currentCurves.get(i).getCoefficients().data1d.size() == 0) {
                    state.currentCurves.get(i).getCoefficients().setElem(Point3D.random(state.step).plus(state.xyz).to2DwoZ().get3D(), 0);
                    j = 0;
                } else {
                    j = (int) (state.currentCurves.get(i).getCoefficients().data1d.size() * Math.random());
                    if (j < 4)
                        state.currentCurves.get(i).getCoefficients().data1d.add(Point3D.random(state.step).plus(state.xyz).to2DwoZ().get3D());
                }
                break;
            case ADD_RANDOM_CURVE:
                if (state.currentCurves.size() <= 8) return;
                state.currentCurves.add(new CourbeParametriquePolynomialeBezier(new Point3D[]{FeatureLine.getFeatLine(randomLine(), 0).multDot(Point3D.n(state.step, state.step, 0)).multDot(state.xyz), FeatureLine.getFeatLine(randomLine(), 1).multDot(Point3D.n(state.step, state.step, 0)).multDot(state.xyz)}));


                break;
            case DEL_RANDOM_CURVE:
                if (state.currentCurves.size() > 9) return;
                if (state.currentCurves.get(0).getCoefficients().data1d.size() > 0)
                    state.currentCurves.get(0).getCoefficients().delete(0);
                else state.currentCurves.remove(0);

                break;
        }

    }

    /***
     * OCR: combien on voit d'inversion.
     * A (0,1) (1,2)+ (2, 1) (3,2)
     * a (0,2) (1,2)+ (2,1) (3,2)
     */
    public Map<Character, Integer[]> patterns() {
        Map<Character, Integer[]> mapcharsAlphabetLines = new HashMap<>();
        mapcharsAlphabetLines.put('A', new Integer[]{1, 2, 1, 2});
        mapcharsAlphabetLines.put('a', new Integer[]{2, 2, 1, 2});
        mapcharsAlphabetLines.put('B', new Integer[]{1, 2, 1, 2, 1});
        mapcharsAlphabetLines.put('b', new Integer[]{1, 2, 2, 1});
        mapcharsAlphabetLines.put('C', new Integer[]{1, 2, 1, 2, 1});
        mapcharsAlphabetLines.put('c', new Integer[]{1, 2, 1, 2, 1});
        mapcharsAlphabetLines.put('D', new Integer[]{1, 2, 1});
        mapcharsAlphabetLines.put('d', new Integer[]{1, 2, 1, 2});
        mapcharsAlphabetLines.put('E', new Integer[]{1});
        mapcharsAlphabetLines.put('e', new Integer[]{1, 2, 1, 2});
        mapcharsAlphabetLines.put('F', new Integer[]{1});
        mapcharsAlphabetLines.put('f', new Integer[]{1});
        mapcharsAlphabetLines.put('G', new Integer[]{1, 2, 1, 2, 1});
        mapcharsAlphabetLines.put('g', new Integer[]{1, 2, 1, 1, 2, 1});
        mapcharsAlphabetLines.put('H', new Integer[]{2, 1, 2});
        mapcharsAlphabetLines.put('h', new Integer[]{1, 2, 1, 2});
        mapcharsAlphabetLines.put('I', new Integer[]{1});
        mapcharsAlphabetLines.put('i', new Integer[]{1, 0, 1});
        mapcharsAlphabetLines.put('J', new Integer[]{1, 2, 1});
        mapcharsAlphabetLines.put('j', new Integer[]{1, 0, 1, 2, 1});
        mapcharsAlphabetLines.put('K', new Integer[]{2, 1, 2});
        mapcharsAlphabetLines.put('k', new Integer[]{2, 1, 2});
        mapcharsAlphabetLines.put('L', new Integer[]{1});
        mapcharsAlphabetLines.put('l', new Integer[]{1});
        mapcharsAlphabetLines.put('M', new Integer[]{2, 3, 2});
        mapcharsAlphabetLines.put('m', new Integer[]{2, 3});
        mapcharsAlphabetLines.put('N', new Integer[]{2});
        mapcharsAlphabetLines.put('n', new Integer[]{2, 1, 2});
        mapcharsAlphabetLines.put('O', new Integer[]{1, 2, 1});
        mapcharsAlphabetLines.put('o', new Integer[]{1, 2, 1});
        mapcharsAlphabetLines.put('P', new Integer[]{1, 2, 1});
        mapcharsAlphabetLines.put('p', new Integer[]{2, 1, 2, 1});
        mapcharsAlphabetLines.put('Q', new Integer[]{1, 2, 1});
        mapcharsAlphabetLines.put('q', new Integer[]{2, 1, 2, 1});
        mapcharsAlphabetLines.put('R', new Integer[]{1, 2, 1, 2});
        mapcharsAlphabetLines.put('r', new Integer[]{2, 1, 2, 1});
        mapcharsAlphabetLines.put('S', new Integer[]{1, 2, 1, 2, 1});
        mapcharsAlphabetLines.put('s', new Integer[]{1, 2, 1, 2, 1});
        mapcharsAlphabetLines.put('T', new Integer[]{1});
        mapcharsAlphabetLines.put('t', new Integer[]{1});
        mapcharsAlphabetLines.put('U', new Integer[]{2, 1});
        mapcharsAlphabetLines.put('u', new Integer[]{2, 1});
        mapcharsAlphabetLines.put('V', new Integer[]{2, 1});
        mapcharsAlphabetLines.put('v', new Integer[]{2, 1});
        mapcharsAlphabetLines.put('W', new Integer[]{3, 4, 2});
        mapcharsAlphabetLines.put('w', new Integer[]{3, 4, 2});
        mapcharsAlphabetLines.put('X', new Integer[]{2, 1, 2});
        mapcharsAlphabetLines.put('x', new Integer[]{2, 1, 2});
        mapcharsAlphabetLines.put('Y', new Integer[]{2, 1});
        mapcharsAlphabetLines.put('y', new Integer[]{2, 1});
        mapcharsAlphabetLines.put('Z', new Integer[]{1});
        mapcharsAlphabetLines.put('z', new Integer[]{1});

        return mapcharsAlphabetLines;
    }

    public Set<Character> recognize(PixM mat, int x, int y, int w, int h) {
        Map<Character, Integer[]> patternsHorizon = patterns();
        Map<Character, Integer> okChars = new HashMap();
        patternsHorizon.forEach((character, integers) -> okChars.put(character, 0));
        for (int j = x; j <= y + h; j++) {
            var ref = new Object() {
                int countOnLineI = 0;
            };
            int current = BLANK;

            for (int i = x; i <= x + w; i++) {
                if (mat.getValues(i, j).equals(new double[]{0, 0, 0})) {
                    if (current == BLANK) {
                        ref.countOnLineI++;
                        current = CHARS;
                    }
                } else {
                    current = BLANK;
                }
            }
            List<Character> del = new ArrayList();
            Map<Character, Integer> put = new HashMap();
            okChars.forEach((character, integer) -> {
                Integer[] integers = patternsHorizon.get(character);
                if (ref.countOnLineI == integers[integer]) {
                } else if (integers.length > integer + 1 && integers[integer] == ref.countOnLineI) {
                    put.put(character, integer + 1);

                } else del.add(character);

            });
            del.forEach(okChars::remove);
            okChars.putAll(put);
        }

        return okChars.keySet();
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
    ArrayList<CourbeParametriquePolynomialeBezier> resolvedCurved = new ArrayList<>();
    ArrayList<CourbeParametriquePolynomialeBezier> currentCurves = new ArrayList<>();
    double lastError = Double.NaN;
    State previousState;
    PixM input;
    PixM backgroundImage;
    Color textColor = Color.BLACK;
    int dim;

    public State(PixM image, PixM backgroundImage, int i, int j, int step) {
        this.input = image;
        this.backgroundImage = backgroundImage;
        xyz = Point3D.n(i + step / 2., j + step / 2., 0.);
        this.step = step;
    }

    public double computeError() {
        State state = this;
        PixM pError = state.backgroundImage;
        PixM inputCopy = input.copy();
        state.currentCurves.forEach(courbeParametriquePolynomialeBezier -> {
            pError.plotCurve(courbeParametriquePolynomialeBezier, new TextureCol(Color.BLACK));
            numCurves++;
        });
        PixM copy = pError.copy();
        Linear linear = new Linear(inputCopy, pError, new PixM(input.getColumns(), input.getLines()));
        linear.op2d2d(new char[]{'-'}, new int[][]{{1, 0}}, new int[]{2});
        PixM diff = linear.getImages()[2];
        return diff.mean(0, 0, diff.getColumns(), diff.getLines());

    }

    public State copy() {
        State copy = new State(this.input, backgroundImage, (int) (double) this.xyz.get(0), (int) (double) this.xyz.get(1), (int) (double) this.step);
        copy.currentError = currentError;
        copy.currentCurves = (ArrayList<CourbeParametriquePolynomialeBezier>) this.currentCurves.clone();
        copy.lastError = lastError;
        copy.step = step;
        copy.xyz = xyz;
        copy.backgroundImage = backgroundImage;
        copy.input = input;
        copy.dim = dim;
        copy.lastErrors = lastErrors;
        copy.textColor = textColor;
        copy.previousState = this;

        return copy;
    }
}
}
