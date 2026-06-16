/*
 * MIT License
 *
 * Copyright (c) 2020 Harshith Shetty (hshetty.biz@gmail.com)
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
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)

}

version = ProjectProperties.version
group = ProjectProperties.group

kotlin {
    android {
        namespace = "com.justadeveloper96.pokedex_kmp.helpers"
        compileSdk = libs.versions.android.compile.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()
        withHostTest {}
    }
    iosArm64()
    iosSimulatorArm64()
    js {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.cmp.compose.runtime)
                api(libs.kotlinx.coroutines.core)
                api(libs.ktor.client.core)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.client.logging)
                api(libs.ktor.serialization.kotlinx.json)
                api(libs.sqldelight.runtime)
                api(libs.sqldelight.coroutines.extensions)
                api(libs.koin.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.property)
                implementation(libs.kotest.assertions.core)
                implementation(libs.mockk.common)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.androidx.lifecycle.runtime.ktx)
                api(libs.androidx.lifecycle.viewmodel.ktx)
                api(libs.material)
                api(libs.ktor.client.android)
                api(libs.ktor.client.okhttp)
                api(libs.sqldelight.android.driver)
                api(libs.androidx.core.ktx)
                implementation(libs.androidx.compose.runtime)
                implementation(libs.androidx.compose.foundation)
                implementation(libs.androidx.compose.material)

            }
        }
        val androidHostTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit)
                implementation(libs.kotest.runner.junit5)
                implementation(libs.mockk.android)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val iosMain by creating {
            dependencies {
                api(libs.ktor.client.darwin)
                api(libs.sqldelight.native.driver)
            }
        }

        val jsMain by getting {
            dependencies {
                api(libs.ktor.client.js)
            }
        }
    }
}
apply(from = "$rootDir/plugins/publish.gradle")
