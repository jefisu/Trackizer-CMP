plugins {
    alias(libs.plugins.trackizer.cmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.util)
        }
    }
}
