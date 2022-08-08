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

import io.kvision.core.Background
import io.kvision.core.Col
import io.kvision.core.Color.Companion.name
import io.kvision.html.label
import io.kvision.onsenui.core.navigator
import io.kvision.onsenui.core.page
import io.kvision.onsenui.grid.row
import io.kvision.onsenui.list.onsList
import io.kvision.onsenui.visual.card
import io.kvision.panel.Root

fun Root.loaderListScreen() {
    navigator(swipeable = true) {
        swipeTargetWidth = 50
        page {
            onsList {
                for (i in 0..10) {
                    card(className = "item-card") {
                        background = Background(color = name(Col.WHITE))
                        row {
                            label(className = "item-label", content = "")
                        }
                    }
                }
            }
        }
    }
}
