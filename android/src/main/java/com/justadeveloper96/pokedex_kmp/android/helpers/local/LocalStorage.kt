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

package com.justadeveloper96.pokedex_kmp.android.helpers.local

import android.content.Context
import com.justadeveloper96.pokedex_kmp.helpers.local.ILocalStorage

class LocalStorage(val context: Context) : ILocalStorage {
    private val prefs = context.getSharedPreferences("app", Context.MODE_PRIVATE)

    override fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    override fun putString(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    override fun putInt(key: String, value: Int?) {
        if (value == null) {
            prefs.edit().remove(key).apply()
        } else {
            prefs.edit().putInt(key, value).apply()
        }
    }

    override fun getInt(key: String): Int? {
        return if (prefs.contains(key)) {
            prefs.getInt(key, -1)
        } else {
            null
        }
    }

    override fun clear() {
        prefs.edit().clear().apply()
    }
}
