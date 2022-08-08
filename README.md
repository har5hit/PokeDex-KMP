# PokeDex KMP
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Android CI](https://github.com/har5hit/PokeDex-KMP/actions/workflows/android_ci.yml/badge.svg)](https://github.com/har5hit/PokeDex-KMP/actions/workflows/android_ci.yml)
[![Web CI-CD](https://github.com/har5hit/PokeDex-KMP/actions/workflows/web_ci_cd.yml/badge.svg)](https://github.com/har5hit/PokeDex-KMP/actions/workflows/web_ci_cd.yml)
[![](https://androidweekly.net/issues/issue-528/badge)](https://androidweekly.net/issues/issue-528)

A Demo Application for demonstrating code reuse in Android, iOS and JavaScript apps using [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) (KMP).
Specifications:
* 100% [Kotlin](https://kotlinlang.org/) for Common Kotlin Code, Android and JavaScript modules.
* 100% [Swift](https://www.swift.org/) for iOS.
* Kotlin: [1.7.10](https://kotlinlang.org/docs/releases.html).
* Test Driven Development (TDD).
* MVVM design pattern.
* Multi Module KMP Application Setup.
* Unit Testing via [Kotest](https://github.com/kotest/kotest) and [Mockk](https://mockk.io/).
* Multiplatform Dependency Injection using [Koin](https://insert-koin.io/).
* Multiplatform Concurrency using [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines).
* Multiplatform Database using [SQLDelight](https://github.com/cashapp/sqldelight).
* Multiplatform Network Client using [Ktor](https://ktor.io/).
* [Ktlint](https://ktlint.github.io/) for lint.
* [Jetpack Compose](https://developer.android.com/jetpack/compose) for Android.
* [SwiftUI](https://developer.apple.com/xcode/swiftui/) for iOS.
* [KVision](https://kvision.io/) for Web. [Project Live Demo](https://har5hit.github.io/PokeDex-KMP/).
* CI-CD using [Github Actions](https://github.com/features/actions).
* [Kermit](https://github.com/touchlab/Kermit) - Better iOS Crash Report Logging on Kotlin code crashes.
* KMP Modules Deployment as libraries for external `Android`, `iOS` and `JavaScript` apps.
* Firebase Crashlytics. Add `google-services.json` for Android and `GoogleService-Info.plist` for iOS.

[Blog for more details](https://har5hitn95.medium.com/experience-using-kmp-in-production-apps-for-a-year-2474406d99d4)

# Why KMP?
All advantages and freedom of native development + code re-usability in other platforms.

# Source Line of Code Stats:

## Common Module (KMP)
| Module              | sloc count |
|----------------------|-----|
| helpers              | 491 |
| core                 | 289 |
| feature_pokemon_list | 428 |
| **Total** | **1208** |

## Host apps (Containing views and di code)
| App              | sloc count | exclusive code % | common code % |
|----------------------|-----|-----|-----|
| android              | 210 | 17.38% | **82.62%**|
| ios                 | 130 | 10.76% | **89.24%**|

Around **~80%** code written for a feature can be reused for other platform using KMP.

# Screenshots
Android             |  iOS |  Web
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_android.png?raw=true)  |  ![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_ios.png?raw=true) | ![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_web.png?raw=true)


# Architecture

![Overview](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/architecture.svg?raw=true)

# Module Structure
* helpers - Generic helper methods for mobile apps
* core - Application specific common code which are shared by mostly all feature modules
* feature_* - Feature module
* iosUmbrellaModule - Umbrella module for ios. This module loads all sub gradle modules and creates a single framework file for adding to ios project. Also iOS specific helper code which needs kotlin access should be put here. Treat it as extension of iOS module with Kotlin support. 
* android - Android host app
* ios - iOS host app
* web-kvision - Web host app


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

# Run
## Android
Run using `Android` Configuration or using gradle task `installDebug`
## iOS
- Open `ios` folder in Mac Finder and double-click `ios.xcodeproj` and run from Xcode.
## Web
- Check [Project Live Demo](https://har5hit.github.io/PokeDex-KMP/)
- Run on local machine: gradle task `web-kvision:run -t`

# Deployment as library

* The KMP libraries can be directly accessed by the embedded `Android`, `iOS` and `JavaScript` module in this project.
* If these KMP libraries is to be used as independent libraries in external apps, it can be published to maven repository.
    * `publishToMavenLocal` task will build and upload the KMP modules as libraries in local maven server which can be used to work in external `Android`, `iOS` and `JavaScript` apps in same machine.
    * `publish` task will build and upload the KMP modules as libraries in remote maven server which can be accessed from any machine. Remote maven configuration to be added in `plugins/publish.gradle`.
* For `Android`, the process is simple as adding gradle dependencies in the app to load these hosted libraries.
* For `iOS`, reference can be taken from this project or [follow this guide](https://kotlinlang.org/docs/multiplatform-mobile-integrate-in-existing-app.html#make-your-cross-platform-application-work-on-ios).
Pro tip: Keep KMP files inside a folder and not at root level to keep it encapsulated from native `iOS` project files and modify the configuration to support this.
* For `JavaScript`, if using Kotlin Application like [KVision](https://kvision.io/) , the process is simple as adding gradle dependencies in the app to load these hosted libraries. 

# TODO
## iOS
- [ ] Test cases
- [ ] UI Event handling
- [ ] Dependency injection framework
- [ ] CI

## Common
- [ ] Code Generation templates

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
Copyright 2022 Harshith Shetty (justadeveloper96@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
