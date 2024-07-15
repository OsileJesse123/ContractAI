package com.jesse.contractai.domain

import androidx.annotation.StringRes

data class ValidationResult(
    val successful: Boolean,
    @StringRes val errorMessage: Int? = null
)
