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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository

import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.IPokemonDao
import com.justadeveloper96.pokedexkmp.featurepokemonlist.PokemonDaoModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class FakePokemonDao : IPokemonDao {

    val channel = Channel<List<PokemonDaoModel>>(1)

    val data = mutableListOf<PokemonDaoModel>()

    override fun insert(list: List<PokemonDaoModel>) {
        data.addAll(list.toList())
        invalidate()
    }

    override fun insert(item: PokemonDaoModel) {
        data.add(item)
        invalidate()
    }

    override fun getAll(): Flow<List<PokemonDaoModel>> = channel.receiveAsFlow()

    override fun deleteAll() {
        data.clear()
        invalidate()
    }

    private fun invalidate() {
        channel.trySend(data)
    }
}
