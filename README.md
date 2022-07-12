# PokeDex KMP
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Android CI](https://github.com/har5hit/PokeDex-KMP/actions/workflows/android_ci.yml/badge.svg)](https://github.com/har5hit/PokeDex-KMP/actions/workflows/android_ci.yml)

A Demo Application for demonstrating code reuse in Both Android and iOS apps using [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) (KMP). Some extra specs:
* 100% [Kotlin](https://kotlinlang.org/) for Android and KMP module.
* 100% [Swift](https://www.swift.org/) for iOS.
* Kotlin: 1.6.21
* Test Driven Development
* MVVM design pattern
* Multi Module KMP Application Setup
* Dependency Injection using [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Android
* Unit Testing via [kotest](https://github.com/kotest/kotest) and [Mockk](https://mockk.io/)
* Multithreading using [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines).
* Multiplatform Database using [SQLDelight](https://github.com/cashapp/sqldelight)
* Multiplatform Network Client using [Ktor](https://ktor.io/)
* [Ktlint](https://ktlint.github.io/) for lint.
* KMP Modules Deployment as libraries for external `Android` and `iOS` apps.
* Jetpack Compose.
* SwiftUI.
* Firebase Crashlytics. Add `google-services.json` for Android and `GoogleService-Info.plist` for iOS.

[Blog for more details](https://har5hitn95.medium.com/experience-using-kmp-in-production-apps-for-a-year-2474406d99d4)

# Why KMP?
All advantages and freedom of native development + code re-usability in other platforms

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
Android             |  iOS
:-------------------------:|:-------------------------:
![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_android.png?raw=true)  |  ![](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/screenshot_ios.png?raw=true)


# Architecture

![Overview](https://github.com/har5hit/PokeDex-KMP/blob/master/assets/architecture.svg?raw=true)

# Module Structure
* helpers - Generic helper methods for mobile apps
* core - Application specific common code which are shared by mostly all feature modules
* feature_* - Feature module
* shared - Umbrella module for ios. This module loads all sub gradle modules and creates a single framework file for adding to ios project.
* android - Android host app
* ios - iOS host app


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


# Deployment as library

* The KMP libraries can be directly accessed by the embedded `Android` and `iOS` module in this project.
* If these KMP libraries is to be used as independent libraries in external `Android` / `iOS` apps, it can be published to maven repository.
    * `publishToMavenLocal` task will build and upload the KMP modules as libraries in local maven server which can be used to work in external android/ios apps in same machine.
    * `publish` task will build and upload the KMP modules as libraries in remote maven server which can be accessed from any machine. Remote maven configuration to be added in `plugins/publish.gradle`.
* For `Android`, the process is simple as adding gradle dependencies in the Android app to load these hosted libraries.
* For `iOS`, reference can be taken from this project or [follow this guide](https://kotlinlang.org/docs/multiplatform-mobile-integrate-in-existing-app.html#make-your-cross-platform-application-work-on-ios).
Pro tip: Keep KMP files inside a folder and not at root level to keep it encapsulated from native `iOS` project files and modify the configuration to support this.

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
