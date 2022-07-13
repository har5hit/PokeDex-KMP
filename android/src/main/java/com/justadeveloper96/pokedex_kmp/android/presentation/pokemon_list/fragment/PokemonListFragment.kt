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
import com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.screen.PokemonListScreen
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.IPokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonListViewModel
import com.justadeveloper96.pokedex_kmp.helpers.flow.VMEventConnector
import com.justadeveloper96.pokedex_kmp.helpers.view.IEventView
import com.justadeveloper96.pokedex_kmp.helpers.viewmodel.getViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class PokemonListFragment : Fragment(), IEventView<IPokemonListViewModel.UIEvent> {

    @Inject
    lateinit var viewModelProvider: Provider<PokemonListViewModel>
    private val viewModel by lazy {
        getViewModel { viewModelProvider.get() }
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
