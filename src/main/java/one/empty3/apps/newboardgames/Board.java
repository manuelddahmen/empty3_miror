package one.empty3.apps.newboardgames;

import one.empty3.library.Camera;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.RepresentableConteneur;
import one.empty3.neuralnetwork.Dimension;

import java.awt.geom.Point2D;
import java.util.List;

public class Board extends Representable {
    private Camera camera;

    Camera camera() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    Point2D getSize2D() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    Point3D getSize3D() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    Cell getCellContainer() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    List<Character> getCharacters() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
