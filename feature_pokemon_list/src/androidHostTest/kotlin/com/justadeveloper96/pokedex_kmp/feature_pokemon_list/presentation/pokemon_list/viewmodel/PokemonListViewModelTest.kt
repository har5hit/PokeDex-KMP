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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel

import com.justadeveloper96.pokedex_kmp.core.network.parse.Success
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.IPokemonRepository
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.model.Pokemon
import com.justadeveloper96.pokedex_kmp.helpers.coroutine.AppCoroutineDispatchers
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

class PokemonListViewModelTest :
    StringSpec({

        "loads cached data on init without fetching when cache is not empty" {
            runTest {
                val repository: IPokemonRepository = mockk()
                val dispatcher = StandardTestDispatcher(testScheduler)
                val dispatchers =
                    AppCoroutineDispatchers(dispatcher, dispatcher, dispatcher, dispatcher)
                val cached =
                    List(2) { i ->
                        Pokemon(
                            "pokemon-$i",
                            "https://pokeapi.co/api/v2/pokemon/${i + 1}/",
                        )
                    }

                coEvery { repository.get() } returns cached

                val viewModel = PokemonListViewModel(dispatchers, repository)

                viewModel.stateHolder.value shouldBe
                    IPokemonListViewModel.UIState(
                        loading = false,
                        list = cached.map { it.toPokemonUiModel() },
                        canLoadMore = true,
                    )

                coVerify(exactly = 1) { repository.get() }
                coVerify(exactly = 0) { repository.fetch(any(), any()) }
            }
        }

        "fetches initial page when cache is empty and appends next page" {
            runTest {
                val repository: IPokemonRepository = mockk()
                val dispatcher = StandardTestDispatcher(testScheduler)
                val dispatchers =
                    AppCoroutineDispatchers(dispatcher, dispatcher, dispatcher, dispatcher)
                val page1 =
                    List(10) { i ->
                        Pokemon(
                            "pokemon-$i",
                            "https://pokeapi.co/api/v2/pokemon/${i + 1}/",
                        )
                    }
                val page2 =
                    List(5) { i ->
                        Pokemon(
                            "pokemon-${i + 10}",
                            "https://pokeapi.co/api/v2/pokemon/${i + 11}/",
                        )
                    }

                coEvery { repository.get() } returns emptyList()
                coEvery { repository.fetch(0, 10) } returns Success(Pair(page1, 15), 200)
                coEvery { repository.fetch(10, 10) } returns Success(Pair(page2, 15), 200)

                val viewModel = PokemonListViewModel(dispatchers, repository)

                viewModel.stateHolder.value shouldBe
                    IPokemonListViewModel.UIState(
                        loading = false,
                        list = page1.map { it.toPokemonUiModel() },
                        canLoadMore = true,
                    )

                viewModel.add(IPokemonListViewModel.Action.Fetch)

                viewModel.stateHolder.value shouldBe
                    IPokemonListViewModel.UIState(
                        loading = false,
                        list = (page1 + page2).map { it.toPokemonUiModel() },
                        canLoadMore = false,
                    )

                viewModel.add(IPokemonListViewModel.Action.Fetch)
            }
        }

        "refresh reloads first page from offset zero" {
            runTest {
                val repository: IPokemonRepository = mockk()
                val dispatcher = StandardTestDispatcher(testScheduler)
                val dispatchers =
                    AppCoroutineDispatchers(dispatcher, dispatcher, dispatcher, dispatcher)
                val initialPage =
                    List(10) { i ->
                        Pokemon(
                            "pokemon-$i",
                            "https://pokeapi.co/api/v2/pokemon/${i + 1}/",
                        )
                    }
                val refreshedPage =
                    List(3) { i ->
                        Pokemon(
                            "refresh-$i",
                            "https://pokeapi.co/api/v2/pokemon/${i + 21}/",
                        )
                    }

                coEvery { repository.get() } returns emptyList()
                coEvery { repository.fetch(0, 10) } returns Success(Pair(initialPage, 20), 200)

                val viewModel = PokemonListViewModel(dispatchers, repository)

                viewModel.stateHolder.value shouldBe
                    IPokemonListViewModel.UIState(
                        loading = false,
                        list = initialPage.map { it.toPokemonUiModel() },
                        canLoadMore = true,
                    )

                coEvery { repository.fetch(0, 10) } returns Success(Pair(refreshedPage, 3), 200)

                viewModel.add(IPokemonListViewModel.Action.Refresh)

                viewModel.stateHolder.value shouldBe
                    IPokemonListViewModel.UIState(
                        loading = false,
                        list = refreshedPage.map { it.toPokemonUiModel() },
                        canLoadMore = false,
                    )
            }
        }
    })
