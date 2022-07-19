/*
 * MIT License
 *
 * Copyright (c) 2020 Harshith Shetty (justadeveloper96@gmail.com)
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

package com.justadeveloper96.pokedex_kmp.helpers.viewmodel

import com.justadeveloper96.pokedex_kmp.helpers.coroutine.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T, E>(dispatchers: AppCoroutineDispatchers) :
    ViewModel(dispatchers), IFlowViewModel<T, E> {

    abstract val TAG: String

    abstract val initialState: T

    private val _stateHolder: MutableStateFlow<T> = MutableStateFlow(initialState)

    private val _eventHolder: MutableStateFlow<E?> = MutableStateFlow(null)

    override val eventHolder: Flow<E?>
        get() = _eventHolder.asStateFlow()

    override val stateHolder: StateFlow<T>
        get() = _stateHolder

    init {
        setState(initialState)
    }

    protected fun setState(state: T) {
        _stateHolder.value = state
    }

    protected fun pushEvent(event: E?) {
        vmScope.launch(dispatchers.mainImmediate) {
            _eventHolder.value = event
        }
    }

    override fun onEventConsumed() {
        pushEvent(null)
    }
}
