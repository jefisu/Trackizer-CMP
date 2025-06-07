import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension

class CMPConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.getPluginId("composeMultiplatform"))
            apply(libs.getPluginId("composeCompiler"))
            apply(libs.getPluginId("composeHotReload"))
        }

        val compose = extensions.getByType<ComposeExtension>()

        dependencies {
            with(compose.dependencies) {
                "commonMainImplementation"(runtime)
                "commonMainImplementation"(foundation)
                "commonMainImplementation"(material3)
                "commonMainImplementation"(ui)
                "commonMainImplementation"(components.resources)
                "commonMainImplementation"(components.uiToolingPreview)

                "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-viewmodel").get())
                "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                "commonMainImplementation"(libs.findLibrary("compose-shadow").get())

                "androidMainImplementation"(preview)

                "desktopMainImplementation"(desktop.currentOs)
            }
        }
    }
}