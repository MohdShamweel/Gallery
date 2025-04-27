plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
    alias(libs.plugins.gallery.android.library.compose)
}

android {
    namespace = "${libs.versions.appId.get()}.core.ui"
}

dependencies {
    api(project(":core:model"))
    api(project(":core:designsystem"))

    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.gif.compose)
    implementation(libs.androidx.media)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
}