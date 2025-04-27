plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
    id("com.google.devtools.ksp")
}

android {
    namespace = "${libs.versions.appId.get()}.core.domain"
}

dependencies {
    api(project(":core:data"))
    api(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:testing"))

    implementation(libs.javax.inject)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}