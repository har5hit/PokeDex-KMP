/*
 * MIT License
 *
 * Copyright (c) 2022 Harshith Shetty (hshetty.biz@gmail.com)
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

import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.PokemonDaoModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.local.IPokemonDao
import kotlinx.coroutines.channels.Channel

class FakePokemonDao : IPokemonDao {

    val channel = Channel<List<PokemonDaoModel>>(1)

    val data = mutableListOf<PokemonDaoModel>()

    override suspend fun insert(list: List<PokemonDaoModel>) {
        data.addAll(list.toList())
        invalidate()
    }

    override suspend fun insert(item: PokemonDaoModel) {
        data.add(item)
        invalidate()
    }

    override suspend fun getAll(): List<PokemonDaoModel> = data.toList()

    override suspend fun deleteAll() {
        data.clear()
        invalidate()
    }

    private fun invalidate() {
        channel.trySend(data)
    }
}
