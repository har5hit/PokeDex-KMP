# PokeDex KMP
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Android CI](https://github.com/har5hit/PokeDex-KMP/actions/workflows/android_ci.yml/badge.svg)](https://github.com/har5hit/PokeDex-KMP/actions/workflows/android_ci.yml)
[![Web CI-CD](https://github.com/har5hit/PokeDex-KMP/actions/workflows/web_ci_cd.yml/badge.svg)](https://github.com/har5hit/PokeDex-KMP/actions/workflows/web_ci_cd.yml)
[![](https://androidweekly.net/issues/issue-528/badge)](https://androidweekly.net/issues/issue-528)

A demo application demonstrating shared business logic and reusable Compose Multiplatform UI across Android, iOS, and Web using [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) (KMP).
<br/>
Specifications:
* 100% [Kotlin](https://kotlinlang.org/) for common Kotlin code, Android, and Web modules.
* 100% [Swift](https://www.swift.org/) for iOS.
* Kotlin: [2.4.0](https://kotlinlang.org/docs/releases.html).
* Gradle: 9.5.1.
* Test Driven Development (TDD).
* MVVM design pattern.
* Multi Module KMP Application Setup.
* Unit Testing via [Kotest](https://github.com/kotest/kotest) and [Mockk](https://mockk.io/).
* Multiplatform Dependency Injection using [Koin](https://insert-koin.io/).
* Multiplatform Concurrency using [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines).
* Multiplatform Database using [SQLDelight](https://github.com/cashapp/sqldelight).
* Multiplatform Network Client using [Ktor](https://ktor.io/).
* [Ktlint](https://ktlint.github.io/) for lint.
* [Jetpack Compose](https://developer.android.com/jetpack/compose) and native Android views for Android.
* [SwiftUI](https://developer.apple.com/xcode/swiftui/) for iOS.
* [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) for reusable shared UI and Web. [Project Live Demo](https://har5hit.github.io/PokeDex-KMP/).
* CI-CD using [Github Actions](https://github.com/features/actions).
* [Kermit](https://github.com/touchlab/Kermit) - Better iOS Crash Report Logging on Kotlin code crashes.
* KMP Modules Deployment as libraries for external `Android`, `iOS` and `JavaScript` apps.
* Firebase Crashlytics. Add `google-services.json` for Android and `GoogleService-Info.plist` for iOS.

[Blog for more details](https://har5hitn95.medium.com/experience-using-kmp-in-production-apps-for-a-year-2474406d99d4)

# Why KMP?
All advantages and freedom of native development + code re-usability in other platforms.

# Source Line of Code Stats:

Generated using `scc` code lines, excluding build outputs.

## Shared Modules (KMP)
| Module              | sloc count |
|----------------------|-----|
| helpers              | 477 |
| core                 | 419 |
| feature_pokemon_list | 554 |
| feature_pokemon_list_compose | 181 |
| **Total** | **1631** |


## Host Apps (Containing views and di code)
| App              | sloc count | exclusive code % | common code % |
|----------------------|-----|-----|-----|
| android              | 396 | 19.52% | **80.48%**|
| ios                  | 285 | 14.89% | **85.11%**|
| web-compose          | 205 | 11.16% | **88.84%**|

Around **~80%** code written for a feature can be reused for other platform using KMP.

# Screenshots

## Android
Native UI             |  Shared CMP UI
:-------------------------:|:-------------------------:
![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_android_compose.png?raw=true)  |  ![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_android_cmp.png?raw=true)

## iOS
Native UI             |  Shared CMP UI
:-------------------------:|:-------------------------:
![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_ios_swiftui.png?raw=true)  |  ![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_ios_cmp.png?raw=true)

## Web
Compose Multiplatform UI
:-------------------------:
![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_web.png?raw=true)


# Architecture

![Overview](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/architecture.svg?raw=true)

# Module Structure
* helpers - Cross-platform utilities and reusable primitives.
* core - Shared app-level infrastructure and common services used by feature modules.
* feature_* - Feature modules containing shared data and presentation logic.
* feature_*_compose - Reusable Compose Multiplatform UI modules.
* iosUmbrellaModule - Umbrella module for iOS. This module aggregates and exports KMP modules as a single framework for the iOS app.
* android - Android host app.
* ios - iOS host app.
* web-compose - Compose Multiplatform web host app.
* feature_pokemon_list_compose - Shared Compose Multiplatform UI for Android, iOS, and Web reuse.

# UI Strategy
* Web uses Compose Multiplatform only.
* Android exposes two Pokémon list experiences through bottom navigation:
  * Native Android UI.
  * Shared Compose Multiplatform UI reuse.
* iOS exposes two Pokémon list experiences through tabs:
  * Native SwiftUI UI.
  * Shared Compose Multiplatform UI reuse.
* Shared CMP screens are platform-route agnostic. For example, `PokemonListScreenCmp(state, onAction)` renders UI while routing remains owned by each host platform.
* CMP composables use the `Cmp` suffix, such as `PokemonListScreenCmp` and `PokemonListItemViewCmp`, while native host views keep plain names.


# Application Structure

Each individual feature folder structure

* **/data** (All data related files)
  * /entity_1 (for e.g: Person)
    * /model
    * /dao
    * /network
    * repository

* **/presentation** (All view related files)
  * /component_1 (for e.g: Person List)
    * /fragment
    * /viewmodel
    * /adapter
    * /view

# Plugins
Install following plugins from `Preferences -> Plugins`
- Kotlin Multiplatform Mobile.
- Kotest

# Tooling
* Prefer JDK 21 for Gradle/Kotlin builds. Running Gradle with JDK 26 can trigger Kotlin JVM target fallback warnings because Kotlin does not yet target JVM 26.
* Web development expects Node to be available on `PATH`. If Homebrew links multiple Node versions, make sure the desired Node binary appears first, for example `/opt/homebrew/bin`.
* Dependency versions are managed through the Gradle version catalog in `gradle/libs.versions.toml`.
* Compose dependencies are split by platform in the catalog:
  * Android Compose uses `androidx-compose-*`.
  * Compose Multiplatform uses `cmp-compose-*`.

# Run
## Android
Run using the `Android` configuration or:
```shell
./gradlew :android:installDebug
```

## iOS
- Open `ios` folder in Mac Finder and double-click `ios.xcodeproj` and run from Xcode.
- The iOS app embeds the shared framework from `iosUmbrellaModule`.
- The CMP tab requires the Compose iOS `Info.plist` setup, including `CADisableMinimumFrameDurationOnPhone`.

## Web
- Check [Project Live Demo](https://har5hit.github.io/PokeDex-KMP/)
- Run on local machine:
```shell
PATH="/opt/homebrew/bin:$PATH" ./gradlew :web-compose:jsBrowserDevelopmentRun --continuous
```

# Deployment as library

* The KMP libraries can be directly accessed by the embedded `Android`, `iOS` and `JavaScript` module in this project.
* If these KMP libraries is to be used as independent libraries in external apps, it can be published to maven repository.
    * `publishToMavenLocal` task will build and upload the KMP modules as libraries in local maven server which can be used to work in external `Android`, `iOS` and `JavaScript` apps in same machine.
    * `publish` task will build and upload the KMP modules as libraries in remote maven server which can be accessed from any machine. Remote maven configuration to be added in `plugins/publish.gradle`.
* For `Android`, the process is simple as adding gradle dependencies in the app to load these hosted libraries.
* For `iOS`, reference can be taken from this project or [follow this guide](https://kotlinlang.org/docs/multiplatform-mobile-integrate-in-existing-app.html#make-your-cross-platform-application-work-on-ios).
Pro tip: Keep KMP files inside a folder and not at root level to keep it encapsulated from native `iOS` project files and modify the configuration to support this.
* For `JavaScript`, the `web-compose` host consumes the shared KMP modules and reusable Compose Multiplatform UI directly.

# Resources

## Pokemon Data

<img src="https://user-images.githubusercontent.com/24237865/83422649-d1b1d980-a464-11ea-8c91-a24fdf89cd6b.png"/>

[PokeAPI](https://pokeapi.co/) - The RESTful Pokémon API

All the Pokémon data you'll ever need in one place,
easily accessible through a modern RESTful API.

## App Icon
* ["Those Icons"](https://www.flaticon.com/authors/those-icons)

# License
```xml
Copyright 2022 Harshith Shetty (hshetty.biz@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
