package com.jesse.contractai.ui.sign_in_handler

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.jesse.contractai.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleSignInHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val oneTapClient: SignInClient = Identity.getSignInClient(context)

    private val signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false)
                .build()
        ).build()

    fun getBeginSignInResult(): Task<BeginSignInResult>{
        return oneTapClient.beginSignIn(signInRequest)
    }

    fun getSignInToken(data: Intent?): String? {
        return try {
            val credential = oneTapClient.getSignInCredentialFromIntent(data)
            // Return sign in token
            credential.googleIdToken
        } catch (e: ApiException){
            Log.e("Google", "Exception: ${e.status}")
            null
        }
    }

}