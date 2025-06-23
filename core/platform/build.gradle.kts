import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.trackizer.cmp.library)
    alias(libs.plugins.trackizer.kmp.koin)
    alias(libs.plugins.trackizer.kmp.firebase)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.util)
        }
        androidMain.dependencies {
            implementation(libs.credentials)
            implementation(libs.credentials.play.services.auth)
            implementation(libs.identity.googleId)
        }

        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }

    cocoapods {
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        version = libs.versions.application.version.get()
        ios.deploymentTarget = "15.3"
        podfile = rootProject.file("iosApp/Podfile")

        pod("GoogleSignIn") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
        pod("FirebaseCore") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
        pod("FirebaseAuth") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}

buildkonfig {
    targetConfigs {
        val localProperties = loadProperties("local.properties")
        create("android") {
            val androidKeys = listOf("GOOGLE_CLIENT_ID")
            androidKeys.forEach { key ->
                val value = localProperties.getProperty(key)
                value?.let { buildConfigField(FieldSpec.Type.STRING, key, value) }
            }
        }
    }
}
