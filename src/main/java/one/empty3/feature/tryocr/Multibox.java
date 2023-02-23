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

package one.empty3.feature.tryocr;

import javaAnd.awt.Point;
import one.empty3.feature.PixM;
import one.empty3.library.Point3D;

import java.util.function.Consumer;

//import ResolutionCharacter8.*;
public class Multibox {


    public Multibox() {

    }
    public void parseForCharacter(PixM input, Letter letter) {
        letter.traits.forEach(trait -> {
            Point3D a = new Point3D(-1.0, -1.0, -1.0);
            Point3D b = new Point3D();
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

        });

    }
}
