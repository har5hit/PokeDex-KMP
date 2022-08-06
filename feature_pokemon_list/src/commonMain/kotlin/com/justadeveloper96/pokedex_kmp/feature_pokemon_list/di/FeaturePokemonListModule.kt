package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.di

import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.PokemonDatabase
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.IPokemonRepository
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.PokemonRepository
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.IPokemonDao
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.PokemonDao
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.IPokemonApi
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.PokemonApi
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonListViewModel
import com.justadeveloper96.pokedex_kmp.helpers.coroutine.CoroutineDependency
import org.koin.dsl.module

val featurePokemonListModule = module {

    single { CoroutineDependency.dispatchers }
    single<IPokemonApi> { PokemonApi(get(), get()) }
    single<IPokemonDao> { PokemonDao(get<PokemonDatabase>().pokemonDaoModelQueries) }
    single<IPokemonRepository> { PokemonRepository(get(), get()) }
    factory { PokemonListViewModel(get(), get()) }
}
