set JAVA_HOME=C:\Program Files\Java\jdk-17\
set PATH=%PATH;C:\Program Files\Java\jdk-17\bin
jpackage --main-jar empty3-library-generic.jar -i build/libs -n VideoEffects --main-class one.empty3.feature.ClassSchemaBuilder
jpackage --main-jar empty3-library-generic.jar -i build/libs -n MorphUI --main-class one.empty3.apps.MorphUI
jpackage --main-jar empty3-library-generic.jar -i build/libs -n Empty3Gui --main-class one.empty3.gui.Main

