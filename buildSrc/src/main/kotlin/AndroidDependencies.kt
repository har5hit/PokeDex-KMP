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
        private const val version = "2.6.0"
        const val runtime = "androidx.work:work-runtime:$version"
        const val coroutine = "androidx.work:work-runtime-ktx:$version"
    }

    object Dagger {
        private const val version = "2.41"
        const val core = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"

        object Hilt {
            private const val version = "2.41"

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
        private const val version = "4.3.0"
        const val core = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:$version"
        const val layoutcontainer =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:$version"
        const val viewbinding =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:$version"
    }

    object AndroidX {
        object Lifecycle {
            private const val version = "2.3.1"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        const val core = "androidx.core:core-ktx:1.5.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.5"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val appCompat = "androidx.appcompat:appcompat:1.3.0"
    }

    object Material {
        private const val version = "1.3.0"
        const val core = "com.google.android.material:material:$version"
    }

    object Firebase {
        val plugin = "com.google.firebase:firebase-crashlytics-gradle:2.7.1"
        val bom = "com.google.firebase:firebase-bom:28.2.0"
        val analytics = "com.google.firebase:firebase-analytics-ktx"
        val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    }

    object Chucker {
        const val version = "3.5.2"
        val debug = "com.github.chuckerteam.chucker:library:$version"
        val release = "com.github.chuckerteam.chucker:library-no-op:$version"
    }


    object Google {
        object Services {
            val plugin = "com.google.gms:google-services:4.3.8"
        }
    }

    object Gradle {
        const val plugin = "com.android.tools.build:gradle:7.0.4"
    }

    object Glide {
        private const val version = "4.12.0"

        const val core = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object SdkVersion {
        const val minSdk = 23
        const val targetSdk = 32
        const val compileSdk = "android-32"
    }

    object OkHttp {
        val okhttp = "com.squareup.okhttp3:okhttp:4.9.2"
    }

    val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

}