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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network

import com.justadeveloper96.pokedex_kmp.core.network.client.INetworkClientProvider
import com.justadeveloper96.pokedex_kmp.core.network.parse.AppNetworkResult
import com.justadeveloper96.pokedex_kmp.core.network.parse.IJsonParser
import com.justadeveloper96.pokedex_kmp.core.network.parse.INetworkExceptionMapper
import com.justadeveloper96.pokedex_kmp.core.network.parse.NetworkResponseData
import com.justadeveloper96.pokedex_kmp.core.network.parse.parseToAppNetworkResult
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.network.model.PokemonListResponseModel
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText

class PokemonApi(
    private val networkClientProvider: INetworkClientProvider,
    private val networkExceptionMapping: INetworkExceptionMapper,
    private val jsonParser: IJsonParser
) : IPokemonApi {

    private val ENDPOINT = "https://pokeapi.co/api/v2/pokemon"

    override suspend fun get(offset: Int, limit: Int): AppNetworkResult<PokemonListResponseModel> {
        return parseToAppNetworkResult(jsonParser, networkExceptionMapping) {
            val result = networkClientProvider.client.get<HttpResponse>(ENDPOINT) {
                parameter("offset", offset)
                parameter("limit", limit)
            }
            NetworkResponseData(result.status.value, result.readText())
        }
    }
}
