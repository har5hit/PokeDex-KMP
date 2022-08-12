/*
 * MIT License
 *
 * Copyright (c) 2022 Harshith Shetty (justadeveloper96@gmail.com)
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

import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("plugin.serialization")
    kotlin("js")
    id("io.kvision") version KVisionDependencies.kVisionVersion
}

version = ProjectProperties.version
group = ProjectProperties.group

val webDir = file("src/main/web")

kotlin {
    js(IR) {
        browser {
            runTask {
                outputFileName = "main.bundle.js"
                sourceMaps = false
                devServer = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    proxy = mutableMapOf(
                        "/kv/*" to "http://localhost:8080",
                        "/kvws/*" to mapOf("target" to "ws://localhost:8080", "ws" to true)
                    ),
                    static = mutableListOf("$buildDir/processedResources/js/main")
                )
            }
            webpackTask {
                outputFileName = "main.bundle.js"
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
            distribution {
                directory = file("$projectDir/dist/")
            }
        }
        binaries.executable()
    }
    sourceSets["main"].dependencies {
        implementation(project(":feature_pokemon_list"))
        implementation(KVisionDependencies.core)
        implementation(KVisionDependencies.state)
        implementation(KVisionDependencies.stateFlow)
        implementation(KVisionDependencies.toast)
        implementation(KVisionDependencies.onsenui)
        implementation(KVisionDependencies.fontawesome)
        implementation(
            devNpm(
                "compression-webpack-plugin",
                KVisionDependencies.compressionWebpackPluginVersion
            )
        )
        implementation(devNpm("zlib", KVisionDependencies.zlibVersion))
    }
    sourceSets["test"].dependencies {
        implementation(kotlin("test-js"))
        implementation(KVisionDependencies.testutils)
    }
    sourceSets["main"].resources.srcDir(webDir)
}
