plugins {
    alias(libs.plugins.trackizer.kmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.util)
        }
    }
}
