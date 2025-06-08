import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class CMPFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply<KMPLibraryConventionPlugin>()
            apply<CMPConventionPlugin>()
            apply<KtlintConventionPlugin>()
            apply<DetektConventionPlugin>()
        }

        dependencies {
            "commonMainImplementation"(project(":core:designsystem"))
            "commonMainImplementation"(project(":core:util"))
            "commonMainImplementation"(project(":core:ui"))
        }
    }
}