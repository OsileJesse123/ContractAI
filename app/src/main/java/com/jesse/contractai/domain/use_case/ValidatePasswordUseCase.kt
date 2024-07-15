package com.jesse.contractai.domain.use_case

import com.jesse.contractai.R
import com.jesse.contractai.domain.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String,): ValidationResult {
        if (password.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.this_field_cannot_be_blank
            )
        }
        return ValidationResult(successful = true)
    }
}