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

object AndroidDependencies {

    object WorkManager {
        private const val version = "2.7.1"
        const val runtime = "androidx.work:work-runtime:$version"
        const val coroutine = "androidx.work:work-runtime-ktx:$version"
    }

    object Dagger {
        private const val version = "2.43"
        const val core = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"

        object Hilt {
            private const val version = "2.43"

            const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
            const val core = "com.google.dagger:hilt-android:$version"
            const val compiler = "com.google.dagger:hilt-compiler:$version"

            object WorkManager {
                const val work = "androidx.hilt:hilt-work:1.0.0"
                const val compiler = "androidx.hilt:hilt-compiler:1.0.0"
            }
        }
    }

    object AdapterDelegate {
        private const val version = "4.3.2"
        const val core = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:$version"
        const val layoutcontainer =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:$version"
        const val viewbinding =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:$version"
    }

    object AndroidX {
        object Lifecycle {
            private const val version = "2.5.0"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        const val core = "androidx.core:core-ktx:1.8.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.5.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val appCompat = "androidx.appcompat:appcompat:1.4.2"
    }

    object Material {
        private const val version = "1.6.1"
        const val core = "com.google.android.material:material:$version"
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:30.3.1"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        object Crashlytics {
            const val plugin = "com.google.firebase:firebase-crashlytics-gradle:2.9.1"
            const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        }
    }

    object Chucker {
        const val version = "3.5.2"
        const val debug = "com.github.chuckerteam.chucker:library:$version"
        const val release = "com.github.chuckerteam.chucker:library-no-op:$version"
    }

    object Google {
        object Services {
            const val plugin = "com.google.gms:google-services:4.3.13"
        }
    }

    object Gradle {
        const val plugin = "com.android.tools.build:gradle:7.2.1"
    }

    object Glide {
        private const val version = "4.13.2"

        const val core = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object SdkVersion {
        const val minSdk = 23
        const val targetSdk = 32
        const val compileSdk = "android-32"
        const val compileSdkInt = 32
    }

    val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    object Compose {
        private const val version = "1.2.0"
        const val kotlinCompilerVersion = "1.3.0-rc01"
        const val compose = "androidx.activity:activity-compose:1.4.0"
        const val material = "androidx.compose.material:material:$version"
        const val animation = "androidx.compose.animation:animation:$version"
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
        const val uiTesting = "androidx.compose.ui:ui-test-junit4:$version"

    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:2.1.0"
    }

    const val swipeRefreshCompose = "com.google.accompanist:accompanist-swiperefresh:0.26.0-alpha"
}