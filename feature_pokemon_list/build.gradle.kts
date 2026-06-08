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
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
}

version = ProjectProperties.version
group = ProjectProperties.group

kotlin {
    android {
        namespace = "com.justadeveloper96.pokedex_kmp.feature_pokemon_list"
        compileSdk = libs.versions.android.compile.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()
        withHostTest {

        }
    }
    iosArm64()
    iosSimulatorArm64()
    js {
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                api(libs.sqldelight.runtime)
                api(libs.sqldelight.coroutines.extensions)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":core"))
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.property)
                implementation(libs.kotest.assertions.core)
            }
        }
        val androidMain by getting {
            dependencies {
                api(project(":core"))
                api(libs.sqldelight.android.driver)
            }
        }
        val androidHostTest by getting {
            dependencies {
                implementation(project(":core"))
                implementation(libs.kotest.runner.junit5)
                implementation(libs.mockk.android)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val iosMain by creating {
            dependencies {
                api(project(":core"))
                api(libs.sqldelight.native.driver)
            }

        }

        val jsMain by getting {
            dependencies {
                api(project(":core"))
            }
        }
    }
}
sqldelight {
    linkSqlite = true

    databases {
        create("PokemonDatabase") {
            packageName.set("${ProjectProperties.group}.feature_pokemon_list")
            verifyMigrations.set(true)
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
        }
    }
}
apply(from = "$rootDir/plugins/publish.gradle")
