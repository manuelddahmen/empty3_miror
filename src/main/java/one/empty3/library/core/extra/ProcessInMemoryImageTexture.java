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

package one.empty3.library.core.extra;

import javaAnd.awt.image.BufferedImage;
import one.empty3.feature.PixM;
import one.empty3.feature.ProcessInMemory;
import one.empty3.library.ECBufferedImage;
import one.empty3.library.ImageTexture;
import one.empty3.library.P;

public class ProcessInMemoryImageTexture extends ImageTexture {
    private ProcessInMemory processInMemory;

    public ProcessInMemoryImageTexture(ProcessInMemory processInMemory, ECBufferedImage bi) {
        super(bi);
        this.processInMemory = processInMemory;
        setImage(new ECBufferedImage(processInMemory.ProcessInMemory(new PixM(bi)).getImage()));
    }
    public ProcessInMemoryImageTexture(ECBufferedImage bi) {
        super(bi);
    }
}
