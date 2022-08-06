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
