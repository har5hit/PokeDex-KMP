package com.justadeveloper96.pokedex_kmp.iosUmbrellaModule.di

import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonListViewModel
import org.koin.core.Koin

fun getPokemonViewModel(koin: Koin): PokemonListViewModel {
    return koin.get()
}
