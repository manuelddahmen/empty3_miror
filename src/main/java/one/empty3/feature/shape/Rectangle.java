package one.empty3.feature.shape;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

public class Rectangle extends ParametricCurve {
    private final double height;
    private final double width;
    private final double y;
    private final double x;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Point3D calculerPoint3D(double t) {
        return new Point3D(t >= 0 && t < 0.25 ? x : t >= 0.5 && t < 0.75 ? x + width : 0.0,
                t >= 0.25 && t < 0.5 ? y : t >= 0.75 && t < 1 ? y + height : 0.0, 0.0,
                texture());
    }
}
