package com.justadeveloper96.pokedex_kmp.iosUmbrellaModule.di

import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.di.featurePokemonListModule
import com.justadeveloper96.pokedex_kmp.helpers.di.helperModule
import com.justadeveloper96.pokedex_kmp.iosUmbrellaModule.di.module.platformModule
import org.koin.core.Koin
import org.koin.core.context.startKoin

class InitKoin {
    operator fun invoke(): Koin {
        return startKoin {
            modules(
                listOf(
                    platformModule(true), featurePokemonListModule, helperModule
                )
            )
        }.koin
    }
}
