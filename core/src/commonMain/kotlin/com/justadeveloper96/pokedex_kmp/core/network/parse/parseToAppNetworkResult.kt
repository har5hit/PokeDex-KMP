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

package com.justadeveloper96.pokedex_kmp.core.network.parse

import com.justadeveloper96.pokedex_kmp.core.network.model.AppNetworkResult
import com.justadeveloper96.pokedex_kmp.core.network.model.AppServerError
import com.justadeveloper96.pokedex_kmp.core.network.model.NetworkException
import com.justadeveloper96.pokedex_kmp.core.network.model.Success
import com.justadeveloper96.pokedex_kmp.core.network.model.Unsuccessful
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.SharedImmutable

data class NetworkResponseData(val code: Int, val body: String?)

@SharedImmutable
val parser = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

inline fun <reified T> parseToAppNetworkResult(
    networkExceptionMapping: INetworkExceptionMapper,
    serviceCall: () -> NetworkResponseData
): AppNetworkResult<T> {
    return try {
        val data = serviceCall()
        try {
            if (data.code in 200..299) {
                val body = parser.decodeFromString<T>(data.body!!)
                Success(data = body, code = data.code)
            } else {
                val error = parser.decodeFromString<AppServerError>(data.body!!)
                Unsuccessful(
                    error = error,
                    code = data.code,
                    message = error.error
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Unsuccessful(
                code = data.code,
                message = networkExceptionMapping.provideMessage(e)
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        NetworkException(
            message = networkExceptionMapping.provideMessage(e),
            exception = e
        )
    }
}
