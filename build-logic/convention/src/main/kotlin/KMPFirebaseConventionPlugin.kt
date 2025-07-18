import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KMPFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.getPluginId("kotlinxSerialization"))
        }
        dependencies {
            "commonMainImplementation"(libs.findLibrary("firebase-common").get())
            "commonMainImplementation"(libs.findLibrary("firebase-auth").get())
            "commonMainImplementation"(libs.findLibrary("firebase-firestore").get())

            // Added to avoid Ktlint formatting task crashing when running.
            "androidMainImplementation"(platform(libs.findLibrary("firebase-bom-android").get()))
        }
    }
}