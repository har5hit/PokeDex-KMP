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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository

import com.justadeveloper96.pokedex_kmp.core.network.parse.Loading
import com.justadeveloper96.pokedex_kmp.core.network.parse.Success
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.IPokemonDao
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.mapper.toDomainModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.IPokemonApi
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.mapper.toDomainModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.model.PokemonListResponseModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.model.PokemonResponseModel
import com.justadeveloper96.pokedex_kmp.helpers.pagination.PaginatedList
import com.justadeveloper96.pokedexkmp.featurepokemonlist.PokemonDaoModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

class PokemonRepositoryTest : StringSpec({

    val api = mockk<IPokemonApi>()
    val dao: IPokemonDao = FakePokemonDao()

    val repository = PokemonRepository(api, dao)

    "first load local data and then update with network data" {

        val localData = List(10) { i -> PokemonDaoModel(i.toString(), i.toString()) }
        dao.insert(localData)

        val networkData =
            List(10) { i -> PokemonResponseModel((i * 2).toString(), (i * 2).toString()) }
        coEvery { api.get(0, 10) } returns Success(PokemonListResponseModel(20, networkData), 200)

        val states = repository.get(0, 10).take(2).toList(mutableListOf())

        val localDataList = localData.map { it.toDomainModel() }
        val networkDataList = networkData.map { it.toDomainModel() }
        states shouldBe listOf(
            Loading(
                PaginatedList(
                    localDataList,
                    localDataList.size
                )
            ),
            Success(
                PaginatedList(
                    networkDataList,
                    20
                ),
                200
            )
        )
    }
})
