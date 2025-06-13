import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.jefisu.trackizer.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.compose.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.dependencyGuard.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    implementation(libs.truth)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("cmp-application") {
            id = "trackizer.cmp.application"
            implementationClass = "CMPApplicationConventionPlugin"
        }
        register("cmp-feature") {
            id = "trackizer.cmp.feature"
            implementationClass = "CMPFeatureConventionPlugin"
        }
        register("kmp-library") {
            id = "trackizer.kmp.library"
            implementationClass = "KMPLibraryConventionPlugin"
        }
        register("cmp-library") {
            id = "trackizer.cmp.library"
            implementationClass = "CMPLibraryConventionPlugin"
        }
        register("kmp-koin") {
            id = "trackizer.kmp.koin"
            implementationClass = "KMPKoinConventionPlugin"
        }
        register("kmp-firebase") {
            id = "trackizer.kmp.firebase"
            implementationClass = "KMPFirebaseConventionPlugin"
        }
    }
}