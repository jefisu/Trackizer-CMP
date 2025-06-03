import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class DependencyGuardConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.getPluginId("dependencyGuard"))

        extensions.configure<DependencyGuardPluginExtension> {
            configuration("releaseRuntimeClasspath") {
                modules = true
                tree = true
            }
        }
    }
}