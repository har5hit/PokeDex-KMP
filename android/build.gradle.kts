

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
}

kapt {
    correctErrorTypes = true
    javacOptions {
        // These options are normally set automatically via the Hilt Gradle plugin, but we
        // set them manually to workaround a bug in the Kotlin 1.5.20
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }
}

android {
    compileSdkVersion = AndroidDependencies.SdkVersion.compileSdk
    defaultConfig {
        applicationId = "com.justadeveloper96.pokedex_kmp.android"
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
    }
    tasks.withType<Test> {
        useJUnitPlatform()
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
}
