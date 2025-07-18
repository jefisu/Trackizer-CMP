import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KMPKtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            "androidMainImplementation"(libs.findLibrary("ktor-client-android").get())
            "iosMainImplementation"(libs.findLibrary("ktor-client-darwin").get())
        }
    }
}