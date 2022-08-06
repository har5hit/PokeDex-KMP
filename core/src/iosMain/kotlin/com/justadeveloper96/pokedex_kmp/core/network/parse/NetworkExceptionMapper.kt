package com.justadeveloper96.pokedex_kmp.core.network.parse

import com.justadeveloper96.pokedex_kmp.core.network.model.ApiMessages

class NetworkExceptionMapper : INetworkExceptionMapper {
    override fun provideMessage(e: Exception): String {
        return e.message ?: ApiMessages.ERR_DEFAULT_MSG
    }
}
