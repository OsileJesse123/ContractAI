package com.jesse.contractai.domain.use_case

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jesse.contractai.work_manager.worker.SignOutWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    @ApplicationContext private val context: Context
){

    operator fun invoke(){
        val workTag = "sign out work"
        val signOutWork = OneTimeWorkRequestBuilder<SignOutWorker>()
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            workTag,
            ExistingWorkPolicy.KEEP,
            signOutWork
        )
    }
}