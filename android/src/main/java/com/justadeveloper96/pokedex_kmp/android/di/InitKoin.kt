package com.justadeveloper96.pokedex_kmp.android.di

import android.content.Context
import com.justadeveloper96.pokedex_kmp.android.di.module.platformModule
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.di.featurePokemonListModule
import com.justadeveloper96.pokedex_kmp.helpers.di.helperModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InitKoin {
    operator fun invoke(applicationContext: Context) {
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                listOf(
                    platformModule,
                    featurePokemonListModule,
                    helperModule
                )
            )
        }
    }
}
