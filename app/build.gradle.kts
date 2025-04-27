import com.shamweel.gallery.AppBuildType

plugins {
    alias(libs.plugins.gallery.android.application)
    alias(libs.plugins.gallery.android.application.flavor)
    alias(libs.plugins.gallery.android.application.compose)
    alias(libs.plugins.gallery.android.hilt)
}

android {
    namespace = libs.versions.appId.get()

    signingConfigs {
        create("release") {
            keyAlias = "gallery"
            keyPassword = "#GALLERY_APP#"
            storeFile = file("../gallery.jks")
            storePassword = "#GALLERY_APP#"
        }
    }

    defaultConfig {
        applicationId = libs.versions.appId.get()
        testInstrumentationRunner = "com.shamweel.gallery.core.testing.testing.AppTestRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = AppBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":feature:gallery"))
    implementation(project(":feature:album"))
    implementation(project(":feature:mediapager"))
    androidTestImplementation(project(":core:testing"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.testManifest)
}