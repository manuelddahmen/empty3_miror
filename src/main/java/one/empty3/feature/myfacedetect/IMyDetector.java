package one.empty3.feature.myfacedetect;

import one.empty3.feature.PixM;
import one.empty3.library.Point2D;

public interface IMyDetector {
    public void setPixM(PixM matrice);
    public void execute();
    public int getFaces();
    public int[] getFaceIds();
    public Point2D getEyeLeft();
    public Point2D getEyeRight();
    public Point2D getNose();
    public Point2D getMouthLeft();
    public Point2D getMouthRight();
}
