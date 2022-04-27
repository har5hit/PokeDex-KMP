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

package com.justadeveloper96.pokedex_kmp.helpers.flow

import androidx.lifecycle.LifecycleCoroutineScope
import com.justadeveloper96.pokedex_kmp.helpers.view.IView
import com.justadeveloper96.pokedex_kmp.helpers.viewmodel.IFlowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class FlowVMConnector<T, E>(private val view: IView<T, E>, private val vm: IFlowViewModel<T, E>) {

    fun observe(flowScope: CoroutineScope) {
        if (flowScope is LifecycleCoroutineScope) {
            observe(flowScope)
        } else {
            flowScope.launch {
                observeState()
            }
            flowScope.launch {
                observeEvent()
            }
        }
    }

    fun observe(flowScope: LifecycleCoroutineScope, launchWhen: LaunchWhen = LaunchWhen.STARTED) {
        when (launchWhen) {
            LaunchWhen.STARTED -> {
                flowScope.launchWhenStarted {
                    observeState()
                }
                flowScope.launchWhenStarted {
                    observeEvent()
                }
            }

            LaunchWhen.RESUMED -> {
                flowScope.launchWhenResumed {
                    observeState()
                }
                flowScope.launchWhenResumed {
                    observeEvent()
                }
            }
            LaunchWhen.CREATED -> {
                flowScope.launchWhenCreated {
                    observeState()
                }
                flowScope.launchWhenCreated {
                    observeEvent()
                }
            }
        }
    }

    val stateSnapShot: T?
        get() = vm.stateSnapShot

    private val TAG = "FlowVMConnector"

    private suspend inline fun observeState() {
        supervisorScope {
            vm.stateHolder.collect {
                view.render(it)
            }
        }
    }

    private suspend inline fun observeEvent() {
        supervisorScope {
            vm.eventHolder.collect { value ->
                value?.let {
                    view.onEvent(it)
                    vm.onEventConsumed()
                }
            }
        }
    }

    enum class LaunchWhen {
        STARTED, RESUMED, CREATED
    }
}
