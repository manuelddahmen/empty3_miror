package one.empty3.feature.histograms;

import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Hist4Contour extends ProcessFile {

    private int kMax = 3;
    private double fractMax = 0.2;

    public class Circle {
        public double x, y, r;
        public double i;
        public Point3D maxColor;
        public double count;

        public Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    ", i=" + i +
                    "n maxColor = p " + maxColor.toString() +
                    '}';
        }
    }

    //private final int[][][] levels;


    public void makeHistogram(double r) {

    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }

    public Circle getLevel(Circle c, PixM m) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        double sum = 0;
        int count = 0;
        c.maxColor = Point3D.O0;
        double intensity = 0.0;
        for (double i = c.x - c.r; i <= c.x + c.r; i++) {
            for (double j = c.y - c.r; j <= c.y + c.r; j++) {
                if (c.x - c.r >= 0 && c.y - c.r >= 0 && c.x + c.r < m.getColumns() && c.x + c.r < m.getLines()
                && (i==c.x-c.r || j==c.y-c.r ||i==c.x+c.r || j==c.y+c.r  )) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                    Point3D p = m.getP((int) i, (int) j);
                    if (p.norme() > 0.3 && p.moins(c.maxColor).norme() > 0.3) {
                        c.maxColor = p;
                        sum++;
                    }
                }
            }
        }
        c.maxColor = c.maxColor.mult(1 / (sum + 1));
        if (count > 0) {
            c.i = intensity;
            c.count = c.i;
        }else {
            c.i = 0.0;
            c.r = 1;
        }


        return c;
    }


    @Override
    public boolean process(File in, File out) {
        if(!isImage(in)) {
            return false;
        }
        PixM inP;
        try {
            inP = PixM.getPixM(ImageIO.read(in), maxRes);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        Double max = 0.0;
        PixM outP = new PixM(inP.getColumns(), inP.getLines());
        PixM outP0 = new PixM(inP.getColumns(), inP.getLines());
        double maxR = Math.min(inP.getLines(), inP.getColumns()) * fractMax;
            for (int i = 0; i < inP.getColumns(); i++) {
                for (int j = 0; j < inP.getLines(); j++) {
                    Circle c = null;
                    for (int k = 1; k < maxR; k += 1) {
                    if (k == 1) {
                        c = getLevel(new Circle(i, j, k), inP);
                        outP0.setP(i, j, new Point3D(c.i, c.r, 0.0));
                    } else {
                        if (outP0.getP(i, j).get(0) != 0) {
                            c = getLevel(new Circle(i, j, k), inP);
                            if (outP.getP(i, j).get(0) > 0) {
                                outP0.setP(i, j, outP.getP(i, j));
                            } else {
                                outP.setP(i, j, new Point3D(0.0, c.r, 0.0));
                                 max = outP.getP(i, j).get(1) > max ? outP.getP(i, j).get(1) : max;
                            }
                        }
                    }
                }
                    outP0.setP(i, j, new Point3D(c.r, c.r, c.r));

            }
        }
// Colorier en fonction des pixels voisins
//        Circle c2 = getLevel(cc, inP, cc.r/2);
        try {
            ImageIO.write(outP0.normalize(1, max, 0, 1).getImage(), "jpg", out);
            //ImageIO.write(outP0.normalize(0, 1).getImage(), "jpg", out);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }


}
