import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.libs
import com.jefisu.trackizer.moduleName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.getPluginId("kotlinMultiplatform"))

        configureKotlinMultiplatform()
    }
}

private fun Project.configureKotlinMultiplatform() {
    extensions.configure<KotlinMultiplatformExtension> {
        applyDefaultHierarchyTemplate()

        androidTarget()

        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = moduleName
                isStatic = true
            }
        }

        jvm("desktop")

        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
    }
}