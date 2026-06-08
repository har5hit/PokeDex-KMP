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

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Layers
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.IPokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list_compose.presentation.pokemon_list.screen.PokemonListScreenCmp
import com.justadeveloper96.pokedex_kmp.helpers.compose.viewModelContainerWrapper
import org.koin.compose.koinInject

private enum class PokemonListScreenTab(val label: String) {
    Native("Native"),
    SharedCmp("Shared CMP")
}

@Composable
fun PokemonListScreenContainer() {

    val viewModel = koinInject<PokemonListViewModel>()
    var selectedTab by remember { mutableStateOf(PokemonListScreenTab.Native) }

    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    PokemonListScreenTab.entries.forEach { tab ->
                        BottomNavigationItem(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = {
                                Icon(
                                    imageVector = if (tab == PokemonListScreenTab.Native) {
                                        Icons.Filled.Android
                                    } else {
                                        Icons.Filled.Layers
                                    },
                                    contentDescription = tab.label
                                )
                            },
                            label = { Text(tab.label) }
                        )
                    }
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                viewModelContainerWrapper(viewModel, viewBlock = {
                    when (selectedTab) {
                        PokemonListScreenTab.Native -> {
                            PokemonListScreen(it, onAction = viewModel::add)
                        }

                        PokemonListScreenTab.SharedCmp -> {
                            PokemonListScreenCmp(it, onAction = viewModel::add)
                        }
                    }
                }, eventBlock = {
                    val context = LocalContext.current
                    when (it) {
                        is IPokemonListViewModel.UIEvent.Message -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }
}

