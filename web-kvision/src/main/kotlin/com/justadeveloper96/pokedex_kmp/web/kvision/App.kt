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

package com.justadeveloper96.pokedex_kmp.web.kvision

import com.justadeveloper96.pokedex_kmp.web.kvision.di.InitKoin
import com.justadeveloper96.pokedex_kmp.web.kvision.presentation.pokemon_list.view.pokemonListScreen
import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.OnsenUIModule
import io.kvision.ToastModule
import io.kvision.module
import io.kvision.panel.root
import io.kvision.require
import io.kvision.startApplication
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel

class App : Application() {

    private val scope = CoroutineScope(window.asCoroutineDispatcher() + SupervisorJob())

    init {
        require("./css/kvapp.css")
    }

    override fun start() {
        root("kvapp") {
            InitKoin().invoke().then { di ->
//                if (!OnsenUi.isAndroid(true) && !OnsenUi.isIOS(true)) {
//                    document.body?.classList?.add("mobile-emulate")
//                    OnsenUi.mockStatusBar()
//                }
                pokemonListScreen(di)
            }
        }
    }

    override fun dispose(): Map<String, Any> {
        scope.cancel()
        return super.dispose()
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        OnsenUIModule,
        ToastModule,
        CoreModule
    )
}
