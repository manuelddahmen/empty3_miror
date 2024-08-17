package one.empty3.apps.facedetect;

import one.empty3.library.Point3D;
import one.empty3.library.Polygons;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.SurfaceParametriquePolynomiale;
import one.empty3.library.core.nurbs.SurfaceParametriquePolynomialeBezier;

import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DistanceBezier3 extends DistanceBezier2 {
    double[][][] doubles;
    final double DIM_MAX = Double.MAX_VALUE;
    final int dim = 1000;

    public DistanceBezier3(List<Point3D> A, List<Point3D> B, Dimension2D aDimReal, Dimension2D bDimReal, boolean opt1, boolean optimizeGrid) {
        super(A, B, aDimReal, bDimReal, opt1, optimizeGrid);
        if(A==null || B==null || A.size()==0 || B.size()==0 || A.size()!=B.size()) {
            setInvalidArray(true);
            return;
        }
        Logger.getAnonymousLogger().log(Level.INFO, "DistanceBezier3() constructor started");
        doubles = new double[5][dim][dim];
        for (int u = 0; u < dim; u++) {
            // Closer A point
            for (int v = 0; v < dim; v++) {
                //[0][u][v] =
                final double finalU = 1.0 * u / dim;
                final double finalV = 1.0 * v / dim;

                Point3D pab = new Point3D(finalU, finalV, 0.0);

                double[] dist_current_min = new double[A.size()];
                Arrays.fill(dist_current_min, DIM_MAX);
                Double distMin = DIM_MAX;
                for (int i = 0; i < B.size(); i++) {
                    Point3D b = B.get(i);
                    distMin = Point3D.distance(b, pab);
                    if (distMin < dist_current_min[i]) {
                        dist_current_min[i] = distMin;
                        // closer b point for B(u,v)
                        doubles[0][u][v] = b.getX();
                        doubles[1][u][v] = b.getY();
                        doubles[2][u][v] = i;
                    }
                }
                double ub = doubles[0][u][v];
                double vb = doubles[1][u][v];
                Point3D paa = new Point3D(ub, vb, 0.0);
                double distance = DIM_MAX;
                for (int i = 0; i < A.size(); i++) {
                    Point3D a = A.get(i);
                    distMin = Point3D.distance(paa, a);
                    if(distMin<distance) {
                        doubles[3][u][v] = a.getX();
                        doubles[4][u][v] = a.getY();
                        distance = distMin;
                    }
                }
            }
        }
        Logger.getAnonymousLogger().log(Level.INFO, "DistanceBezier3() constructor ended");
    }


    @Override
    public Point3D findAxPointInB(double u, double v) {
        int iX = (int)(u * dim);
        int iY = (int)(v * dim);
        if(iX >= dim) System.exit(1);
        if(iY>= dim) System.exit(1);
        Point3D a  = A.get((int) doubles[2][iX][iY]);
        Point3D b  = new Point3D(doubles[0][(int) (u * dim)][(int) (v * dim)], doubles[1][(int) (u * dim)][(int) (v * dim)], 0.0);
        Point3D a2 = new Point3D(doubles[3][(int) (u * dim)][(int) (v * dim)], doubles[4][(int) (u * dim)][(int) (v * dim)], 0.0);
        System.out.println(b);
        return a;
    }


}