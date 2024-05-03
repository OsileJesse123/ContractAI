package com.jesse.contractai.data.service

import com.jesse.contractai.data.model.TokenResponse

interface TokenService {
    suspend fun getAccessToken(
        grantType: String = "client_credentials",
        clientId: String,
        clientSecret: String
    ): TokenResponse
}
