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

package com.justadeveloper96.pokedex_kmp.core.network

import com.justadeveloper96.pokedex_kmp.core.data.network.mapper.ApiMessages
import com.justadeveloper96.pokedex_kmp.core.data.network.mapper.NetworkException
import com.justadeveloper96.pokedex_kmp.core.data.network.mapper.Success
import com.justadeveloper96.pokedex_kmp.core.data.network.mapper.Unsuccessful
import com.justadeveloper96.pokedex_kmp.core.data.network.model.AppServerError
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.net.SocketException

class NetworkResponseParsingTest : StringSpec({

    "success case" {
        val body = "Abc"
        val code = 200

        val expected = Success(
            body,
            code
        )
        val actual = parseToAppNetworkResult<String> { NetworkResponseData(code, body) }

        actual shouldBe expected
    }

    "success case 2" {
        val body = "Abc"
        val code = 200

        val expected = Success(mapOf("data" to body), code)
        val actual = parseToAppNetworkResult<String> {
            NetworkResponseData(
                code,
                body
            )
        }.map { result -> mapOf("data" to result) }

        actual shouldBe expected
    }

    "Unsuccessful case" {
        val errorMsg = "Bad Request"
        val body = """
            {
              "error": "Bad Request"
            }
        """.trimIndent()
        val code = 400

        val expected = Unsuccessful<String>(
            error = AppServerError(errorMsg),
            message = errorMsg,
            code = code
        )
        val actual = parseToAppNetworkResult<String> { NetworkResponseData(code, body) }

        actual shouldBe expected
    }

    "error case" {
        val exception = SocketException()

        val expected = NetworkException<String>(
            message = ApiMessages.ERR_TIMEOUT,
            exception = exception
        )
        val actual = parseToAppNetworkResult<String> { throw exception }

        actual shouldBe expected
    }
})
