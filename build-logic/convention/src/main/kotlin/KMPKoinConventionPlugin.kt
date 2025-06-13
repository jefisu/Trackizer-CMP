import com.google.devtools.ksp.gradle.KspExtension
import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KMPKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply<KMPKspConventionPlugin>()
        }

        extensions.configure<KspExtension> {
            arg("KOIN_CONFIG_CHECK", "true")
            arg("KOIN_DEFAULT_MODULE", "true")
            arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
        }

        dependencies {
            "commonMainImplementation"(platform(libs.findLibrary("koin-bom").get()))
            "commonMainImplementation"(libs.findLibrary("koin-core").get())
            "commonMainImplementation"(libs.findLibrary("koin-compose").get())
            "commonMainImplementation"(libs.findLibrary("koin-compose-viewmodel").get())
            "commonMainImplementation"(libs.findLibrary("koin-compose-viewmodel-navigation").get())

            "commonMainImplementation"(platform(libs.findLibrary("koin-annotations-bom").get()))
            "commonMainImplementation"(libs.findLibrary("koin-annotations").get())

            "androidMainImplementation"(libs.findLibrary("koin-android").get())

            "kspCommonMainMetadata"(libs.findLibrary("koin-ksp-compiler").get())
        }
    }
}