/*__
 * Importer une autre test: ah si ca pouvait être fait par classes!
 */
package tests.tests2.repereAssocieAUneCourbeEX;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.CameraInPath;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.ExtrusionB1B1;
import one.empty3.library.core.nurbs.ExtrusionCurveCurve;
import one.empty3.library.core.testing.Resolution;
import one.empty3.library.core.testing.TestObjet;
import one.empty3.library.core.tribase.Extrusion;
import one.empty3.library.core.tribase.TRICylindre;

import java.awt.*;

/*__
 *
 * @author Manuel Dahmen
 */
public class TestCameraEnMouvementCylindre extends TestObjet {

    TextureMov textureMov;
    private CameraInPath cam;
    private ExtrusionCurveCurve e;

    public static void main(String[] args) {
        TestCameraEnMouvementCylindre t = new TestCameraEnMouvementCylindre();
        t.setGenerate(GENERATE_IMAGE | GENERATE_MOVIE);
        t.setMaxFrames(30 * 25);
        t.setDimension(new Resolution(640, 480));
        t.setPublish(true);
        new Thread(t).start();
    }

    @Override
    public void afterRenderFrame() {

    }

    @Override
    public void finit() throws EOFVideoException {
        cam.setT(frame / 25.0 / 8);
        cam.setMatrice(cam.getMatrice().mult(-1));
    }

    @Override
    public void ginit() {
        CourbeChoisie cc = new CourbeChoisie(200, 80, 70, 20);

        cam = new CameraInPath(cc);

        e = new ExtrusionCurveCurve();

        e.getBase().setElem(new Circle(new Axe(Point3D.Z.mult(-100), Point3D.Z.mult(100)), 200));

        e.getPath().setElem(new CourbeParametriquePolynomialeBezier());
        ((CourbeParametriquePolynomialeBezier)(e.getPath().getElem())).getCoefficients().setElem(Point3D.Z.mult(-100), 0);
        ((CourbeParametriquePolynomialeBezier)(e.getPath().getElem())).getCoefficients().setElem(Point3D.Z.mult(100), 1);

        e.texture(new ColorTexture(Color.BLACK));

        Cube cube = new Cube();

        cube.getCote().setElem(100.);
        cube.texture(new ColorTexture(Color.RED));

        scene().add(cube);
        scene().add(e);

        scene().cameraActive(cam);

    }

    @Override
    public void testScene() throws Exception {

    }

    public void afterRender() {

    }
}
