package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.Point2Point;
import one.empty3.library.core.nurbs.SurfaceParametriquePolynomialeBezier;
import one.empty3.library1.shader.Vec;

public class VecHeightMap extends ParametricSurface {
    private final Vec vec;
    private SurfaceParametriquePolynomialeBezier surfaceParametriquePolynomialeBezier;
    private int columnsCount;
    private final ParametricSurface parametricSurface;

    public VecHeightMap(ParametricSurface parametricSurface, Vec map, int columnsCount) {
        terminalU.setElem(new Point2Point() {
            @Override
            public Point3D result(Point3D p) {
                return new Point3D(1.0, p.get(1), p.get(2));
            }
        });
        terminalV.setElem(new Point2Point() {
            @Override
            public Point3D result(Point3D p) {
                return new Point3D(p.get(0), 1.0, 0.0);
            }
        });
        this.vec = map;
        this.columnsCount = columnsCount;
        this.parametricSurface = parametricSurface;

        Point3D[][] ps = new Point3D[columnsCount][vec.size() / columnsCount];
        try {
            for (int i = 0; i < map.size(); i++) {
                ps[i % columnsCount][i / columnsCount] = new Point3D(map.get(i), 0.0, 0.0);
            }
            surfaceParametriquePolynomialeBezier = new SurfaceParametriquePolynomialeBezier(ps);
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        int i = (int) (u * columnsCount)+vec.size()*2;
        int j = (int) (v * ((vec.size() / columnsCount)))+vec.size()*2;

        i = Math.round(i);
        j = Math.round(j);

        i = i % (columnsCount);
        j = j % (vec.size()/columnsCount);


        if(surfaceParametriquePolynomialeBezier!=null) {
            return parametricSurface.calculerPoint3D(u, v)
                    .plus(parametricSurface.calculerNormale3D(u, v)
                            .mult(surfaceParametriquePolynomialeBezier.calculerPoint3D(u, v)));

        } else {
            Double round = getControlAt(i, j);
            if (round != null) {
                return parametricSurface.calculerPoint3D(u, v)
                        .plus(parametricSurface.calculerNormale3D(u, v)
                                .mult(round));
            }
            return null;
        }
    }

    public Double getControlAt(int x, int y) {
        Double v = null;
        if(y*columnsCount+x<vec.size()&&y*columnsCount+x>=0
                &&x>=0&&y>=0&&x<vec.size()&&y<vec.size()) {
            v = vec.get(y * columnsCount + x);
        }
        return v;
    }

    public Vec getVec() {
        return vec;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public void setColumnsCount(int columnsCount) {
        this.columnsCount = columnsCount;
    }

    public ParametricSurface getParametricSurface() {
        return parametricSurface;
    }
}
