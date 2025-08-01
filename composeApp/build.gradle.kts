import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.trackizer.cmp.application)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.util)
            implementation(projects.core.platform)

            implementation(projects.feature.welcome)
            implementation(projects.feature.auth)
            implementation(projects.feature.home)
            implementation(projects.feature.addSubscription)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.splashscreen)
        }
    }
}

android {
    namespace = libs.versions.packageName.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.packageName.get()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.application.version.get().filter(Char::isDigit).toInt()
        versionName = libs.versions.application.version.get()

        val localProperties = loadProperties("local.properties")
        manifestPlaceholders["FACEBOOK_APPLICATION_ID"] =
            localProperties["FACEBOOK_APPLICATION_ID"] ?: ""
        manifestPlaceholders["FACEBOOK_CLIENT_TOKEN"] =
            localProperties["FACEBOOK_CLIENT_TOKEN"] ?: ""
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

compose.desktop {
    application {
        mainClass = "${libs.versions.packageName.get()}.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = libs.versions.packageName.get()
            packageVersion = libs.versions.application.version.get()
        }
    }
}
