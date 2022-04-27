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
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.net.SocketException

class ApiCallExtensionKtTest : StringSpec({

    "success case" {
        val body = "Abc"
        execute { body } shouldBe Success(body)
    }

    "success case 2" {
        val body = "Abc"

        val modifiedBody = mapOf("data" to body)
        execute(
            { body },
            { result -> mapOf("data" to result) }
        ) shouldBe Success(modifiedBody)
    }

    "error case" {
        val exception = SocketException()
        val expectedError = NetworkException<String>(
            message = ApiMessages.ERR_TIMEOUT,
            exception = exception
        )
        execute<String, Map<String, String>>(
            { throw exception },
            { result -> mapOf("data" to result) }
        ) shouldBe expectedError
    }
})
