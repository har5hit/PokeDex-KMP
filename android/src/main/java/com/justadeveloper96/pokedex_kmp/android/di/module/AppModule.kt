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

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.justadeveloper96.pokedex_kmp.core.network.AndroidNetworkGateway
import com.justadeveloper96.pokedex_kmp.core.network.INetworkGateway
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.PokemonDatabase
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.database.PokemonDatabaseProvider
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.IPokemonRepository
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.PokemonRepository
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.IPokemonDao
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.PokemonDao
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.IPokemonApi
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.PokemonApi
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonListViewModel
import com.justadeveloper96.pokedex_kmp.helpers.coroutine.AppCoroutineDispatchers
import com.justadeveloper96.pokedex_kmp.helpers.coroutine.CoroutineDependency
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDefaultDispatcher(): AppCoroutineDispatchers {
        return CoroutineDependency.dispatchers
    }

    @Singleton
    @Provides
    fun provideNetworkGateway(@ApplicationContext context: Context): INetworkGateway {
        return AndroidNetworkGateway(
            debug = BuildConfig.DEBUG,
            interceptors = listOf(),
            networkInterceptor = if (BuildConfig.DEBUG) listOf(ChuckerInterceptor(context)) else listOf()
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return PokemonDatabaseProvider(PokemonDatabaseProviderFactory(context)).instance()
    }

    @Reusable
    @Provides
    fun providePokemonApi(networkGateway: INetworkGateway): IPokemonApi {
        return PokemonApi(networkGateway)
    }

    @Reusable
    @Provides
    fun providePokemonDao(pokemonDatabase: PokemonDatabase): IPokemonDao {
        return PokemonDao(pokemonDatabase.pokemonDaoModelQueries)
    }

    @Provides
    fun providePokemonRepository(
        pokemonApi: IPokemonApi,
        pokemonDao: IPokemonDao
    ): IPokemonRepository {
        return PokemonRepository(pokemonApi, pokemonDao)
    }

    @Provides
    fun providePokemonListViewModel(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        pokemonRepository: IPokemonRepository
    ): PokemonListViewModel {
        return PokemonListViewModel(appCoroutineDispatchers, pokemonRepository)
    }
}
