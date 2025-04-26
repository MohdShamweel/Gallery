plugins {
    alias(libs.plugins.gallery.android.library)
}

android {
    namespace = "${libs.versions.appId.get()}.core.model"
}

dependencies {
    api(project(":core:common"))
}