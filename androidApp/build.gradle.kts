plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.example.basekmm_003.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        dataBinding = true
        compose = true
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("io.insert-koin:koin-android:3.2.0")

    implementation("io.insert-koin:koin-android-compat:3.2.0")
    implementation("io.insert-koin:koin-androidx-workmanager:3.2.0")
    implementation("io.insert-koin:koin-androidx-navigation:3.2.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.1")

    // Architectural Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.4.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    implementation("androidx.compose.compiler:compiler:1.2.0-beta03")
    implementation("androidx.compose.material:material:1.2.0-beta03")
    implementation("androidx.compose.runtime:runtime-livedata:1.2.0-beta03")
    implementation("androidx.compose.ui:ui:1.2.0-beta03")
    implementation ("androidx.compose.ui:ui-tooling:1.2.0-beta03")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0-beta03")
    implementation ("androidx.compose.ui:ui:1.2.0-beta03")
    implementation ("io.coil-kt:coil:1.4.0")
    implementation ("io.coil-kt:coil-compose:1.4.0")
}
