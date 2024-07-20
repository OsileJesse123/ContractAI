package com.jesse.contractai.domain.use_case

import com.jesse.contractai.domain.ValidationResult
import javax.inject.Inject
import com.jesse.contractai.R

class ValidateNameUseCase @Inject constructor(){

    operator fun invoke(username: String): ValidationResult {
        if (username.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.this_field_cannot_be_blank
            )
        }
        if (!isUsernamePatternValid(username)){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.invalid_name_error
            )
        }
        return ValidationResult(successful = true)
    }

    private fun isUsernamePatternValid(username: String): Boolean {
        /*
        * ^ and $: These symbols indicate the start and end of the string, respectively, ensuring that the regex matches the entire string.
          [a-zA-Z]: This part allows only letters, both uppercase and lowercase. No numbers or symbols are allowed.
          {3,20}: This specifies that the username should be between 3 and 20 characters long.
          So, this regex pattern enforces your criteria for a username: it's no longer than 20
          characters, doesn't allow blank spaces, and only allows letters (no numbers or symbols).
        * */
        val regexPattern = Regex("^[a-zA-Z]{3,20}\$")
        return regexPattern.matches(username)
    }
}