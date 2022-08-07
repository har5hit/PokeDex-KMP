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

package com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.justadeveloper96.pokedex_kmp.android.helpers.flow.IEventView
import com.justadeveloper96.pokedex_kmp.android.helpers.flow.VMEventConnector
import com.justadeveloper96.pokedex_kmp.android.helpers.viewmodel.getViewModel
import com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.screen.PokemonListScreen
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.IPokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonListViewModel
import org.koin.android.ext.android.get

class PokemonListFragment : Fragment(), IEventView<IPokemonListViewModel.UIEvent> {

    private val viewModel by lazy {
        getViewModel {
            get<PokemonListViewModel>()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.stateHolder.collectAsState()
                PokemonListScreen(state = uiState) {
                    viewModel.add(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeEvents()
        viewModel.add(IPokemonListViewModel.Action.Fetch)
    }

    private fun subscribeEvents() {
        VMEventConnector(this, viewModel.eventHolder) { viewModel.onEventConsumed() }.observe(
            viewLifecycleOwner
        )
    }

    override fun onEvent(event: IPokemonListViewModel.UIEvent) {
        Log.d(TAG, "event: $event")
        when (event) {
            is IPokemonListViewModel.UIEvent.Message -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val TAG = "PokemonListFragment"
}
