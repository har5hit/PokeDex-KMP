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
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
}

version = ProjectProperties.version
group = ProjectProperties.group

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Dependencies.Coroutines.Common.core)
                api(Dependencies.Ktor.Common.core)
                api(Dependencies.Ktor.Common.logging)
                api(Dependencies.Ktor.Common.contentNegotiation)
                api(Dependencies.Ktor.Common.serialization)
                api(Dependencies.SqlDelight.Common.runtime)
                api(Dependencies.SqlDelight.Common.coroutinesExtension)
                api(Dependencies.Koin.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(Dependencies.Kotest.Common.engine)
                implementation(Dependencies.Kotest.Common.property)
                implementation(Dependencies.Kotest.Common.assertions)
                implementation(Dependencies.Mockk.Common.core)
                implementation(Dependencies.Coroutines.Common.core)
            }
        }
        val androidMain by getting {
            dependencies {
                api(AndroidDependencies.AndroidX.Lifecycle.runtime)
                api(AndroidDependencies.AndroidX.Lifecycle.viewmodel)
                api(AndroidDependencies.Material.core)
                api(Dependencies.Ktor.Android.client)
                api(Dependencies.Ktor.Android.okhttp)
                api(Dependencies.SqlDelight.Android.driver)
                api(AndroidDependencies.AndroidX.core)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation(Dependencies.Kotest.Android.runner)
                implementation(Dependencies.Mockk.Android.core)
                implementation(Dependencies.Coroutines.Android.test)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                api(Dependencies.Ktor.iOS.client)
                api(Dependencies.SqlDelight.iOS.driver)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        val jsMain by getting {
            dependencies {
                api(Dependencies.Ktor.Js.client)
                api(Dependencies.SqlDelight.Js.driver)
            }
        }
    }
}

android {
    compileSdkVersion = AndroidDependencies.SdkVersion.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AndroidDependencies.SdkVersion.minSdk
        targetSdk = AndroidDependencies.SdkVersion.targetSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = Config.Jvm.target
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    buildFeatures {
        dataBinding = true
    }
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = Config.Test.coverageEnabled
        }
    }

    packagingOptions {
        exclude("**/attach_hotspot_windows.dll")
        exclude("META-INF/**.md")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/licenses/**")
    }
}
apply(from = "$rootDir/plugins/publish.gradle")
