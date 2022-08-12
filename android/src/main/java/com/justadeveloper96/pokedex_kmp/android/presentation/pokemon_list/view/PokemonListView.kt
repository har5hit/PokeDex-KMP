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

package com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.view

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
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.IPokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonUiModel

@Composable
fun PokemonListItemView(model: PokemonUiModel) {
    val cardModifier = Modifier.padding(12.dp).height(150.dp)
    Card(modifier = cardModifier) {
        val boxModifier =
            Modifier.fillMaxSize()
                .background(color = Color(android.graphics.Color.parseColor(model.color)))
                .padding(12.dp)
        Box(modifier = boxModifier) {
            val imageModifier =
                Modifier.align(Alignment.CenterEnd)
                    .width(120.dp)
                    .height(120.dp)
            AsyncImage(
                model = model.imagePng,
                contentDescription = null,
                modifier = imageModifier
            )
            Text(text = model.name, style = Typography().body1, color = Color.White)
        }
    }
}

@Composable
fun PokemonListView(model: List<PokemonUiModel>, onEndReached: () -> Unit) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState
    ) {
        itemsIndexed(model, key = { _, item -> item.url }) { _, item ->
            PokemonListItemView(item)
        }
    }
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }

    // act when end of list reached
    LaunchedEffect(endOfListReached) {
        onEndReached()
    }
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

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
    PokemonListItemView(uiState.list[0])
}
