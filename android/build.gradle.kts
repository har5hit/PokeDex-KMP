/*
 * MIT License
 *
 * Copyright (c) 2020 Harshith Shetty (justadeveloper96@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    // id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

kapt {
    correctErrorTypes = true
}

android {
    compileSdkVersion = AndroidDependencies.SdkVersion.compileSdk
    defaultConfig {
        applicationId = "${ProjectProperties.group}.android"
        minSdk = AndroidDependencies.SdkVersion.minSdk
        targetSdk = AndroidDependencies.SdkVersion.targetSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    buildFeatures {
        dataBinding = true
        compose = true
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AndroidDependencies.Compose.kotlinCompilerVersion
    }
}

dependencies {
    implementation(project(":feature_pokemon_list"))
    implementation(AndroidDependencies.AndroidX.appCompat)
    implementation(AndroidDependencies.Material.core)

    implementation(AndroidDependencies.Dagger.core)
    kapt(AndroidDependencies.Dagger.compiler)
    implementation(AndroidDependencies.Dagger.Hilt.core)
    kapt(AndroidDependencies.Dagger.Hilt.compiler)

    implementation(AndroidDependencies.swipeRefresh)

    implementation(AndroidDependencies.Glide.core)
    kapt(AndroidDependencies.Glide.compiler)

    debugImplementation(AndroidDependencies.Chucker.debug)
    releaseImplementation(AndroidDependencies.Chucker.release)

    implementation(AndroidDependencies.AdapterDelegate.core)
    implementation(AndroidDependencies.AdapterDelegate.layoutcontainer)
    implementation(AndroidDependencies.AdapterDelegate.viewbinding)
    implementation(AndroidDependencies.Compose.compose)
    implementation(AndroidDependencies.Compose.material)
    implementation(AndroidDependencies.Compose.animation)
    implementation(AndroidDependencies.Compose.tooling)
    androidTestImplementation(AndroidDependencies.Compose.uiTesting)
    implementation(AndroidDependencies.Compose.tooling)

    implementation(AndroidDependencies.Coil.compose)
    implementation(AndroidDependencies.swipeRefreshCompose)
    implementation(platform(AndroidDependencies.Firebase.bom))
    implementation(AndroidDependencies.Firebase.analytics)
    implementation(AndroidDependencies.Firebase.Crashlytics.crashlytics)
}
