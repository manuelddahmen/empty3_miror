package one.empty3.library.zshapecontrols;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.tribase.Plan3D;

public class PlanarShapeModifier implements ShapeModifier {
    private Plan3D plan;

    public PlanarShapeModifier(Plan3D plan3D) {
        this.plan = plan3D;
    }
    @Override
    public Point3D calculerPoint3D(ParametricSurface surface, double u, double v) {
        return surface.calculerPoint3D(u, v);
    }
}
