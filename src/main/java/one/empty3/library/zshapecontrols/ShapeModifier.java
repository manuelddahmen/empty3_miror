package one.empty3.library.zshapecontrols;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricSurface;

public interface ShapeModifier {
    public Point3D calculerPoint3D(ParametricSurface surface, double u, double v);
}
