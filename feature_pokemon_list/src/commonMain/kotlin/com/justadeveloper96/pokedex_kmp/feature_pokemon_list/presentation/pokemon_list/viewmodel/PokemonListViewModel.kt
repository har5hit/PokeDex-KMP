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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel

import com.justadeveloper96.pokedex_kmp.core.network.parse.Loading
import com.justadeveloper96.pokedex_kmp.core.network.parse.NetworkException
import com.justadeveloper96.pokedex_kmp.core.network.parse.Success
import com.justadeveloper96.pokedex_kmp.core.network.parse.Unsuccessful
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.IPokemonRepository
import com.justadeveloper96.pokedex_kmp.helpers.coroutine.AppCoroutineDispatchers
import com.justadeveloper96.pokedex_kmp.helpers.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
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

    private val offset = MutableStateFlow(0)
    private val loading = MutableStateFlow(false)
    private val moreAvailable = MutableStateFlow(true)
    private val list = MutableStateFlow(listOf<PokemonUiModel>())

    override fun add(action: IPokemonListViewModel.Action) {
        when (action) {
            IPokemonListViewModel.Action.Fetch -> {
                fetch()
            }
            IPokemonListViewModel.Action.Refresh -> {
                clearFlags()
                fetch()
            }
        }
    }

    private fun clearFlags() {
        offset.value = 0
        moreAvailable.value = true
    }

    private fun fetch() {
        if (moreAvailable.value && !loading.value) {
            loading.value = true
            vmScope.launch(appCoroutineDispatchers.io) {
                fetchNewItems()
            }
        }
    }

    private suspend fun fetchNewItems() {
        repository.get(offset.value, limit).collect {
            when (it) {
                is Loading -> {
                    loading.value = true
                    list.value = it.data?.data?.map { it.toPokemonUiModel() } ?: listOf()
                }
                is Success -> {
                    loading.value = false
                    offset.value = it.data.data.size
                    list.value = it.data.data.map { it.toPokemonUiModel() }
                    moreAvailable.value = it.data.moreAvailable
                }
                is Unsuccessful, is NetworkException -> {
                    loading.value = false
                    pushEvent(IPokemonListViewModel.UIEvent.Message(it.message))
                }
            }
        }
    }

    init {
        vmScope.launch(dispatchers.mainImmediate) {
            combine(loading, moreAvailable, list) { loading, moreAvailable, list ->
                IPokemonListViewModel.UIState(
                    loading = loading,
                    list = list,
                    canLoadMore = moreAvailable
                )
            }.collect {
                setState(it)
            }
        }
    }
}
