package com.jesse.contractai.work_manager.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SignOutWorker@AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val userAuthenticationRepository: UserAuthenticationRepository
): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        userAuthenticationRepository.signOut()
        return Result.success()
    }
}