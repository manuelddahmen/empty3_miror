/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */
package one.empty3.tests.coursecheval;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomiale;
import one.empty3.library.core.nurbs.FctXY;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.tribase.TubulaireN2;

import java.io.File;
import java.util.function.Consumer;

public class ChatHumain extends RepresentableConteneur {
    private final StructureMatrix<ParametricSurface> psGround = new StructureMatrix<ParametricSurface>(0, ParametricSurface.class);
    private final StructureMatrix<Sphere> tete = new StructureMatrix<>(0, Sphere.class);
    private final StructureMatrix<Sphere> queue = new StructureMatrix<>(0, Sphere.class);
    private StructureMatrix<TubulaireN2> corps = new StructureMatrix<>(0, TubulaireN2.class);
    private StructureMatrix<TubulaireN2> pattes = new StructureMatrix<>(1, TubulaireN2.class);
    private ChatMovements move;


    public ChatHumain(ParametricSurface psGround) {
        this.psGround.setElem(psGround);

        corps.setElem(new TubulaireN2());
        StructureMatrix<Point3D> coefficients
                = ((CourbeParametriquePolynomiale) corps.getElem().getSoulCurve().getElem()).getCoefficients();
        coefficients.add(new Point3D(50.0, -100.0, 0.0));
        coefficients.add(new Point3D(50.0, -50.0, 0.0));
        coefficients.add(new Point3D(0.0, -50.0, 0.0));
        coefficients.add(new Point3D(-50.0, -50.0, 0.0));
        coefficients.add(new Point3D(-100.0, -50.0, 0.0));
        corps.getElem().getDiameterFunction().setElem(new FctXY().setFormulaX("50"));

        corps.getElem().texture(new ImageTexture(new File("res/img/_light-1337_2082-pelage-d-un-chat-roux-et-blanc-a-poil-long.jpg")));

        pattes.setElem(new TubulaireN2(), 0);
        pattes.setElem(new TubulaireN2(), 1);
        pattes.setElem(new TubulaireN2(), 2);
        pattes.setElem(new TubulaireN2(), 3);


        coefficients = ((CourbeParametriquePolynomiale) pattes.getElem(0).getSoulCurve().getElem()).getCoefficients();

        // Avant gauche
        coefficients.setElem(new Point3D(50., -25., 30.), 0);
        coefficients.setElem(new Point3D(50., 100., 30.), 1);
        coefficients.setElem(new Point3D(50., 150., 30.), 2);
        coefficients = ((CourbeParametriquePolynomiale) pattes.getElem(1).getSoulCurve().getElem()).getCoefficients();
        // Avant droite
        coefficients.setElem(new Point3D(50., -25., -30.), 0);
        coefficients.setElem(new Point3D(50., 100., -30.), 1);
        coefficients.setElem(new Point3D(50., 150., -30.), 2);

        coefficients = ((CourbeParametriquePolynomiale) pattes.getElem(2).getSoulCurve().getElem()).getCoefficients();
        // Arrière gauche
        coefficients.setElem(new Point3D(-50., -25., 30.), 0);
        coefficients.setElem(new Point3D(-50., 100., 30.), 1);
        coefficients.setElem(new Point3D(-50., 150., 30.), 2);
        coefficients = ((CourbeParametriquePolynomiale) pattes.getElem(3).getSoulCurve().getElem()).getCoefficients();
        // Arrière droite
        coefficients.setElem(new Point3D(-50., -25., -30.), 0);
        coefficients.setElem(new Point3D(-50., 100., -30.), 1);
        coefficients.setElem(new Point3D(-50., 150., -30.), 2);

        StructureMatrix<Point3D> coefficients1
                = ((CourbeParametriquePolynomiale) corps.getElem().getSoulCurve().getElem()).getCoefficients();

        tete.setElem(new Sphere(coefficients1.getElem(0), 50));
        queue.setElem(new Sphere(coefficients1.getElem(coefficients1.getData1d().size() - 1), 50));

        add(new PsOnPs(psGround, corps.getElem()));
        for (TubulaireN2 patte : pattes.getData1d()) {
            patte.setDiameter(20);
            add(new PsOnPs(psGround, patte));
        }
        add(new PsOnPs(psGround, tete.getElem()));
        add(new PsOnPs(psGround, queue.getElem()));

        move = new ChatMovements(this);

        for (ParametricSurface elem : new ParametricSurface[]{corps.getElem(), pattes.getElem(0), pattes.getElem(1), pattes.getElem(2), pattes.getElem(3)}) {
            elem.setIncrU(0.1);
            elem.setIncrV(0.1);
        }
    }

    @Override
    public void texture(ITexture tc) {
        pattes.getData1d().forEach(tubulaireN2 -> tubulaireN2.texture(tc));
        corps.getElem().texture(tc);
        tete.getElem().texture(tc);
        queue.getElem().texture(tc);
        super.texture(tc);
    }

    public ChatMovements getMoves() {
        return move;
    }

    public void setAngleXyZ(double angleXY, double angleZ) {
        for (Representable r : getListRepresentable()) {
            if (r instanceof PsOnPs) {
                ((PsOnPs) r).setAngleXY(angleXY);
                ((PsOnPs) r).setAngleZ(angleXY);
            }
        }
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("corps/corps", corps);
        getDeclaredDataStructure().put("tete/tete", tete);
        getDeclaredDataStructure().put("queue/queue", queue);
        getDeclaredDataStructure().put("pattes/pattes", pattes);
        return;
    }

    final static class ChatMovements extends Move {
        private final ChatHumain cheval;

        public ChatMovements(ChatHumain cheval) {
            this.cheval = cheval;
        }

        public void moveTete(StructureMatrix<Sphere> tete, TubulaireN2 corps) {
            Point3D elem = ((CourbeParametriquePolynomiale) corps.getSoulCurve().getElem()).getCoefficients().getElem(0);

            StructureMatrix<Axe> axe = new StructureMatrix<>(0, Axe.class);
            axe.setElem(new Axe(
                    elem.plus(Point3D.Y.mult(tete.getElem().getCircle().getRadius())),
                    elem.moins(Point3D.Y.mult(tete.getElem().getCircle().getRadius()))
            ));
            tete.getElem().getCircle().setAxis(axe);
        }

        public void moveQueue(StructureMatrix<Sphere> tete, TubulaireN2 corps) {
            Point3D elem = ((CourbeParametriquePolynomiale) corps.getSoulCurve().getElem()).getCoefficients().getElem(
                    ((CourbeParametriquePolynomiale) corps.getSoulCurve().getElem()).getCoefficients().getData1d().size() - 1);

            StructureMatrix<Axe> axe = new StructureMatrix<>(0, Axe.class);
            axe.setElem(new Axe(
                    elem.plus(Point3D.Y.mult(tete.getElem().getCircle().getRadius())),
                    elem.moins(Point3D.Y.mult(tete.getElem().getCircle().getRadius()))
            ));
            tete.getElem().getCircle().setAxis(axe);
        }

        public ChatHumain[] trotte(double speed, double seconds, int fps, boolean startsLeftFront, double angleTurn, double distance) {
            ChatHumain[] cheval1 = new ChatHumain[(int) (seconds * fps)];

            for (int i = 0; i < cheval1.length; i++) {
                cheval1[i] = new ChatHumain(cheval.psGround.getElem());
                cheval1[i].texture(this.cheval.texture());
                double sin = Math.sin((speed * 2.0 * Math.PI * i) / seconds / fps);
                double cos = Math.cos((speed * 2.0 * Math.PI * i) / seconds / fps);
                if (!startsLeftFront) {
                    double a = cos;
                    cos = sin;
                    sin = a;
                }
                System.out.println("sin : " + sin);
                ((CourbeParametriquePolynomiale) cheval1[i].pattes.getElem(0)
                        .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(50 + sin * distance / 2);
                ((CourbeParametriquePolynomiale) cheval1[i].pattes.getElem(1)
                        .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(50 + cos * distance / 2);
                ((CourbeParametriquePolynomiale) cheval1[i].pattes.getElem(2)
                        .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(-50 + cos * distance / 2);
                ((CourbeParametriquePolynomiale) cheval1[i].pattes.getElem(3)
                        .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(-50 + sin * distance / 2);

                ((CourbeParametriquePolynomiale) cheval1[i].pattes.getElem(0)
                        .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(50 + sin * distance);
                ((CourbeParametriquePolynomiale) cheval1[i].pattes.getElem(1)
                        .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(50 + cos * distance);
                ((CourbeParametriquePolynomiale) cheval1[i].pattes.getElem(2)
                        .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(-50 + cos * distance);
                ((CourbeParametriquePolynomiale) cheval1[i].pattes.getElem(3)
                        .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(-50 + sin * distance);

                moveTete(cheval1[i].tete, cheval1[i].corps.getElem());
            }

            return cheval1;
        }

    }
}
