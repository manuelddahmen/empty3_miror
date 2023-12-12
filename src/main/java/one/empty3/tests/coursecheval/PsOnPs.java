package one.empty3.tests.coursecheval;

import one.empty3.library.CopyRepresentableError;
import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.PcOnPs;
import one.empty3.library.core.tribase.Plan3D;

public class PsOnPs extends ParametricSurface {
    private final ParametricSurface shape;
    private final ParametricSurface base;

    private double angleZ;
    private double angleXY;
    public PsOnPs(ParametricSurface base, ParametricSurface shape) {
        this.base = base;
            this.shape = shape;//.copy();
        angleXY = 0;
        angleZ = 0;

    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        Point3D o0 = base.calculerPoint3D(u, v);
        Point3D vx = base.calculerTangenteU(u, v).norme1();
        Point3D vy = base.calculerTangenteV(u, v).norme1();
        Point3D vz = base.calculerNormale3D(u, v).norme1();

        Point3D axeX = vx.mult(Math.cos(2*Math.PI*angleXY)).plus(vy.mult(Math.sin(2*Math.PI*angleXY)));
        Point3D axeY = vx.mult(-Math.sin(2*Math.PI*angleXY)).plus(vy.mult(Math.cos(2*Math.PI*angleXY)));
        Point3D axeZ = vx.prodVect(vy).mult(-1.0);

        Point3D uvShape = shape.calculerPoint3D(u, v);

        return o0.plus(axeX.mult(uvShape.get(0))).plus(axeY.mult(uvShape.get(2))).plus(axeZ.mult(uvShape.get(1)));
    }

    public ParametricSurface getShape() {
        return shape;
    }

    public ParametricSurface getBase() {
        return base;
    }

    public double getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(double angleZ) {
        this.angleZ = angleZ;
    }

    public double getAngleXY() {
        return angleXY;
    }

    public void setAngleXY(double angleXY) {
        this.angleXY = angleXY;
    }
}
