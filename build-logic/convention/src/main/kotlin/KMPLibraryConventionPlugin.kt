import com.android.build.gradle.LibraryExtension
import com.jefisu.trackizer.configureKotlinAndroid
import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.getVersion
import com.jefisu.trackizer.libs
import com.jefisu.trackizer.modulePackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class KMPLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.getPluginId("androidLibrary"))
            apply<KMPConventionPlugin>()
            apply<KtlintConventionPlugin>()
            apply<DetektConventionPlugin>()
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)

            namespace = libs.getVersion("packageName").plus(".$modulePackageName")

            // The resource prefix is derived from the module name,
            // so resources inside ":core:module1" must be prefixed with "core_module1_"
            resourcePrefix = path
                .split("""\W""".toRegex())
                .drop(1).distinct()
                .joinToString(separator = "_")
                .lowercase() + "_"
        }
    }
}