plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "${libs.versions.appId.get()}.core.data"
}

dependencies {
    api(project(":core:common"))
    api(project(":core:model"))
    implementation(project(":core:media"))
    implementation(project(":core::datastore"))
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
}