plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
    id("com.google.devtools.ksp")
}

dependencies {
    api(project(":core:data"))
    api(project(":core:model"))
    implementation(libs.javax.inject)
}