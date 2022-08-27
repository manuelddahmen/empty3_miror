package one.empty3.feature.myfacedetect;

import one.empty3.feature.PixM;
import one.empty3.library.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Detector implements IMyDetector {
     List<Face> faces = new ArrayList<>();
    private PixM pixels;

    @Override
    public void execute() {
        if(pixels!=null && pixels.getLines()>0&& pixels.getColumns()>0) {

        }
    }

    @Override
    public void setPixM(PixM matrice) {
        this.pixels = matrice;
    }

    @Override
    public int getFaces() {
        return 0;
    }

    @Override
    public int[] getFaceIds() {
        return new int[0];
    }

    @Override
    public Point2D getEyeLeft() {
        return null;
    }

    @Override
    public Point2D getEyeRight() {
        return null;
    }

    @Override
    public Point2D getNose() {
        return null;
    }

    @Override
    public Point2D getMouthLeft() {
        return null;
    }

    @Override
    public Point2D getMouthRight() {
        return null;
    }
}
