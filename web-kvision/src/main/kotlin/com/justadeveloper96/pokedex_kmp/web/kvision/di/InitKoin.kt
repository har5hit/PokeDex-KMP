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

package com.justadeveloper96.pokedex_kmp.web.kvision.di

import com.justadeveloper96.pokedex_kmp.core.di.coreModule
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.PokemonDatabase
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.di.featurePokemonListModule
import com.justadeveloper96.pokedex_kmp.helpers.di.helperModule
import com.justadeveloper96.pokedex_kmp.web.kvision.di.module.platformModule
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import org.koin.core.Koin
import org.koin.core.context.startKoin
import kotlin.js.Promise

class InitKoin {
    operator fun invoke(): Promise<Koin> {
        return initSqlDriver(PokemonDatabase.Schema).then { sqlDriver ->
            startKoin {
                modules(
                    listOf(
                        platformModule(true, sqlDriver),
                        featurePokemonListModule,
                        coreModule,
                        helperModule
                    )
                )
            }.koin
        }
    }
}
