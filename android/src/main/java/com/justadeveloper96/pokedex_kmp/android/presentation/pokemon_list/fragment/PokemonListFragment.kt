package com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.justadeveloper96.pokedex_kmp.android.R
import com.justadeveloper96.pokedex_kmp.android.databinding.FragmentPokemonListBinding
import com.justadeveloper96.pokedex_kmp.android.presentation.pokemon_list.adapter.PokemonListAdapter
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.IPokemonListViewModel
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel.PokemonListViewModel
import com.justadeveloper96.pokedex_kmp.helpers.flow.FlowVMConnector
import com.justadeveloper96.pokedex_kmp.helpers.fragment.BaseFragment
import com.justadeveloper96.pokedex_kmp.helpers.view.IView
import com.justadeveloper96.pokedex_kmp.helpers.viewmodel.getViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class PokemonListFragment :
    BaseFragment<FragmentPokemonListBinding>(),
    IView<IPokemonListViewModel.UIState, IPokemonListViewModel.UIEvent> {

    @Inject
    lateinit var viewModelProvider: Provider<PokemonListViewModel>
    private val viewModel by lazy {
        getViewModel { viewModelProvider.get() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelProvider.get()
        setupView()
        subscribe()
        viewModel.add(IPokemonListViewModel.Action.Fetch)
    }

    private fun subscribe() {
        FlowVMConnector(this, viewModel).observe(lifecycleScope)
    }

    override val layout: Int
        get() = R.layout.fragment_pokemon_list

    private val adapter by lazy { PokemonListAdapter() }

    private fun setupView() {
        binding.refresh.setOnRefreshListener {
            viewModel.add(IPokemonListViewModel.Action.Refresh)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.onEndReached {
            viewModel.add(IPokemonListViewModel.Action.Fetch)
        }
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

    override fun render(state: IPokemonListViewModel.UIState) {
        Log.d(TAG, "render: ${state.list.size} $state")
        adapter.items = state.list
        binding.refresh.isRefreshing = state.loading
    }
}
