plugins {
    alias(libs.plugins.trackizer.kmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.util)
        }
    }
}
