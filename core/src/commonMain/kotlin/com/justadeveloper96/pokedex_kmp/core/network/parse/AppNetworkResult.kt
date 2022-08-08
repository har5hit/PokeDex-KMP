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

package com.justadeveloper96.pokedex_kmp.core.network.parse

import com.justadeveloper96.pokedex_kmp.core.network.model.AppServerError
import com.justadeveloper96.pokedex_kmp.helpers.network.NetworkResult
import com.justadeveloper96.pokedex_kmp.helpers.network.State

sealed class AppNetworkResult<T>(
    override val data: T? = null,
    override val code: Int? = null,
    override val message: String? = null,
    override val error: AppServerError? = null,
    override val exception: Exception? = null,
    override val state: State
) : NetworkResult<T, AppServerError>(
    data,
    code,
    message,
    exception,
    error,
    state
) {
    fun <R> map(transform: (T) -> R): AppNetworkResult<R> {
        return when (this) {
            is Success -> {
                Success(transform(data), code, message)
            }
            is Loading -> {
                Loading(data?.let { transform(it) })
            }
            is Unsuccessful -> {
                Unsuccessful(data?.let { transform(it) }, code, error, message)
            }
            is NetworkException -> {
                NetworkException(data?.let { transform(it) }, exception, message)
            }
        }
    }

    fun <T> modify(data: T): AppNetworkResult<T> {
        return when (this) {
            is Success -> {
                Success(data, code, message)
            }
            is Loading -> {
                Loading(data)
            }
            is Unsuccessful -> {
                Unsuccessful(data, code, error, message)
            }
            is NetworkException -> {
                NetworkException(data, exception, message)
            }
        }
    }
}

data class Success<T>(
    override val data: T,
    override val code: Int? = null,
    override val message: String? = null
) :
    AppNetworkResult<T>(data, code = code, message = message, state = State.SUCCESS)

data class Unsuccessful<T>(
    override val data: T? = null,
    override val code: Int,
    override val error: AppServerError? = null,
    override val message: String? = null
) : AppNetworkResult<T>(
    data,
    code = code,
    error = error,
    message = message,
    state = State.UNSUCCESSFUL
)

data class NetworkException<T>(
    override val data: T? = null,
    override val exception: Exception,
    override val message: String
) : AppNetworkResult<T>(data, exception = exception, message = message, state = State.ERROR)

data class Loading<T>(override val data: T? = null) :
    AppNetworkResult<T>(data, state = State.LOADING)
