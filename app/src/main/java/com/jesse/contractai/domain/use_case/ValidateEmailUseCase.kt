package com.jesse.contractai.domain.use_case

import com.jesse.contractai.R
import com.jesse.contractai.domain.ValidationResult
import com.jesse.contractai.util.EmailMatcher
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val emailMatcher: EmailMatcher
) {

    operator fun invoke(email: String): ValidationResult {

        if(email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.this_field_cannot_be_blank
            )
        }
        if(!emailMatcher.emailMatches(email)){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.invalid_email
            )
        }
        return ValidationResult(successful = true)
    }


}