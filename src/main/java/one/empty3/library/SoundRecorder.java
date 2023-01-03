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

package one.empty3.library;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/*__
 * Created by manue on 03-09-15.
 */
public class SoundRecorder {

    protected TargetDataLine getOutputLine(File file) {
        try {
            AudioFileFormat ff = AudioSystem.getAudioFileFormat(file);


            try {

                return AudioSystem.getTargetDataLine(ff.getFormat());

            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mix() {

    }
}
