import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import com.jefisu.trackizer.configureBadgingTasks
import com.jefisu.trackizer.configureKotlinAndroid
import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class CMPApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.getPluginId("androidApplication"))
            apply<KMPConventionPlugin>()
            apply<CMPConventionPlugin>()
            apply<KtlintConventionPlugin>()
            apply<DetektConventionPlugin>()
            apply<DependencyGuardConventionPlugin>()
            apply<GitHooksConventionPlugin>()
            apply<KMPKoinConventionPlugin>()
            apply<KMPBuildKonfigConventionPlugin>()
            apply<KMPFirebaseConventionPlugin>()
            apply(libs.getPluginId("kotlinxSerialization"))
            apply(libs.getPluginId("googleServices"))
        }

        with(extensions) {
            configure<ApplicationExtension>(::configureKotlinAndroid)
            configure<ApplicationAndroidComponentsExtension> {
                configureBadgingTasks(extensions.getByType<BaseExtension>(), this)
            }
        }

        dependencies {
            "commonMainImplementation"(libs.findLibrary("compose-navigation").get())
            "commonMainImplementation"(libs.findLibrary("kotlinx-serialization").get())

            "androidMainImplementation"(libs.findLibrary("androidx-activity-compose").get())

            "commonTestImplementation"(project(":testUtil"))
        }
    }
}