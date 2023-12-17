package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library1.shader.Vec;

public class VecHeightMap extends HeightMapSurface {
    private final Vec vec;
    private int columnsCount;
    private final ParametricSurface parametricSurface;

    public VecHeightMap(ParametricSurface parametricSurface, Vec map, int columnsCount) {
        this.vec = map;
        this.columnsCount = columnsCount;
        this.parametricSurface = parametricSurface;
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        int i = (int) (u * ((vec.size() / columnsCount)))+vec.size()*2;
        int j = (int) (v * columnsCount)+vec.size()*2;

        i = Math.round(i);
        j = Math.round(j);

        i = i % (vec.size()/columnsCount);
        j = j % (columnsCount);

        double round = getControlAt(i, j);
        return parametricSurface.calculerPoint3D(u, v)
          .plus(parametricSurface.calculerNormale3D(u, v)
                  .mult(round));
    }

    public double getControlAt(int x, int y) {
        double v = 0.0;
        if(y*columnsCount+x<vec.size()&&y*columnsCount+x>=0
                &&x>=0&&y>=0&&x<vec.size()&&y<vec.size()) {
            v = vec.get(y * columnsCount + x);
        }
        return v==0.0?0.0:v;
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
