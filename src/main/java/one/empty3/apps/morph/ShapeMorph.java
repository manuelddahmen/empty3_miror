/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.apps.morph;

import one.empty3.library.*;

public class ShapeMorph extends ITexture {
    private StructureMatrix<Point3D> grid1 = new StructureMatrix<>(2, Point3D.class);
    private StructureMatrix<Point3D> grid2 = new StructureMatrix<>(2, Point3D.class);
    private int gridSizeX = 0;
    private int gridSizeY = 0;
    double t;

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
                new Point3D(distanceU0/(distanceU1-distanceU0), distanceV0/(distanceV1-distanceV0), 0d),


        /*Yu0= Mu * Xu0 + bu0 point projeté de puv sur p0->p1
                Yu1 = Mu* xu1 + bu1 point projeté de puv sur p3->p2

        Du0 (Y0+y1)/2=Mu*(x0+x1)/2+b01 point milieu droite passant par de pente moyenne. => B01
*/
        //gridT[1].moins(gridT[0]).pro

/*
        Point3D xyInt = new Point3D((x-xGrid1)/(xGrid2-xGrid1),
                    ((y-yGrid1)/(yGrid2-yGrid1)), 0d);
*/


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

    @Override
    public int getColorAt(double x, double y) {
        return 0;
    }
}
