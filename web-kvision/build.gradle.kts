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
        implementation(npm("sql.js", KVisionDependencies.sqlVersion))
        implementation(devNpm("copy-webpack-plugin", KVisionDependencies.copyWebpackPluginVersion))
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
