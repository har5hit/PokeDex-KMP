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

package com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.view.PokemonListView
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.IPokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonUiModel

@Composable
fun PokemonListScreen(
    state: IPokemonListViewModel.UIState,
    onAction: (IPokemonListViewModel.Action) -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(state.loading),
        onRefresh = {
            onAction(IPokemonListViewModel.Action.Refresh)
        }
    ) {
        PokemonListView(state.list) {
            if (state.canLoadMore) {
                onAction(IPokemonListViewModel.Action.Fetch)
            }
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    val uiState = IPokemonListViewModel.UIState(
        loading = true,
        list = listOf(
            PokemonUiModel(
                name = "Bulbasaur",
                url = "https://pokeapi.co/api/v2/pokemon/1/"
            ),
            PokemonUiModel(name = "Ivysaur", url = "https://pokeapi.co/api/v2/pokemon/2/"),
            PokemonUiModel(name = "Ivysaur", url = "https://pokeapi.co/api/v2/pokemon/3/"),
            PokemonUiModel(name = "Ivysaur", url = "https://pokeapi.co/api/v2/pokemon/4/"),
            PokemonUiModel(name = "Ivysaur", url = "https://pokeapi.co/api/v2/pokemon/5/")
        ),
        canLoadMore = true
    )
    PokemonListScreen(uiState) { println("On Action $it") }
}
