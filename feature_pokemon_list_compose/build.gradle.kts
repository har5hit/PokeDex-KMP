/*
 * MIT License
 *
 * Copyright (c) 2026 Harshith Shetty (hshetty.biz@gmail.com)
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
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
}

version = ProjectProperties.version
group = ProjectProperties.group

kotlin {
    android {
        namespace = "com.justadeveloper96.pokedex_kmp.feature_pokemon_list_compose"
        compileSdk = libs.versions.android.compile.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()
    }
    iosArm64()
    iosSimulatorArm64()
    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":feature_pokemon_list"))
            implementation(libs.cmp.compose.runtime)
            implementation(libs.cmp.compose.foundation)
            implementation(libs.cmp.compose.ui)
            implementation(libs.cmp.compose.material3)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
        }
    }
}
