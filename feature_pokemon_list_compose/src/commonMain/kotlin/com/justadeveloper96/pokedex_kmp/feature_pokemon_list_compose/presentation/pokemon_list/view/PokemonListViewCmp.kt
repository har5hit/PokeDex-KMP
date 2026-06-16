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

package com.justadeveloper96.pokedex_kmp.feature_pokemon_list_compose.presentation.pokemon_list.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonUiModel

@Composable
private fun PokemonListItemViewCmp(model: PokemonUiModel) {
    val cardModifier = Modifier.padding(12.dp).height(150.dp)
    Card(modifier = cardModifier) {
        val boxModifier =
            Modifier.fillMaxSize()
                .background(color = parseColor(model.color))
                .padding(12.dp)
        Box(modifier = boxModifier) {
            val imageModifier =
                Modifier.align(Alignment.CenterEnd)
                    .width(120.dp)
                    .height(120.dp)
            AsyncImage(
                model = model.imagePng,
                contentDescription = null,
                modifier = imageModifier,
                onError = {
                    it.result.throwable.printStackTrace()
                    print("AsyncImage ${it.result.throwable}")
                }
            )
            Text(text = model.name, color = Color.White)
        }
    }
}

@Composable
fun PokemonListViewCmp(model: List<PokemonUiModel>, onEndReached: () -> Unit) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState
    ) {
        itemsIndexed(model, key = { _, item -> item.url }) { _, item ->
            PokemonListItemViewCmp(item)
        }
    }
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }

    LaunchedEffect(endOfListReached) {
        onEndReached()
    }
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1


private fun parseColor(hex: String): Color {
    val normalized = hex.removePrefix("#")
    return runCatching {
        Color(normalized.toLong(16) or 0x00000000FF000000)
    }.getOrDefault(Color(0xFF64748B))
}
