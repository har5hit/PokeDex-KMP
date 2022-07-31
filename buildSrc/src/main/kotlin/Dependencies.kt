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

object Config {

    object Test {
        const val coverageEnabled = false
    }

    object Jvm {
        const val target = "1.8"
    }
}

object Dependencies {
    object Kotlin {
        const val version = "1.7.10"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Kotest {
        const val version = "5.4.1"

        object Common {
            const val property = "io.kotest:kotest-property:$version"
            const val assertions = "io.kotest:kotest-assertions-core:$version"
            const val engine = "io.kotest:kotest-framework-engine:$version"
        }

        object Android {
            const val runner = "io.kotest:kotest-runner-junit5:$version"
        }
    }

    object Mockk {
        private const val version = "1.12.5"

        object Common {
            const val core = "io.mockk:mockk-common:$version"
        }

        object Android {
            const val core = "io.mockk:mockk:$version"
        }
    }

    object Coroutines {
        private const val version = "1.6.3"
        const val versionNativeMT = "${version}-native-mt"

        object Common {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${versionNativeMT}"
        }

        object Android {
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${version}"
        }
    }

    object Ktor {
        private const val version = "1.6.8"

        object Common {
            const val core = "io.ktor:ktor-client-core:$version"
            const val serialization = "io.ktor:ktor-client-serialization:$version"
            const val logging = "io.ktor:ktor-client-logging:$version"
        }

        object Android {
            const val okhttp = "io.ktor:ktor-client-okhttp:$version"
            const val client = "io.ktor:ktor-client-android:$version"
        }

        object iOS {
            const val client = "io.ktor:ktor-client-ios:$version"
        }
    }

    object SqlDelight {
        private const val version = "1.5.3"

        const val plugin = "com.squareup.sqldelight:gradle-plugin:$version"

        object Common {
            const val runtime = "com.squareup.sqldelight:runtime:$version"
            const val coroutinesExtension = "com.squareup.sqldelight:coroutines-extensions:$version"
        }

        object Android {
            const val driver = "com.squareup.sqldelight:android-driver:$version"
        }

        object iOS {
            const val driver = "com.squareup.sqldelight:native-driver:$version"
        }
    }

    object Ktlint {
        const val version = "0.45.2"
        const val pluginVersion = "10.0.0"
    }

    object Serialization {
        const val version = "1.7.10"
    }

    object Kermit {
        private const val version = "1.1.3"
        const val kermit = "co.touchlab:kermit:$version"
        const val crashlytics = "co.touchlab:kermit-crashlytics:$version"
    }
}