/*
 * Copyright 2020 Harshith Shetty (justadeveloper96@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.justadeveloper96.pokedex_kmp.android.di.module

import androidx.viewbinding.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.justadeveloper96.pokedex_kmp.android.database.PokemonDatabaseProviderFactory
import com.justadeveloper96.pokedex_kmp.core.network.client.AndroidNetworkClientProvider
import com.justadeveloper96.pokedex_kmp.core.network.client.INetworkClientProvider
import com.justadeveloper96.pokedex_kmp.core.network.parse.INetworkExceptionMapper
import com.justadeveloper96.pokedex_kmp.core.network.parse.NetworkExceptionMapper
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.database.PokemonDatabaseProvider
import org.koin.dsl.module

val platformModule = module {

    single<INetworkClientProvider> {
        AndroidNetworkClientProvider(
            debug = BuildConfig.DEBUG,
            interceptors = listOf(),
            networkInterceptor = if (BuildConfig.DEBUG) listOf(ChuckerInterceptor(get())) else listOf()
        )
    }

    single {
        PokemonDatabaseProvider(PokemonDatabaseProviderFactory(get())).instance()
    }

    single<INetworkExceptionMapper> {
        NetworkExceptionMapper()
    }
}
