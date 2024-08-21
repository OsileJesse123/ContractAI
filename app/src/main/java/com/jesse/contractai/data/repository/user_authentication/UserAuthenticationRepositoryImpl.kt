package com.jesse.contractai.data.repository.user_authentication

import com.jesse.contractai.data.local.pref_store.PrefStore
import com.jesse.contractai.data.network.service.user_auth_service.UserAuthenticationService
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import com.jesse.contractai.ui.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAuthenticationRepositoryImpl @Inject constructor(
    @Dispatcher(ContractAIDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userAuthenticationService: UserAuthenticationService,
    private val prefStore: PrefStore
): UserAuthenticationRepository {

    override val user: SharedFlow<User?>
        get() = userAuthenticationService.user

    override var rememberPassword: Boolean
        get() = prefStore.rememberPassword
        set(value) {
            prefStore.rememberPassword = value
        }

    override fun signOut() {
        userAuthenticationService.signOut()
        prefStore.rememberPassword = false
        prefStore.signInMethod = null
    }

    override suspend fun signUpWithEmailAndPassword(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User> {
        return withContext(ioDispatcher){
            userAuthenticationService.signUpWithEmailAndPassword(firstName, lastName, email, password)
        }
    }

    override suspend fun verifyUserEmail(): Result<Unit> {
        return withContext(ioDispatcher){
            userAuthenticationService.verifyUserEmail()
        }
    }

    override suspend fun hasTheUserBeenVerified(): Boolean {
        return withContext(ioDispatcher){
            userAuthenticationService.hasTheUserBeenVerified()
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return withContext(ioDispatcher){
            userAuthenticationService.sendPasswordResetEmail(email)
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<User> {
        return withContext(ioDispatcher){
            userAuthenticationService.signInWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<User> {
        return withContext(ioDispatcher){
            userAuthenticationService.signInWithGoogle(idToken)
        }
    }

}