package com.jesse.contractai.domain.use_case

import com.jesse.contractai.ui.screens.sign_up.PasswordStrength
import javax.inject.Inject

class ValidatePasswordStrengthUseCase @Inject constructor(){

    operator fun invoke(password: String): PasswordStrength{
        val containsLowerCase = password.any { it.isLowerCase() }
        val containsUpperCase = password.any { it.isUpperCase() }
        val containsDigits = password.any { it.isDigit() }
        // This checks to see if the password contains anything that is not an element of
        // "a-z", "A-Z", "0-9". Simply put, does the password contains symbols.
        val regex = Regex("[^a-zA-Z0-9]")
        val containsSymbols = password.any { it.toString().matches(regex) }
        // Valid password length is anything between 8 - x where x is any positive integer greater
        // than 8.
        val hasValidPasswordLength = password.length >= 8
        val conditionsForPasswordStrength = listOf(containsLowerCase, containsUpperCase, containsDigits, containsSymbols, hasValidPasswordLength)
        return when{
            conditionsForPasswordStrength.count { it } == 5 -> PasswordStrength.VERY_STRONG
            conditionsForPasswordStrength.count { it } >= 3 && hasValidPasswordLength -> PasswordStrength.STRONG
            else -> PasswordStrength.WEAK
        }
    }
}