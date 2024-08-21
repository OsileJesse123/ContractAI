package com.jesse.contractai.data.repository.user_authentication

import com.jesse.contractai.ui.model.User
import kotlinx.coroutines.flow.SharedFlow

interface UserAuthenticationRepository {

    val user: SharedFlow<User?>

    var rememberPassword: Boolean

    suspend fun signUpWithEmailAndPassword(firstName: String, lastName: String, email: String, password: String): Result<User>

    suspend fun verifyUserEmail(): Result<Unit>

    suspend fun hasTheUserBeenVerified(): Boolean

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User>

    suspend fun sendPasswordResetEmail(email: String): Result<Unit>

    suspend fun signInWithGoogle(idToken: String): Result<User>

    fun signOut()
}