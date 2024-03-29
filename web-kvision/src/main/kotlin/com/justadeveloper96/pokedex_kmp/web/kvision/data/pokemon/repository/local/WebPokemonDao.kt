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

package com.justadeveloper96.pokedex_kmp.web.kvision.data.pokemon.repository.local

import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.IPokemonDao
import com.justadeveloper96.pokedexkmp.featurepokemonlist.PokemonDaoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class WebPokemonDao : IPokemonDao {
    private val dataHolder by lazy { MutableStateFlow<MutableList<PokemonDaoModel>>(mutableListOf()) }

    override fun insert(list: List<PokemonDaoModel>) {
        dataHolder.value.addAll(list)
    }

    override fun insert(item: PokemonDaoModel) {
        dataHolder.value.add(item)
    }

    override fun getAll(): Flow<List<PokemonDaoModel>> {
        return dataHolder
    }

    override fun deleteAll() {
        dataHolder.value.clear()
    }
}
