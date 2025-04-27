import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.shamweel.gallery.configureFlavors
import com.shamweel.gallery.configureKotlinAndroid
import com.shamweel.gallery.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                        isReturnDefaultValues = true
                    }
                }
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("appTargetSdk").get().toString().toInt()
                defaultConfig.testInstrumentationRunner = "com.shamweel.gallery.core.testing.testing.AppTestRunner"
                testOptions.animationsDisabled = true
                configureFlavors(this)
                resourcePrefix =
                    path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_")
                        .lowercase() + "_"
            }
            dependencies {
                "androidTestImplementation"(libs.findLibrary("kotlin.test").get())
                "testImplementation"(libs.findLibrary("kotlin.test").get())
            }
        }
    }
}
