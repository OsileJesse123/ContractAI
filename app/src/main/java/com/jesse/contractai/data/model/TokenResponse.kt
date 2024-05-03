package com.jesse.contractai.data.model


data class TokenResponse(
    val accessToken: String,
    val expiresIn: Long,
    val tokenType: String
)

