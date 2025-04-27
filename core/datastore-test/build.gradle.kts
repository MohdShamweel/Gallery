plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
}

android {
    namespace = "${libs.versions.appId.get()}.core.datastore_test"
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:common"))
    implementation(libs.dataStore.core)
    implementation(libs.dataStore)
    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android.testing)
}