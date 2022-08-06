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

package com.justadeveloper96.pokedex_kmp.iosUmbrellaModule.di.module

import com.justadeveloper96.pokedex_kmp.core.network.client.INetworkClientProvider
import com.justadeveloper96.pokedex_kmp.core.network.client.IOSNetworkClientProvider
import com.justadeveloper96.pokedex_kmp.core.network.parse.INetworkExceptionMapper
import com.justadeveloper96.pokedex_kmp.core.network.parse.NetworkExceptionMapper
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.database.PokemonDatabaseProvider
import com.justadeveloper96.pokedex_kmp.iosUmbrellaModule.database.PokemonDatabaseProviderFactory
import org.koin.dsl.module

fun platformModule(debug: Boolean) = module {
    single<INetworkClientProvider> {
        IOSNetworkClientProvider(
            debug = debug
        )
    }

    single {
        PokemonDatabaseProvider(PokemonDatabaseProviderFactory()).instance()
    }

    single<INetworkExceptionMapper> {
        NetworkExceptionMapper()
    }
}
