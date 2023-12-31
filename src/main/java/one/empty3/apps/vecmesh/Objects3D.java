package one.empty3.apps.vecmesh;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library1.shader.Vec;
import one.empty3.library1.tree.ListInstructions;

public class Objects3D {
    public ParametricSurface suv(/*ListInstructions listInstructions,*/ Vec vec3d) {
        return new ParametricSurface() {
            @Override
            public Point3D calculerPoint3D(double u, double v) {
                Point3D p = new Point3D();
                StructureMatrix<Double> vecVal = vec3d.getVecVal();
                double p0 = 0.0;
                for(int i=0; i<3; i++) {
                    if(vecVal.getData1d().size()>i) {
                        p0 = vecVal.getElem(i);
                    }
                    p.set(i, p0);
                }
                return p;
            }

        };
    }
}
