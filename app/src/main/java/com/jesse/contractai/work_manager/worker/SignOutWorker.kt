package com.jesse.contractai.work_manager.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import com.jesse.contractai.ui.sign_in_handler.GoogleSignInHandler
import com.jesse.contractai.util.SignInMethod
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SignOutWorker@AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val userAuthenticationRepository: UserAuthenticationRepository,
    private val googleSignInHandler: GoogleSignInHandler
): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        when(userAuthenticationRepository.signInMethod){
            SignInMethod.EMAIL_PASSWORD.name -> {
                userAuthenticationRepository.signOut()
            }
            SignInMethod.GOOGLE.name -> {
                googleSignInHandler.signOut()
                userAuthenticationRepository.signOut()
            }
        }
        return Result.success()
    }
}