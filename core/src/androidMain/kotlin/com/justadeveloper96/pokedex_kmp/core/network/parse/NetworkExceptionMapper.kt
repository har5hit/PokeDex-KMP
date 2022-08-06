package com.justadeveloper96.pokedex_kmp.core.network.parse

import com.justadeveloper96.pokedex_kmp.core.network.model.ApiMessages
import java.net.SocketException
import java.net.UnknownHostException

class NetworkExceptionMapper : INetworkExceptionMapper {
    override fun provideMessage(e: Exception): String {
        if (e is UnknownHostException) {
            return ApiMessages.ERR_NO_INTERNET
        }

        if (e is SocketException) {
            return ApiMessages.ERR_TIMEOUT
        }
        return ApiMessages.ERR_DEFAULT_MSG
    }
}
