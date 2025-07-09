import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KMPKspConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.getPluginId("ksp"))

        // KSP Common sourceSet
        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain {
                kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            }
        }

        // Trigger Common Metadata Generation from Native tasks
        project.tasks.configureEach {
            val kspTask = project.tasks.findByName("kspCommonMainKotlinMetadata") ?: return@configureEach
            if (this is KotlinCompilationTask<*> || name.contains("runKtlint")) {
                dependsOn(kspTask)
            }
        }
    }
}