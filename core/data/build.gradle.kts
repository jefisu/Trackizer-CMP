plugins {
    alias(libs.plugins.trackizer.kmp.library)
    alias(libs.plugins.trackizer.kmp.koin)
    alias(libs.plugins.trackizer.kmp.firebase)
}

dependencies {
    commonMainImplementation(projects.core.domain)
}
