plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
}

android {
    namespace = "${libs.versions.appId.get()}.ui_test_hilt_manifest"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}