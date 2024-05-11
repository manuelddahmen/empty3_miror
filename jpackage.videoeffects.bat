set JAVA_HOME=C:\Program Files\Java\jdk-21\
set PATH=%PATH;C:\Program Files\Java\jdk-21\bin

REM gradle dist

jpackage --dest packages --main-jar empty3-library-3d.jar -i build/libs -n MorphAndDeformUi --main-class one.empty3.apps.MorphUI --app-version 4.0 --description "Application for testing functionality"    --vendor "Empty3 by Manuel D. Dahmen" --copyright "Copyright 2024, Apache 2 Open Sources License"
jpackage --dest packages --main-jar empty3-library-3d.jar -i build/libs -n VideoEffectsUiLive --main-class one.empty3.feature.ClassSchemaBuilder --app-version 4.0  --description "Application for testing functionality"    --vendor "Empty3 by Manuel D. Dahmen" --copyright "Copyright 2024, Apache 2 Open Sources License"
jpackage --dest packages --main-jar empty3-library-3d.jar -i build/libs -n ModellerUi --main-class one.empty3.gui.Main --app-version 4.0 --description "Application for testing functionality"    --vendor "Empty3 by Manuel D. Dahmen" --copyright "Copyright 2024, Apache 2 Open Sources License"
jpackage --dest packages --main-jar empty3-library-3d.jar -i build/libs -n TestHumanHeadTexturing --main-class one.empty3.tests.humanheadtexturing.TestHumanHeadTexturing --app-version 4.0 --description "Application for testing functionality"    --vendor "Empty3 by Manuel D. Dahmen" --copyright "Copyright 2024, Apache 2 Open Sources License"