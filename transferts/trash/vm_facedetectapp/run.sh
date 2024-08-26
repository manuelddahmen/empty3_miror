#!/bin/bash
CURRENT_DIRECTORY=´pwd´
echo $CURRENT_DIRECTORY
cd empty3_miror
~/empty3_miror/gradlew FaceDetectApp --args="$1 $2"
cd -

