/*
 * MIT License
 *
 * Copyright (c) 2020 Harshith Shetty (justadeveloper96@gmail.com)
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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository

import com.justadeveloper96.pokedex_kmp.core.network.model.Loading
import com.justadeveloper96.pokedex_kmp.core.network.model.Success
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.IPokemonDao
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.mapper.toDaoModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.mapper.toDomainModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.IPokemonApi
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.mapper.toDomainModel
import com.justadeveloper96.pokedex_kmp.helpers.pagination.PaginatedList
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class PokemonRepository constructor(
    private val pokemonApi: IPokemonApi,
    private val pokemonDao: IPokemonDao
) : IPokemonRepository {

    private val TAG = "PokemonRepository"
    override suspend fun get(offset: Int, limit: Int) =
        combine(
            pokemonDao.getAll(),
            fetchPokemonList(offset, limit)
        ) { a, b ->
            val data =
                PaginatedList(a.map { it.toDomainModel() }, maxOf(a.size, b.data?.total ?: 0))
            b.modify(data)
        }

    private suspend fun fetchPokemonList(offset: Int, limit: Int) =
        flow {
            emit(Loading())
            val result = pokemonApi.get(offset, limit)
            if (result is Success) {
                if (offset == 0) {
                    pokemonDao.deleteAll()
                }
                pokemonDao.insert(result.data.results.map { it.toDaoModel() })
            }
            emit(result.map { PaginatedList(it.results.map { it.toDomainModel() }, it.count) })
        }
}
