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

package com.justadeveloper96.pokedex_kmp.web.kvision.presentation.pokemon_list.view

import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.IPokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonListViewModel
import io.kvision.core.Background
import io.kvision.core.Color
import io.kvision.html.div
import io.kvision.html.image
import io.kvision.html.label
import io.kvision.onsenui.core.navigator
import io.kvision.onsenui.core.page
import io.kvision.onsenui.grid.row
import io.kvision.onsenui.list.onsList
import io.kvision.onsenui.visual.card
import io.kvision.onsenui.visual.icon
import io.kvision.panel.Root
import io.kvision.state.bind
import io.kvision.utils.px
import kotlinx.browser.window
import org.koin.core.Koin

fun Root.pokemonListScreen(di: Koin) {
    val pokemonListViewModel: PokemonListViewModel = di.get()
    pokemonListViewModel.add(IPokemonListViewModel.Action.Fetch)
    navigator(swipeable = true) {
        swipeTargetWidth = 50
        page {
            bind(pokemonListViewModel.stateHolder) { state ->
                if (state.list.isEmpty()) {
                    div {
                        icon("fa-spinner", size = "26px", spin = true)
                    }
                } else {
                    onsList {
                        state.list.forEach {
                            card(className = "item-card") {
                                background = Background(color = Color(it.color))
                                row {
                                    label(className = "item-label", content = it.name)
                                    div {
                                        image(it.imageSvg) {
                                            width = 100.px
                                            height = 100.px
                                        }
                                    }
                                }
                            }
                        }
                    }
                    div(className = "after-list") {
                        icon("fa-spinner", size = "26px", spin = true)
                    }
                    onInfiniteScroll { done ->
                        window.setTimeout({
                            pokemonListViewModel.add(IPokemonListViewModel.Action.Fetch)
                            done()
                        }, 600)
                    }
                }
            }
        }
        onSwipe { pokemonListViewModel.add(IPokemonListViewModel.Action.Refresh) }
    }
}
