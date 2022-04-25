plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jcodec:jcodec-javase:0.2.5")
    implementation("org.jogamp.jogl:jogl-all-main:2.3.2")
    implementation("com.miglayout:miglayout:3.7.4")
    implementation("com.badlogicgames.gdx:gdx:1.9.10")
    implementation("xom:xom:1.3.5")
    implementation("com.jgoodies:jgoodies-common:1.8.1")
    implementation("com.google.android:android:2.2.1")
    implementation("com.formdev:flatlaf:2.1")
    implementation("org.jdesktop:beansbinding:1.2.1")
    implementation("junit:junit:3.8.2")
    implementation("junit:junit:4.13.2")
    implementation("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}