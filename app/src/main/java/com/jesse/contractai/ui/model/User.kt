package com.jesse.contractai.ui.model

data class User(
    val id: String,
    val isEmailVerified: Boolean,
    val email: String?,
    val userName: String?
)
