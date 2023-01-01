/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.apps.morph;

import one.empty3.library.*;

import java.util.List;

public class ShapeMorph extends ITexture {
    private ITexture text1;
    private ITexture text2;
    private StructureMatrix<Point3D> grid1 = new StructureMatrix<>(2, Point3D.class);
    private StructureMatrix<Point3D> grid2 = new StructureMatrix<>(2, Point3D.class);
    private int gridSizeX = 0;
    private int gridSizeY = 0;
    double t;

    ShapeMorph(ITexture text1, ITexture text2, StructureMatrix<Point3D> grid1,
               StructureMatrix<Point3D> grid2) {
        this.text1 = text1;
        this.text2 = text2;
        this.grid1 = grid1;
        this.grid2 = grid2;
        this.t = 0;
    }

    public ITexture getText1() {
        return text1;
    }

    public void setText1(ITexture text1) {
        this.text1 = text1;
    }

    public ITexture getText2() {
        return text2;
    }

    public void setText2(ITexture text2) {
        this.text2 = text2;
    }

    public StructureMatrix<Point3D> getGrid1() {
        return grid1;
    }

    public void setGrid1(StructureMatrix<Point3D> grid1) {
        this.grid1 = grid1;
    }

    public StructureMatrix<Point3D> getGrid2() {
        return grid2;
    }

    public void setGrid2(StructureMatrix<Point3D> grid2) {
        this.grid2 = grid2;
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public void setGridSizeX(int gridSizeX) {
        this.gridSizeX = gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public void setGridSizeY(int gridSizeY) {
        this.gridSizeY = gridSizeY;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    /**
     *
     * @param u ordonnée du point intersection texturé
     * @param v coordonnée du point intersection texturé
     * @return u,v : coordonnées de texurée dans la case
     */
    public Point3D coordText(double u, double v) {
        int sizeGridX = grid1.getData2d().size();
        int sizeGridY = grid1.getData2d().get(0).size();
        int xGrid1 = (int) (sizeGridX * u);
        int yGrid1 = (int) (sizeGridY * v);
        int xGrid2 = (int) (sizeGridX * (u+1./sizeGridX));
        int yGrid2 = (int) (sizeGridY * (v+1./sizeGridY));

        Point3D [] gridP1 = new Point3D[] {
                grid1.getElem(xGrid1, yGrid1),
                grid1.getElem(xGrid2, yGrid1),
                grid1.getElem(xGrid2, yGrid2),
                grid1.getElem(xGrid1, yGrid2)
        };
        Point3D [] gridP2 = new Point3D[] {
                grid2.getElem(xGrid1, yGrid1),
                grid2.getElem(xGrid2, yGrid1),
                grid2.getElem(xGrid2, yGrid2),
                grid2.getElem(xGrid1, yGrid2)
        };
        Point3D [] gridT = new Point3D[4];

        for(int i=0; i<4; i++) {
            gridT[i] = gridP1[i].plus(gridP2[i].moins(gridP1[i].mult(t)));
        }




        double Mu0 =(gridT[1].getY()-gridT[0].getY())/(gridT[1].getX()-gridT[0].getX());
        double Mu1 =(gridT[2].getY()-gridT[3].getY())/(gridT[2].getX()-gridT[3].getX());
        double Mu= (Mu0+Mu1)/2;
        double Mv0 =(gridT[3].getY()-gridT[0].getY())/(gridT[3].getX()-gridT[0].getX());
        double Mv1 =(gridT[2].getY()-gridT[1].getY())/(gridT[2].getX()-gridT[1].getX());
        double Mv= (Mv0+Mv1)/2;


        // yP=Mu*xP+b
        double bu0 = (gridT[1].getY()+gridT[0].getY())/2-Mu*(gridT[1].getX()+gridT[0].getX());
        double bu1 = (gridT[3].getY()+gridT[2].getY())/2-Mu*(gridT[3].getX()+gridT[2].getX());
        double bv0 = (gridT[1].getY()+gridT[2].getY())/2-Mv*(gridT[1].getX()+gridT[2].getX());
        double bv1 = (gridT[3].getY()+gridT[0].getY())/2-Mv*(gridT[3].getX()+gridT[0].getX());

        double distanceU0 = distance(u, v, 1, -Mu, bu0);
        double distanceU1 = distance(u, v, 1, -Mu, bu1);
        double distanceV0 = distance(u, v, 1, -Mu, bv0);
        double distanceV1 = distance(u, v, 1, -Mu, bv1);

        return
                new Point3D(distanceU0/(distanceU1-distanceU0), distanceV0/(distanceV1-distanceV0), 0d);
    }

    /***
     * Droite ax+by+c=0 et point p0
     * @return distance
     */
    public double distance(double x0, double y0, double a, double b, double c) {
        return Math.abs(a*x0+b*x0+c) /Math.sqrt(a*a+b*b);
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }

    public int colorMean(int color1, int color2) {
        double[] doubles1 = Lumiere.getDoubles(color1);
        double[] doubles2 = Lumiere.getDoubles(color2);
        double[] d3 = new double[3];
        for (int i = 0; i < d3.length; i++) {
            d3[i] = doubles1[i]*t+doubles2[i]*(1-t);
        }
        return Lumiere.getInt(d3);
    }

    @Override
    public int getColorAt(double x, double y) {
        List<Double> pText1 = coordText(x, y).multDot(
                new Point3D(1. / grid1.getData2d().size(),
                        1. / grid1.getData2d().get(0).size(),
                        0d)).getCoordArr().getData1d();
        List<Double> pText2 = coordText(x, y).multDot(
                new Point3D(1. / grid1.getData2d().size(),
                        1. / grid1.getData2d().get(0).size(),
                        0d)).getCoordArr().getData1d();
        int colorAt1 = text1.getColorAt(pText1.get(0), pText1.get(1));
        int colorAt2 = text2.getColorAt(pText2.get(0), pText2.get(1));

        return colorMean(colorAt1, colorAt2);
    }
}
