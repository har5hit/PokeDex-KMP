/*
 * MIT License
 *
 * Copyright (c) 2020 Harshith Shetty (hshetty.biz@gmail.com)
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

import com.justadeveloper96.pokedex_kmp.core.network.parse.NetworkException
import com.justadeveloper96.pokedex_kmp.core.network.parse.Success
import com.justadeveloper96.pokedex_kmp.core.network.parse.Unsuccessful
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.IPokemonRepository
import com.justadeveloper96.pokedex_kmp.helpers.coroutine.AppCoroutineDispatchers
import com.justadeveloper96.pokedex_kmp.helpers.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: IPokemonRepository
) :
    BaseViewModel<IPokemonListViewModel.UIState, IPokemonListViewModel.UIEvent, IPokemonListViewModel.Action>(
        IPokemonListViewModel.UIState(true, listOf(), false),
        appCoroutineDispatchers
    ),
    IPokemonListViewModel {

    override val TAG = "PokemonListViewModel"
    private val limit = 10
    private var offset = 0
    private var loading = false
    private var moreAvailable = true
    private var list = listOf<PokemonUiModel>()

    override fun add(action: IPokemonListViewModel.Action) {
        when (action) {
            IPokemonListViewModel.Action.Fetch -> {
                fetchNextPage()
            }

            IPokemonListViewModel.Action.Refresh -> {
                refresh()
            }
        }
    }

    private fun fetchNextPage() {
        if (!moreAvailable || loading) {
            return
        }
        loading = true
        pushState()
        vmScope.launch(appCoroutineDispatchers.io) {
            fetchPage(reset = false)
        }
    }

    private fun refresh() {
        if (loading) {
            return
        }
        loading = true
        offset = 0
        moreAvailable = true
        pushState()
        vmScope.launch(appCoroutineDispatchers.io) {
            fetchPage(reset = true)
        }
    }

    private suspend fun loadInitialData() {
        list = repository.get().map { it.toPokemonUiModel() }
        offset += list.size
        pushState()

        if (list.isEmpty()) {
            fetchNextPage()
        }
    }

    private suspend fun fetchPage(reset: Boolean) {
        when (val result = repository.fetch(offset, limit)) {
            is Success -> {
                val newItems = result.data.first.map { it.toPokemonUiModel() }
                list = if (reset || offset == 0) {
                    newItems
                } else {
                    list + newItems
                }
                offset += list.size
                moreAvailable = list.size < result.data.second
                loading = false
                pushState()
            }

            is Unsuccessful, is NetworkException -> {
                loading = false
                pushState()
                pushEvent(IPokemonListViewModel.UIEvent.Message(result.message))
            }
        }
    }

    private fun pushState() {
        setState(
            IPokemonListViewModel.UIState(
                loading = loading,
                list = list,
                canLoadMore = moreAvailable
            )
        )
    }

    init {
        vmScope.launch(appCoroutineDispatchers.io) {
            loadInitialData()
        }
    }
}
