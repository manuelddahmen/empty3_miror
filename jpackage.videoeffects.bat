set JAVA_HOME=C:\Program Files\Java\jdk-17\
set PATH=%PATH;C:\Program Files\Java\jdk-17\bin

REM gradle dist

jpackage --dest packages --main-jar empty3-library-generic.jar -i build/libs - MorphAndDeform --main-class one.empty3.apps.MorphUI --app-version 3.0 --description "Application for testing functionality"    --vendor "Empty3 by Manuel D. Dahmen" --copyright "Copyright 2023, All rights reserved"
jpackage --dest packages --main-jar empty3-library-generic.jar -i build/libs -n VideoEffects --main-class one.empty3.feature.ClassSchemaBuilder --app-version 3.0  --description "Application for testing functionality"    --vendor "Empty3 by Manuel D. Dahmen" --copyright "Copyright 2023, All rights reserved"
jpackage --dest packages --main-jar empty3-library-generic.jar -i build/libs -n MorphUI --main-class one.empty3.apps.MorphUI --app-version 3.0  --description "Application for testing functionality"    --vendor "Empty3 by Manuel D. Dahmen" --copyright "Copyright 2023, All rights reserved"
jpackage --dest packages --main-jar empty3-library-generic.jar -i build/libs -n Empty3Gui --main-class one.empty3.gui.Main --app-version 3.0 --description "Application for testing functionality"    --vendor "Empty3 by Manuel D. Dahmen" --copyright "Copyright 2023, All rights reserved"

