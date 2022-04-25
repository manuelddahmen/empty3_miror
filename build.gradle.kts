plugins {
    id("java")
}

group = "one.empty3"
version = "generic-1.0"

repositories {
    google()
    mavenCentral()
    maven {
        url =uri("https://repo.maven.apache.org/maven2/")
    }
    maven {
        url =uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
    maven {
        url =uri("https://repository.sonatype.org/content/groups/public/")
    }
    maven {
        url =uri("https://mvnrepository.com/")
    }
    maven {
        url =uri("https://maven.scijava.org/content/repositories/public/")
    }
}

dependencies {
    implementation("org.jcodec:jcodec-javase:0.2.5")
    implementation("org.jogamp.jogl:jogl-all-main:2.3.2")
    implementation("com.miglayout:miglayout:3.7.4")
    implementation("com.badlogicgames.gdx:gdx:1.10.0")
    implementation("xom:xom:1.3.7")
    implementation("com.jgoodies:jgoodies-common:1.8.1")
    implementation("com.google.android:android:4.1.1.4")
    implementation("com.formdev:flatlaf:2.1")
    implementation("org.jdesktop:beansbinding:1.2.1")
    implementation("junit:junit:4.13.2")
    implementation("junit:junit:4.13.2")
    implementation("com.github.sarxos:webcam-capture:0.3.12")
    implementation("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
    implementation("commons-net:commons-net:3.5")
    implementation("org.jdom:jdom2:2.0.6.1")
    implementation("org.apache.servicemix.bundles:org.apache.servicemix.bundles.jdom:2.0.6.1_1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}