# PokeDex KMP
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)

A Demo Application for demonstrating code reuse in Both Android and iOS apps using [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) (KMP). Some extra specs:

* 100% [Kotlin](https://kotlinlang.org/) for Android and KMP module.
* 100% [Swift](https://www.swift.org/) for iOS.
* Test Driven Development
* MVVM design pattern
* Multi Module KMP Application Setup
* Dependency Injection using [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Android
* Unit Testing via [kotest](https://github.com/kotest/kotest) and [Mockk](https://mockk.io/)
* Multithreading using [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines).
* Multiplatform Database using [SQLDelight](https://github.com/cashapp/sqldelight)
* Multiplatform Network Client using [Ktor](https://ktor.io/)
* [Ktlint](https://ktlint.github.io/) for lint.


# Source Line of Code Stats:

## Common Module (KMP)
| Module              | sloc count |
|----------------------|-----|
| helpers              | 491 |
| core                 | 289 |
| feature_pokemon_list | 428 |
| **Total** | **1208** |

## Host apps
| App              | sloc count | common code % |
|----------------------|-----|-----|
| android              | 210 |82.62%|
| ios                 | 130 |89.24%|

Around **~80%** code made common for android and ios app using KMP.

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
      

# TODO
## iOS
- [ ] test cases
- [ ] UI Event handling
- [ ] Dependency injection framework

## Common
- [ ] CI
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