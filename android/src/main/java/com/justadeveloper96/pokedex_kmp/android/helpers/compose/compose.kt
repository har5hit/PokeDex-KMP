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

package com.justadeveloper96.pokedex_kmp.android.helpers.compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.justadeveloper96.pokedex_kmp.helpers.viewmodel.IFlowViewModel
import kotlinx.coroutines.flow.filter

private const val TAG = "Compose"

@Composable
fun <S, E, A> viewModelViewWrapper(
    viewModel: IFlowViewModel<S, E, A>,
    block: @Composable (S) -> Unit
) {
    Log.d(TAG, "viewModelViewWrapper start")
    val state by viewModel.stateHolder.collectAsState()
    block(state)
    Log.d(TAG, "viewModelViewWrapper end $state")
}

@Composable
fun <S, E, A> viewModelEventWrapper(
    viewModel: IFlowViewModel<S, E, A>,
    block: @Composable (E) -> Unit
) {
    Log.d(TAG, "viewModelEventWrapper start")
    val event by viewModel.eventHolder.filter { it != null }.collectAsState(null)
    event?.let {
        block(it)
        viewModel.onEventConsumed()
    }
    Log.d(TAG, "viewModelEventWrapper end $event")
}

@Composable
fun <S, E, A> viewModelContainerWrapper(
    viewModel: IFlowViewModel<S, E, A>,
    viewBlock: @Composable (S) -> Unit,
    eventBlock: @Composable (E) -> Unit
) {
    Log.d(TAG, "viewModelContainerWrapper start")
    viewModelViewWrapper(viewModel) {
        viewBlock(it)
    }
    viewModelEventWrapper(viewModel) {
        eventBlock(it)
    }
    Log.d(TAG, "viewModelContainerWrapper end")
}
