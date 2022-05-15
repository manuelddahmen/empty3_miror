/**
* JetBrains Space Automation
* This Kotlin-script file lets you automate build activities
* For more info, refer to https:*/

plugins {
    `java-library`
}

dependencies {                              
    api("junit:junit:4.13")
    implementation("junit:junit:4.13")
    testImplementation("junit:junit:4.13")
}

configurations {                            
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

sourceSets {                                
    main {                                  
        java.srcDir("src/core/java")
    }
}

java {                                      
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    test {                                  
        testLogging.showExceptions = true
    }
}

tasks { test { testLogging.showExceptions = true } } / job("Build and publish") { container(displayName = "Run publish script", image = "gradle") { kotlinScript { api -> api.gradle("build") api.gradle("publish") } } }
*/
job("Build and publish") {
    container(displayName = "Run publish script", image = "gradle") {
        kotlinScript { api ->
            api.gradle("build")
            api.gradle("publish")
        }
    }
}