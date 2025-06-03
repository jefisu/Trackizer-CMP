import com.jefisu.trackizer.configureDetekt
import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.libs
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.getPluginId("detekt"))

        extensions.configure<DetektExtension> {
            configureDetekt(this)
        }
    }
}