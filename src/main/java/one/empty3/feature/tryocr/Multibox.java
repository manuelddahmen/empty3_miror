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

package one.empty3.feature.tryocr;

import one.empty3.feature.PixM;
import one.empty3.library.Point3D;

import java.util.HashMap;

//import ResolutionCharacter8.*;
public class Multibox {
    private static HashMap<Character, Letter> letters = new HashMap<Character, Letter>();



    public Multibox() {
        if(letters.size()==0)
            initLetters();
    }

    public void initLetters() {

        letters.put('a', new Letter(
                new Trait(TraitsY.Up, TraitsX.Left, TraitsY.Up, TraitsX.Right,
                        TraitsShape.Round, TraitsShape.Round),
                new Trait(TraitsY.Up, TraitsX.Right, TraitsY.Down, TraitsX.Right,
                        TraitsShape.Round, TraitsShape.Line)));
        letters.put('b', new Letter(new Trait(TraitsY.UpUp, TraitsX.Left,
                TraitsY.DownDown, TraitsX.Left, TraitsShape.Line, TraitsShape.Line),
                new Trait(TraitsY.Up, TraitsX.Right, TraitsY.Down, TraitsX.Right,
                        TraitsShape.Round, TraitsShape.Round)));
        letters.put('c', new Letter(new Trait(TraitsY.Up, TraitsX.Right, TraitsY.Down, TraitsX.Right,
                        TraitsShape.Round, TraitsShape.Round)));
        letters.put('d', new Letter(new Trait(TraitsY.UpUp, TraitsX.Right,
                TraitsY.DownDown, TraitsX.Right, TraitsShape.Round, TraitsShape.Round),
                new Trait(TraitsY.Up, TraitsX.Left, TraitsY.Down, TraitsX.Left,
                        TraitsShape.Round, TraitsShape.Round)));
        letters.put('e', new Letter(new Trait(TraitsY.Up, TraitsX.Left,
                TraitsY.Up, TraitsX.Right, TraitsShape.Round, TraitsShape.Round)));
        letters.put('f', new Letter(new Trait(TraitsY.Down, TraitsX.Center,
                TraitsY.Center, TraitsX.Center, TraitsShape.Line, TraitsShape.Line)));
        letters.put('g', new Letter(new Trait(TraitsY.Up, TraitsX.Left,
                TraitsY.Up, TraitsX.Right, TraitsShape.Round, TraitsShape.Round)));
        letters.put('h', new Letter(new Trait(TraitsY.Up, TraitsX.Left,
                TraitsY.Down, TraitsX.Left,
                TraitsShape.Line, TraitsShape.Line)));
        letters.put('i', new Letter(new Trait(TraitsY.Up, TraitsX.Center,
                TraitsY.Down, TraitsX.Center,
                TraitsShape.Line, TraitsShape.Line)));
    }

    public int parseForCharacter(PixM input, Letter letter) {
        final boolean[] err = {false};
        int [] okTraits = new int[] {-1};
        letter.traits.forEach(trait -> {
            Point3D a = new Point3D(-1.0, -1.0, -1.0);
            Point3D b = new Point3D(-1.0, -1.0, -1.0);
            if(trait.getValueFrom1().equals(TraitsY.UpUp)
                    && trait.getCurveFrom().equals(TraitsShape.Line))
                a.setY(0.0);
            if(trait.getValueTo1().equals(TraitsY.UpUp)
                    && trait.getCurveFrom().equals(TraitsShape.Line))
                a.setY(0.0);
            if(trait.getValueFrom1().equals(TraitsY.DownDown)
                    && trait.getCurveFrom().equals(TraitsShape.Line))
                a.setY((double) (input.getLines()-1));
            if(trait.getValueTo1().equals(TraitsY.DownDown)
                    && trait.getCurveFrom().equals(TraitsShape.Line))
                b.setY((double) (input.getLines()-1));
            if(trait.getValueFrom1().equals(TraitsY.Center)
                    && trait.getCurveFrom().equals(TraitsShape.Line))
                a.setY((double) (input.getLines()-1));
            if(trait.getValueTo1().equals(TraitsY.Center)
                    && trait.getCurveFrom().equals(TraitsShape.Line))
                b.setY((double) (input.getLines()-1));
            if(a.getY()==-1 || b.getY()==-1) {

            } else {
                // set X
                switch (trait.getValueFrom2()) {
                    case Left:
                        a.setX(0.0);
                        break;
                    case Right:
                        a.setX(input.getColumns()-1.0);
                        break;
                    case Center:
                        a.setX(input.getColumns()/2.0);
                        break;
                    case CenterLeft:
                        a.setX(input.getColumns()*0.25);
                        break;
                    case CenterRight:
                        a.setX(input.getColumns()*0.75);
                        break;
                }
                switch (trait.getValueTo2()) {
                    case Left:
                        a.setX(0.0);
                        break;
                    case Right:
                        a.setX(input.getColumns()-1.0);
                        break;
                    case Center:
                        a.setX(input.getColumns()/2.0);
                        break;
                    case CenterLeft:
                        a.setX(input.getColumns()*0.25);
                        break;
                    case CenterRight:
                        a.setX(input.getColumns()*0.75);
                        break;
                }
                if(a.getY()!=-1 && a.getX()!=-1&&b.getY()!=-1&&b.getX()!=-1) {
                    double l = Point3D.distance(a, b);
                    for(int i=0; i<l; i++) {
                        Point3D p = a.plus(b.moins(a).mult(1.0*i/l));

                        if (input.getValues((int) (double) p.getX(), (int) (double) p.getY()).equals(Point3D.O0)) {
                            err[0] = true;
                        }


                    }

                }

             }
            if(err[0]) {
                okTraits[0]++;
            }

        });
        return okTraits[0];
    }
}
