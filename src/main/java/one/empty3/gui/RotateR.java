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

package one.empty3.gui;

import one.empty3.library.Matrix33;
import one.empty3.library.Representable;
import one.empty3.library.Rotation;
import one.empty3.library.ZBufferImpl;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

/**
 * Created by manue on 17-11-19.
 */

/***
 * Rotate around Object's axis. New axis. Re
 */
public class RotateR extends ModifyR {
    private Representable representable;
    private Rotation rotationR;
    private Matrix33 vectorsDir;

    public RotateR(ZBufferImpl impl)
    {
        super(impl);
    }


}
