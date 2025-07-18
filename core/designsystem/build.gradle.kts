plugins {
    alias(libs.plugins.trackizer.cmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.util)
            implementation(projects.core.ui)
            implementation(projects.core.domain)
        }
    }
}

compose.resources {
    publicResClass = true
}
