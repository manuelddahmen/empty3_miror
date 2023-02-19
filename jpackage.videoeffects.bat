set JAVA_HOME=C:\Program Files\Java\jdk-17\
set PATH=%PATH;C:\Program Files\Java\jdk-17\bin

REM gradle dist

jpackage --dest packages --name MorphAndDeform -i build/libs --main-class one.empty3.apps.MorphUI --app-version 2.0  --main-jar empty3-library-generic.jar    --description "Application for testing functionality"    --vendor "Empty3 by manuel dahmen" --copyright "Copyright 2022, All rights reserved"

jpackage --main-jar empty3-library-generic.jar -i build/libs -n VideoEffects --main-class one.empty3.feature.ClassSchemaBuilder
jpackage --main-jar empty3-library-generic.jar -i build/libs -n MorphUI --main-class one.empty3.apps.MorphUI
jpackage --main-jar empty3-library-generic.jar -i build/libs -n Empty3Gui --main-class one.empty3.gui.Main

