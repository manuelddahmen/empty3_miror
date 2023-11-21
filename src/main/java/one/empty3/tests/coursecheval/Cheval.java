/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.tests.coursecheval;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomiale;
import one.empty3.library.core.nurbs.FctXY;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.tribase.TubulaireN2;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class Cheval extends RepresentableConteneur {
    private StructureMatrix<TubulaireN2> corps = new StructureMatrix<>(0, TubulaireN2.class);
    private StructureMatrix<TubulaireN2> pattes = new StructureMatrix<>(1, TubulaireN2.class);
    private ChevalMovements move;


    public Cheval() {
        corps.setElem(new TubulaireN2());
        StructureMatrix<Point3D> coefficients = ((CourbeParametriquePolynomiale) corps.getElem().getSoulCurve().getElem()).getCoefficients();
        coefficients.add(new Point3D(100.0, -100.0, 0.0));
        coefficients.add(new Point3D(50.0, -50.0, 0.0));
        coefficients.add(new Point3D(0.0, -50.0, 0.0));
        coefficients.add(new Point3D(-50.0, -50.0, 0.0));
        coefficients.add(new Point3D(-100.0, -50.0, 0.0));
        corps.getElem().getDiameterFunction().setElem(new FctXY().setFormulaX("30*x"));

        pattes.setElem(new TubulaireN2(), 0);
        pattes.setElem(new TubulaireN2(), 1);
        pattes.setElem(new TubulaireN2(), 2);
        pattes.setElem(new TubulaireN2(), 3);

        coefficients = ((CourbeParametriquePolynomiale) pattes.getElem(0).getSoulCurve().getElem()).getCoefficients();
        coefficients.add(new Point3D(50., -50., 50.));
        coefficients.add(new Point3D(50., -25., 50.));
        coefficients.add(new Point3D(50., -.0, 50.));
        coefficients = ((CourbeParametriquePolynomiale) pattes.getElem(1).getSoulCurve().getElem()).getCoefficients();
        coefficients.add(new Point3D(50., -50., -50.));
        coefficients.add(new Point3D(50., -25., -50.));
        coefficients.add(new Point3D(50., -.0, -50.));

        coefficients = ((CourbeParametriquePolynomiale) pattes.getElem(2).getSoulCurve().getElem()).getCoefficients();
        coefficients.add(new Point3D(-50., -50., 50.));
        coefficients.add(new Point3D(-50., -25., 50.));
        coefficients.add(new Point3D(-50., -.0, 50.));
        coefficients = ((CourbeParametriquePolynomiale) pattes.getElem(3).getSoulCurve().getElem()).getCoefficients();
        coefficients.add(new Point3D(-50., -50., -50.));
        coefficients.add(new Point3D(-50., -25., -50.));
        coefficients.add(new Point3D(-50., -.0, -50.));

        add(corps.getElem());
        for (TubulaireN2 TubulaireN2 : pattes.getData1d()) {
            add(TubulaireN2);
        }

        move = new ChevalMovements(this);

        for(ParametricSurface elem :  new ParametricSurface[] {corps.getElem(), pattes.getElem(0), pattes.getElem(1), pattes.getElem(2), pattes.getElem(3)}) {
            elem.setIncrU(0.1);
            elem.setIncrV(0.1);
        }
    }

    @Override
    public void texture(ITexture tc) {
        pattes.getData1d().forEach(new Consumer<TubulaireN2>() {
            @Override
            public void accept(TubulaireN2 tubulaireN2) {
                tubulaireN2.texture(tc);
            }
        });
        corps.getElem().texture(tc);
        super.texture(tc);
    }

    @Override
    public void declareProperties() {
        getDeclaredDataStructure().put("corps/corps du cheval", corps);
        getDeclaredDataStructure().put("pattes/pattes du cheval", pattes);
        super.declareProperties();
    }

    public ChevalMovements getMoves() {
        return move;
    }

    final static class ChevalMovements extends Move {
        private final Cheval cheval;

        public ChevalMovements(Cheval cheval) {
            this.cheval = cheval;
        }
        public Cheval [] trotte(double seconds, int fps, boolean startsLeftFront, double angleTurn, double distance) {
            Cheval [] cheval1 = new Cheval[(int) (seconds * fps)];
            for(int i=0; i<cheval1.length; i++) {
                cheval1[i] = new Cheval();
                double sin = Math.sin((2.0 * Math.PI * i) / cheval1.length);
                double cos = Math.cos((2.0 * Math.PI * i) / cheval1.length);
                if(!startsLeftFront) {
                    double a = cos;
                    cos = sin;
                    sin = a;
                }
                    ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(0)
                            .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(sin *distance/2);
                    ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(2)
                            .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(sin *distance/2);
                    ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(1)
                            .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(cos *distance/2);
                    ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(3)
                            .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(cos *distance/2);

                    ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(0)
                            .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(sin *distance);
                    ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(2)
                            .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(sin *distance);
                    ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(1)
                            .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(cos *distance);
                    ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(3)
                            .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(cos *distance);


            }
            return cheval1;
        }
        public Cheval [] galope(double seconds, int fps, boolean startsLeftFront, double angleTurn, int distance) {
            Cheval [] cheval1 = new Cheval[(int) (seconds * fps)];
            for(int i=0; i<cheval1.length; i++) {
                cheval1[i] = new Cheval();
                double sin = Math.sin((2.0 * Math.PI * i) / cheval1.length);
                double cos = Math.cos((2.0 * Math.PI * i) / cheval1.length);
                if(!startsLeftFront) {
                    double a = cos;
                    cos = sin;
                    sin = a;
                }
                ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(0)
                        .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(sin *distance*2);
                ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(2)
                        .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(sin *distance*2);
                ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(1)
                        .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(cos *distance*2);
                ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(3)
                        .getSoulCurve().getElem()).getCoefficients().getElem(1).setX(cos *distance*2);

                ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(0)
                        .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(sin *distance);
                ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(2)
                        .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(sin *distance);
                ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(1)
                        .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(cos *distance);
                ((CourbeParametriquePolynomiale)cheval1[i].pattes.getElem(3)
                        .getSoulCurve().getElem()).getCoefficients().getElem(2).setX(cos *distance);


            }
            return cheval1;
        }
        public Cheval [] saute(double seconds, int fps, boolean startsLeftFront, double angleTurn, int i) {
            Cheval [] cheval1 = new Cheval[(int) (seconds * fps)];

            return cheval1;
        }

    }
}
