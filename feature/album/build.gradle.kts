plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
    alias(libs.plugins.gallery.android.library.compose)
    id("kotlinx-serialization")
}

android {
    namespace = "${libs.versions.appId.get()}.feature.album"
}

dependencies {
    api(project(":core:domain"))
    api(project(":core:model"))
    api(project(":core:ui"))
    testImplementation(project(":core:testing"))
    testImplementation(project(":core:data"))
    androidTestImplementation(project(":core:data"))
    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(project(":ui-test-hilt-manifest"))

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.runtime)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.coil.kt.compose)

    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.compose.ui.testManifest)

    testImplementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.testManifest)
    androidTestImplementation(libs.hilt.android.testing)
}