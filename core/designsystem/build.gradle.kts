plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
    alias(libs.plugins.gallery.android.library.compose)
}

android {
    namespace = "${libs.versions.appId.get()}.core.designsystem"
}

dependencies {

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.runtime)
    implementation(libs.coil.kt.compose)

    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.testManifest)
   // androidTestImplementation(libs.androidx.test.ext.junit)
}