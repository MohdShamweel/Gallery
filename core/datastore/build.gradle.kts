plugins {
    alias(libs.plugins.gallery.android.library)
    alias(libs.plugins.gallery.android.hilt)
    id("kotlinx-serialization")
    id("com.google.protobuf")
}

android {
    namespace = "${libs.versions.appId.get()}.core.datastore"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.2"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}



dependencies {
    api(project(":core:common"))
    api(project(":core:model"))
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.dataStore.core)
    implementation(libs.dataStore)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
}