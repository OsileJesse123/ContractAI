package com.jesse.contractai.ui.sign_in_handler

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.annotation.StringRes
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.jesse.contractai.R
import com.jesse.contractai.data.exceptions.AuthenticationException
import com.jesse.contractai.data.exceptions.NetworkErrorException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleSignInHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(context, gso)

    fun getSignInIntent() = googleSignInClient.signInIntent

    fun signOut(){
        googleSignInClient.signOut()
    }

    fun getIdToken(intent: Intent?): String? {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.getResult(ApiException::class.java)
            return account.idToken
        } catch (e: ApiException) {
            null
        }
    }

}