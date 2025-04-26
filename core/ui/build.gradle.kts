plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
    alias(libs.plugins.gallery.android.library.compose)
}

dependencies {
    api(project(":core:model"))
    api(project(":core:designsystem"))

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
}