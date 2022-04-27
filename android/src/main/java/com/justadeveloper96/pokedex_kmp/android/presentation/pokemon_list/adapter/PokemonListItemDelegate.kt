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

import android.graphics.Color
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.justadeveloper96.pokedex_kmp.android.databinding.ItemPokemonBinding
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonUiModel

fun pokemonListItemDelegate() =
    adapterDelegateViewBinding<PokemonUiModel, PokemonUiModel, ItemPokemonBinding>(
        { layoutInflater, root -> ItemPokemonBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.data = item
            Glide.with(binding.root.context).load(item.image).into(binding.image)
            binding.flBg.setBackgroundColor(Color.parseColor(item.color))
        }
    }
