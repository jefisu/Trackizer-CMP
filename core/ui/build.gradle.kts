plugins {
    alias(libs.plugins.trackizer.cmp.library)
}

compose.resources {
    publicResClass = true
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.util)
            implementation(projects.core.domain)
        }
    }
}
