import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import com.jefisu.trackizer.getPluginId
import com.jefisu.trackizer.getVersion
import com.jefisu.trackizer.libs
import com.jefisu.trackizer.modulePackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class KMPBuildKonfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.getPluginId("buildKonfig"))

        extensions.configure<BuildKonfigExtension> {
            packageName = libs.getVersion("packageName").plus(".$modulePackageName")
            defaultConfigs { }
        }
    }
}

fun Project.loadProperties(fileName: String): Properties {
    val properties = Properties()
    val file = rootProject.file(fileName)
    if (file.exists()) {
        file.inputStream().use(properties::load)
    }
    return properties
}