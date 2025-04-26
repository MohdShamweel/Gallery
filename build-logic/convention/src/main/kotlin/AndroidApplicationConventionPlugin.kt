import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import com.shamweel.gallery.configureKotlinAndroid
import com.shamweel.gallery.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    versionCode = libs.findVersion("appVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("appVersionName").get().toString()
                    targetSdk = libs.findVersion("appTargetSdk").get().toString().toInt()
                }
                configureKotlinAndroid(this)
                @Suppress("UnstableApiUsage")
                testOptions.animationsDisabled = true
            }
        }
    }
}
