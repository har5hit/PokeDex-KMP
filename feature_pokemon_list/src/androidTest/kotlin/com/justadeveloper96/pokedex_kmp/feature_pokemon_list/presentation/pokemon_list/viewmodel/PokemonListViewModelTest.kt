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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel

import com.justadeveloper96.pokedex_kmp.core.network.parse.AppNetworkResult
import com.justadeveloper96.pokedex_kmp.core.network.parse.Loading
import com.justadeveloper96.pokedex_kmp.core.network.parse.Success
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.IPokemonRepository
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.model.Pokemon
import com.justadeveloper96.pokedex_kmp.helpers.coroutine.AppCoroutineDispatchers
import com.justadeveloper96.pokedex_kmp.helpers.pagination.PaginatedList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest : StringSpec({

    val repository: IPokemonRepository = mockk()

    val dispatcher = UnconfinedTestDispatcher()
    val dispatchers = AppCoroutineDispatchers(dispatcher, dispatcher, dispatcher, dispatcher)

    "inital state test" {
        val viewModel = PokemonListViewModel(dispatchers, repository)

        val dataChannel = Channel<AppNetworkResult<PaginatedList<Pokemon>>>(1)
        val data = PaginatedList(List(10) { i -> Pokemon(i.toString(), i.toString()) }, total = 10)
        coEvery { repository.get(0, 10) } returns dataChannel.consumeAsFlow()

        dataChannel.trySend(Loading(data))

        viewModel.add(IPokemonListViewModel.Action.Fetch)

        dataChannel.trySend(Success(data, 200))

        viewModel.stateHolder.value shouldBe IPokemonListViewModel.UIState(list = data.data.map { it.toPokemonUiModel() })
    }

    "pagination for new items and refresh" {
        val viewModel = PokemonListViewModel(dispatchers, repository)

        val dataChannel = Channel<AppNetworkResult<PaginatedList<Pokemon>>>(1)
        val data1 = PaginatedList(List(10) { i -> Pokemon(i.toString(), i.toString()) }, total = 20)
        val data2 = PaginatedList(List(20) { i -> Pokemon(i.toString(), i.toString()) }, total = 20)
        coEvery { repository.get(eq(0), eq(10)) } answers {
            dataChannel.trySend(Success(data1, 200))
            dataChannel.consumeAsFlow()
        }
        coEvery { repository.get(eq(10), eq(10)) } answers {
            dataChannel.trySend(Success(data2, 200))
            dataChannel.consumeAsFlow()
        }

        viewModel.add(IPokemonListViewModel.Action.Fetch)

        viewModel.stateHolder.value shouldBe IPokemonListViewModel.UIState(list = data1.data.map { it.toPokemonUiModel() }, canLoadMore = true)

        viewModel.add(IPokemonListViewModel.Action.Fetch)

        viewModel.stateHolder.value shouldBe IPokemonListViewModel.UIState(list = data2.data.map { it.toPokemonUiModel() }, canLoadMore = false)

        viewModel.add(IPokemonListViewModel.Action.Fetch)

        coVerify(inverse = true) { repository.get(eq(30), eq(10)) }

        viewModel.add(IPokemonListViewModel.Action.Refresh)

        viewModel.stateHolder.value shouldBe IPokemonListViewModel.UIState(list = data1.data.map { it.toPokemonUiModel() }, canLoadMore = true)
    }
})
