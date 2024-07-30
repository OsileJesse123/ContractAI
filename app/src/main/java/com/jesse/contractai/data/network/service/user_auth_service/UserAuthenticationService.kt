package com.jesse.contractai.data.network.service.user_auth_service

import com.jesse.contractai.ui.model.User
import kotlinx.coroutines.flow.SharedFlow

interface UserAuthenticationService {

    val user: SharedFlow<User?>
    suspend fun signUpWithEmailAndPassword(firstName: String, lastName: String, email: String, password: String): Result<User>
    suspend fun verifyUserEmail(): Result<Unit>
    suspend fun hasTheUserBeenVerified(): Boolean
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    suspend fun signInWithGoogle(idToken: String): Result<User>
    fun signOut()
}