/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
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

/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*__
Global license : 

 CC Attribution

 author Manuel Dahmen _manuel.dahmen@gmx.com_

 ***/


package one.empty3.apps.opad;

import one.empty3.library.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 *
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class Ciel {
    private final Sphere bleu;

    public Ciel() {
        bleu = new Sphere(new Point3D(0.5, 0.5, 0.0), 2);

        try {
            URL resource = getClass().getResource("one.empty3.library/apps/darz/marssurface.jpg");
            Logger.getAnonymousLogger().log(Level.INFO, resource.toExternalForm());
            BufferedImage read = ImageIO.read(resource);
            ECBufferedImage ecBufferedImage = new ECBufferedImage(read);
            bleu.texture(new ImageTexture(ecBufferedImage));

        } catch (Exception  ex) {
            bleu.texture(new ColorTexture(Color.BLUE));
        }
    }

    public Sphere getBleu() {
        return bleu;
    }


}
