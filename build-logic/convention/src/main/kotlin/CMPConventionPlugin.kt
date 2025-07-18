import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

class CMPConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.getPluginId("composeMultiplatform"))
            apply(libs.getPluginId("composeCompiler"))
            apply(libs.getPluginId("composeHotReload"))
            apply<KMPKtorConventionPlugin>()
        }

        configureAndroidUiTest()

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
                "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
                "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                "commonMainImplementation"(libs.findLibrary("compose-shadow").get())
                "commonMainImplementation"(libs.findLibrary("composeIcons-feather").get())
                "commonMainImplementation"(libs.findLibrary("compose-unstyled").get())

                "commonMainImplementation"(libs.findLibrary("coil-compose").get())
                "commonMainImplementation"(libs.findLibrary("coil-test").get())
                "commonMainImplementation"(libs.findLibrary("coil-network-ktor3").get())

                "debugImplementation"(uiTooling)

                "androidMainImplementation"(preview)

                "desktopMainImplementation"(desktop.currentOs)

                @OptIn(ExperimentalComposeLibrary::class)
                "commonTestImplementation"(uiTest)
            }
        }
    }
}

private fun Project.configureAndroidUiTest() {
    extensions.configure<KotlinMultiplatformExtension> {
        androidTarget {
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)

            dependencies {
                "androidTestImplementation"(libs.findLibrary("compose-ui-test-junit4-android").get())
                "debugImplementation"(libs.findLibrary("compose-ui-test-manifest").get())
            }
        }
    }
}