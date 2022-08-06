package com.justadeveloper96.pokedex_kmp.iosUmbrellaModule.database

import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.PokemonDatabase
import com.justadeveloper96.pokedex_kmp.helpers.dao.IDatabaseDriverFactory
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

class PokemonDatabaseProviderFactory : IDatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = PokemonDatabase.Schema,
            name = "pokemon.db",
            maxReaderConnections = 4
        )
    }
}
