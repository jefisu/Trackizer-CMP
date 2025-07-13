rootProject.name = "Trackizer"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

buildCache {
    local {
        isEnabled = true
        directory = File(rootDir, "build-cache")
    }
}

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    This project requires JDK 17+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}

includeBuild("build-logic")

include(":composeApp")
include(":testUtil")

include(":core:designsystem")
include(":core:util")
include(":core:ui")
include(":core:domain")
include(":core:data")
include(":core:platform")

include(":feature:welcome")
include(":feature:auth")
include(":feature:home")
