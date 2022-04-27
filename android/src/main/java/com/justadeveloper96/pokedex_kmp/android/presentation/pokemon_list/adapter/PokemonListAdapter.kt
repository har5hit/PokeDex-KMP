/*
 * Copyright 2020 Harshith Shetty (justadeveloper96@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonUiModel

val TAG = "diffUtil"
val diffUtil = object : DiffUtil.ItemCallback<PokemonUiModel>() {
    override fun areItemsTheSame(oldItem: PokemonUiModel, newItem: PokemonUiModel): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: PokemonUiModel, newItem: PokemonUiModel): Boolean {
        return oldItem.url == newItem.url
    }
}

class PokemonListAdapter : AsyncListDifferDelegationAdapter<PokemonUiModel>(
    diffUtil,
    AdapterDelegatesManager<List<PokemonUiModel>>().apply {
        addDelegate(pokemonListItemDelegate())
    }
)
