package one.empty3.library;

import one.empty3.library.core.nurbs.SurfaceParametriquePolynomiale;

public class Polygons extends SurfaceParametriquePolynomiale {
    @Override
    public Point3D calculerPoint3D(double u, double v) {
        try {
            int indexU0 = (int) (u * coefficients.getData2d().get(0).size());
            int indexV0 = (int) (v * coefficients.getData2d().size());
            if (indexU0 > coefficients.getData2d().get(0).size() - 1) {
                indexU0 = coefficients.getData2d().get(0).size() - 1;
            }
            if (indexV0 > coefficients.getData2d().size() - 1) {
                indexV0 = coefficients.getData2d().size() - 1;
            }
            int indexU1 = (int) (indexU0 + 1.);
            int indexV1 = (int) (indexV0 + 1.);
            if (indexU1 > coefficients.getData2d().get(0).size() - 1) {
                indexU1 = coefficients.getData2d().get(0).size() - 1;
            }
            if (indexV1 > coefficients.getData2d().size() - 1) {
                indexV1 = coefficients.getData2d().size() - 1;
            }
            Point3D[] points = new Point3D[]{
                    coefficients.getElem(indexU0, indexV0), coefficients.getElem(indexU1, indexV0),
                    coefficients.getElem(indexU1, indexV1), coefficients.getElem(indexU0, indexV1)
            };
            double U = u * (coefficients.getData2d().get(0).size()) - indexU0;
            double V = v * (coefficients.getData2d().size()) - indexV0;
            assert U>=0 && U<=1 && V>=0 && V<=1;
            Point3D pUv0 = points[0].plus(points[1].moins(points[0]).mult(U));
            Point3D pUv1 = points[3].plus(points[2].moins(points[3]).mult(U));
            Point3D pU0v = points[3].moins(points[0]).mult(V);
            Point3D pU1v = points[3].moins(points[2]).mult(V);
            return pUv0.plus(pUv1.moins(pUv0).mult(V)); // Discutable
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
