package com.justadeveloper96.pokedex_kmp.feature_pokemon_list.presentation.pokemon_list.viewmodel

import com.justadeveloper96.pokedex_kmp.core.network.model.AppNetworkResult
import com.justadeveloper96.pokedex_kmp.core.network.model.Loading
import com.justadeveloper96.pokedex_kmp.core.network.model.Success
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.IPokemonRepository
import com.justadeveloper96.pokedex_kmp.feature_pokemon_list.data.pokemon.repository.model.Pokemon
import com.justadeveloper96.pokedex_kmp.helpers.coroutine.AppCoroutineDispatchers
import com.justadeveloper96.pokedex_kmp.helpers.pagination.PaginatedList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest : StringSpec({

    val repository: IPokemonRepository = mockk()

    val dispatcher = UnconfinedTestDispatcher()
    val dispatchers = AppCoroutineDispatchers(dispatcher, dispatcher, dispatcher, dispatcher)

    "inital state test" {
        val viewModel = PokemonListViewModel(dispatchers, repository)

        val dataChannel = Channel<AppNetworkResult<PaginatedList<Pokemon>>>(1)
        val data = PaginatedList(List(10) { i -> Pokemon(i.toString(), i.toString()) }, total = 10)
        coEvery { repository.get(0, 10) } returns dataChannel.consumeAsFlow()

        dataChannel.trySend(Loading(data))

        viewModel.add(IPokemonListViewModel.Action.Fetch)

        dataChannel.trySend(Success(data, 200))

        viewModel.stateHolder.value shouldBe IPokemonListViewModel.UIState(list = data.data.map { it.toPokemonUiModel() })
    }

    "pagination for new items and refresh" {
        val viewModel = PokemonListViewModel(dispatchers, repository)

        val dataChannel = Channel<AppNetworkResult<PaginatedList<Pokemon>>>(1)
        val data1 = PaginatedList(List(10) { i -> Pokemon(i.toString(), i.toString()) }, total = 20)
        val data2 = PaginatedList(List(20) { i -> Pokemon(i.toString(), i.toString()) }, total = 20)
        coEvery { repository.get(eq(0), eq(10)) } answers {
            dataChannel.trySend(Success(data1, 200))
            dataChannel.consumeAsFlow()
        }
        coEvery { repository.get(eq(10), eq(10)) } answers {
            dataChannel.trySend(Success(data2, 200))
            dataChannel.consumeAsFlow()
        }

        viewModel.add(IPokemonListViewModel.Action.Fetch)

        viewModel.stateHolder.value shouldBe IPokemonListViewModel.UIState(list = data1.data.map { it.toPokemonUiModel() }, canLoadMore = true)

        viewModel.add(IPokemonListViewModel.Action.Fetch)

        viewModel.stateHolder.value shouldBe IPokemonListViewModel.UIState(list = data2.data.map { it.toPokemonUiModel() }, canLoadMore = false)

        viewModel.add(IPokemonListViewModel.Action.Fetch)

        coVerify(inverse = true) { repository.get(eq(30), eq(10)) }

        viewModel.add(IPokemonListViewModel.Action.Refresh)

        viewModel.stateHolder.value shouldBe IPokemonListViewModel.UIState(list = data1.data.map { it.toPokemonUiModel() }, canLoadMore = true)
    }
})
