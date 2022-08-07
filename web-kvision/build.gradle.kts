import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("plugin.serialization")
    kotlin("js")
    id("io.kvision") version KVisionDependencies.version
}

version = ProjectProperties.version
group = ProjectProperties.group

// Versions

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
        implementation("io.kvision:kvision:${KVisionDependencies.version}")
        implementation("io.kvision:kvision-state:${KVisionDependencies.version}")
        implementation("io.kvision:kvision-state-flow:${KVisionDependencies.version}")
        implementation("io.kvision:kvision-toast:${KVisionDependencies.version}")
        implementation("io.kvision:kvision-onsenui:${KVisionDependencies.version}")
        implementation("io.kvision:kvision-onsenui:${KVisionDependencies.version}")
        implementation("io.kvision:kvision-fontawesome:${KVisionDependencies.version}")
        implementation(npm("sql.js", "1.6.2"))
        implementation(devNpm("copy-webpack-plugin", "9.1.0"))
    }
    sourceSets["test"].dependencies {
        implementation(kotlin("test-js"))
        implementation("io.kvision:kvision-testutils:${KVisionDependencies.version}")
    }
    sourceSets["main"].resources.srcDir(webDir)
}
