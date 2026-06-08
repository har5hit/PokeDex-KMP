/*
 * MIT License
 *
 * Copyright (c) 2026 Harshith Shetty (justadeveloper96@gmail.com)
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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list_compose.presentation.pokemon_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.IPokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list_compose.presentation.pokemon_list.screen.PokemonListScreenCmp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import platform.UIKit.UIViewController

fun PokemonListComposeViewController(
    viewModel: IPokemonListViewModel,
    onMessage: (String?) -> Unit = {}
): UIViewController = ComposeUIViewController {
    PokemonListComposeViewControllerContent(
        viewModel = viewModel,
        onMessage = onMessage
    )
}

@Composable
private fun PokemonListComposeViewControllerContent(
    viewModel: IPokemonListViewModel,
    onMessage: (String?) -> Unit
) {
    val scope = remember { MainScope() }
    var state by remember { mutableStateOf(viewModel.initialState) }

    DisposableEffect(viewModel) {
        val stateJob = scope.launch {
            viewModel.stateHolder.collect { state = it }
        }
        val eventJob = scope.launch {
            viewModel.eventHolder.collect { event ->
                if (event is IPokemonListViewModel.UIEvent.Message) {
                    onMessage(event.message)
                }
            }
        }

        onDispose {
            stateJob.cancel()
            eventJob.cancel()
            scope.cancel()
        }
    }

    PokemonListScreenCmp(
        state = state,
        onAction = viewModel::add
    )
}
