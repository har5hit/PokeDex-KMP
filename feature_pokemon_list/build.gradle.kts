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
    id("com.squareup.sqldelight")
}

version = ProjectProperties.version
group = ProjectProperties.group

kotlin {
    android()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                api(Dependencies.SqlDelight.Common.runtime)
                api(Dependencies.SqlDelight.Common.coroutinesExtension)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":core"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(project(":core"))
                api(Dependencies.SqlDelight.Android.driver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(project(":core"))
                implementation(Dependencies.Kotest.Common.property)
                implementation(Dependencies.Kotest.Common.assertions)
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
                api(project(":core"))
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
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdkVersion = AndroidDependencies.SdkVersion.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AndroidDependencies.SdkVersion.minSdk
        targetSdk = AndroidDependencies.SdkVersion.targetSdk
        consumerProguardFiles("proguard-consumer-rules.pro")
    }
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = Config.Test.coverageEnabled
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
sqldelight {
    database("PokemonDatabase") {
        packageName = "${ProjectProperties.group}.feature_pokemon_list"
        verifyMigrations = true
        schemaOutputDirectory = file("src/commonMain/sqldelight/databases")
    }
}
apply(from = "$rootDir/plugins/publish.gradle")
