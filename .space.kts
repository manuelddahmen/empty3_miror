job("Qodana") {
  startOn {
    gitPush {
      anyBranchMatching {
        +"main"
      }
    }
    codeReviewOpened{}
  }
  container("jetbrains/qodana-jvm") {
    env["QODANA_TOKEN"] = "{{ project:qodana-token }}"
    shellScript {
      content = "qodana"
    }
  }
}

/**
* JetBrains Space Automation
* This Kotlin-script file lets you automate build activities
* For more info, refer to https:*/

job("Build and publish") {
    container(displayName = "Run publish script", image = "gradle") {
        kotlinScript { api ->
            api.gradle("build")
            api.gradle("publish")
        }
    }
    
    
    container(displayName = "Show key using api", image = "openjdk:11.0.3-jdk") {
        kotlinScript { api ->
            // get env var from system
            println("Project key: " + System.getenv("JB_SPACE_PROJECT_KEY"))
            // get env var using API
            println("Project key: " + api.projectKey())
        }
    }
}