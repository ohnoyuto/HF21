pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials {
                username = "mapbox"  // 固定で "mapbox" と設定
                password = "pk.eyJ1IjoieXV0bzEyMDciLCJhIjoiY20zaTJ4ajI0MG04azJqc2FqMXFhZzB5OSJ9.qPixdG9r-FhD1wzLJRblBQ"  // アクセストークン
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

    }
}

rootProject.name = "HF21"
include(":app")
