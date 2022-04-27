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

package com.justadeveloper96.pokedex_kmp.helpers.local

import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

class LocalStorage() : ILocalStorage {

    override fun getString(key: String): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey(key)
    }

    override fun putString(key: String, value: String?) {
        NSUserDefaults.standardUserDefaults.setValue(value = value, forKey = key)
    }

    override fun putInt(key: String, value: Int?) {
        NSUserDefaults.standardUserDefaults.setValue(value = value, forKey = key)
    }

    override fun getInt(key: String): Int? {
        return try {
            NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun clear() {
        val dict = NSUserDefaults.standardUserDefaults.dictionaryRepresentation()
        dict.forEach {
            try {
                NSUserDefaults.standardUserDefaults.removeObjectForKey(it.key as String)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        NSUserDefaults.standardUserDefaults.synchronize()
    }
}
