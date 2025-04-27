plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
}

android {
    namespace = "${libs.versions.appId.get()}.core.testing"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(libs.dataStore.core)
    implementation(libs.dataStore)
    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android.testing)
    implementation(libs.androidx.test.rules)
    implementation(libs.kotlinx.coroutines.test)
}