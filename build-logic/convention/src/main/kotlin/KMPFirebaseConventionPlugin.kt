import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KMPFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            "commonMainImplementation"(libs.findLibrary("firebase-common").get())
            "commonMainImplementation"(libs.findLibrary("firebase-auth").get())

            "androidMainImplementation"(platform(libs.findLibrary("firebase-bom-android").get()))
            "androidMainImplementation"(libs.findLibrary("firebase-common-android").get())
            "androidMainImplementation"(libs.findLibrary("firebase-auth-android").get())
        }
    }
}