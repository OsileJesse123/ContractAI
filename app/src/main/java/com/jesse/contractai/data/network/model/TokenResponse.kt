package com.jesse.contractai.data.network.model


data class TokenResponse(
    val accessToken: String,
    val expiresIn: Long,
    val tokenType: String
)

