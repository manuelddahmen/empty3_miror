package one.empty3.apps.opad.game;

import one.empty3.library.Representable;

public class Unit {
    private double x, y;
    private int unitsX, unitsY;
    private int centerX, centerY;
    private double orientation;
    private Representable graphics;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getUnitsX() {
        return unitsX;
    }

    public void setUnitsX(int unitsX) {
        this.unitsX = unitsX;
    }

    public int getUnitsY() {
        return unitsY;
    }

    public void setUnitsY(int unitsY) {
        this.unitsY = unitsY;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public Representable getGraphics() {
        return graphics;
    }

    public void setGraphics(Representable graphics) {
        this.graphics = graphics;
    }
}
