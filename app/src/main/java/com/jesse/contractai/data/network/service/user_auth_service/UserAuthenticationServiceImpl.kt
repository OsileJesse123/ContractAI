package com.jesse.contractai.data.network.service.user_auth_service

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.jesse.contractai.data.exceptions.AuthenticationException
import com.jesse.contractai.data.exceptions.NetworkErrorException
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import com.jesse.contractai.ui.model.User
import com.jesse.contractai.util.SPLIT_FIRST_AND_LAST_NAME_WITH_WHITESPACE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserAuthenticationServiceImpl @Inject constructor(
    @Dispatcher(ContractAIDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): UserAuthenticationService {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _user: MutableSharedFlow<User?> = MutableSharedFlow()
    override val user: SharedFlow<User?> = _user.shareIn(
        CoroutineScope(ioDispatcher),
        started = SharingStarted.Lazily,
        replay = 1
    ).onSubscription {
        emit(getUser())
    }

    private fun getUser(): User? {
        return firebaseAuth.currentUser?.let {
                firebaseUser ->
            User(
                id = firebaseUser.uid,
                isEmailVerified =  firebaseUser.isEmailVerified,
                email = firebaseUser.email,
                userName = firebaseUser.displayName
            )
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null){
                updateUserName(user, "$firstName$SPLIT_FIRST_AND_LAST_NAME_WITH_WHITESPACE$lastName")
                // If registration task is successful and user is not null
                val authUser = User(
                    id = user.uid,
                    isEmailVerified =  user.isEmailVerified,
                    email = user.email,
                    userName = user.displayName
                )
                _user.emit(authUser)
                Result.success(authUser)
            } else {
                Result.failure(AuthenticationException.NoUserException())
            }
        } catch (e: FirebaseAuthUserCollisionException){
            when(e.errorCode){
                FirebaseErrorCode.ERROR_EMAIL_ALREADY_IN_USE.name -> Result.failure(AuthenticationException.EmailAlreadyInUseException())
                FirebaseErrorCode.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL.name -> Result.failure(AuthenticationException.AccountExistWithDifferentCredentialException())
                FirebaseErrorCode.ERROR_CREDENTIAL_ALREADY_IN_USE.name -> Result.failure(AuthenticationException.CredentialAlreadyInUseException())
                else -> Result.failure(Exception())
            }
        }
        catch (e: FirebaseNetworkException){
            Result.failure(NetworkErrorException())
        }
        catch (e: Exception){
            Log.e("SignUpError", "SignUp Error: $e")
            Result.failure(e)
        }
    }

    override suspend fun verifyUserEmail(): Result<Unit> {
        return try{
            val user = firebaseAuth.currentUser
            if (user != null){
                user.sendEmailVerification().await()
                Result.success(Unit)
            } else{
                Result.failure(Exception())
            }
        }
        catch (e: FirebaseNetworkException){
            Result.failure(NetworkErrorException())
        }
        catch (e: FirebaseTooManyRequestsException){
            Result.failure(AuthenticationException.TooManyRequestsException())
        }
        catch (e: Exception){
            Log.e("VerifyEmailError","Send email verification failed, Exception: $e")
            Result.failure(e)
        }
    }

    override suspend fun hasTheUserBeenVerified(): Boolean {
        return try {
            val user = firebaseAuth.currentUser
            if(user != null){
                // If the user is not null reload the user to get an updated status of the user then
                // check to see if user's email is verified.
                user.reload().await()
                user.isEmailVerified
            } else{
                // Else the user is null hence has not been verified
                false
            }
        } catch (e: Exception){
            Log.e("UserEmailVerified","Has user been verified failed, Exception: $e")
            false
        }
    }

    private suspend fun updateUserName(user: FirebaseUser, userName: String){
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .build()
        user.updateProfile(profileUpdates).await()
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<User> {
        return try{
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null){
                // If login task is successful and user is not null
                val authUser = User(
                    id = user.uid,
                    isEmailVerified =  user.isEmailVerified,
                    email = user.email,
                    userName = user.displayName
                )
                _user.emit(authUser)
                Result.success(authUser)
            } else {
                // If login task is successful and user is null
                Result.failure(AuthenticationException.NoUserException())
            }
        }
        catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(AuthenticationException.InvalidCredentialsException())
        }
        catch (e: FirebaseAuthInvalidUserException){
            when(e.errorCode){
                FirebaseErrorCode.ERROR_USER_DISABLED.name -> Result.failure(AuthenticationException.UserDisabledException())
                FirebaseErrorCode.ERROR_USER_NOT_FOUND.name -> Result.failure(AuthenticationException.NoUserException())
                FirebaseErrorCode.ERROR_USER_TOKEN_EXPIRED.name -> Result.failure(AuthenticationException.UserTokenExpiredException())
                FirebaseErrorCode.ERROR_INVALID_USER_TOKEN.name -> Result.failure(AuthenticationException.InvalidUserTokenException())
                else -> Result.failure(Exception())
            }
        }
        catch (e: FirebaseNetworkException){
            Result.failure(NetworkErrorException())
        }
        catch (e: Exception){
            Log.e("LoginError","Login Failed, Exception: $e")
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val user = result.user

            if (user != null){
                // If sign in task is successful and user is not null
                val authUser = User(
                    id = user.uid,
                    isEmailVerified =  user.isEmailVerified,
                    email = user.email,
                    userName = user.displayName
                )
                _user.emit(authUser)
                Result.success(authUser)
            } else {
                // If login task is successful and user is null
                Result.failure(AuthenticationException.NoUserException())
            }
        } catch (e: FirebaseAuthInvalidUserException){
            when(e.errorCode){
                FirebaseErrorCode.ERROR_USER_DISABLED.name -> Result.failure(AuthenticationException.UserDisabledException())
                FirebaseErrorCode.ERROR_USER_NOT_FOUND.name -> Result.failure(AuthenticationException.NoUserException())
                FirebaseErrorCode.ERROR_USER_TOKEN_EXPIRED.name -> Result.failure(AuthenticationException.UserTokenExpiredException())
                FirebaseErrorCode.ERROR_INVALID_USER_TOKEN.name -> Result.failure(AuthenticationException.InvalidUserTokenException())
                else -> Result.failure(Exception())
            }
        } catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(AuthenticationException.InvalidCredentialsException())
        } catch (e: FirebaseAuthUserCollisionException){
            Result.failure(AuthenticationException.AuthUserCollisionException())
        }catch (e: Exception){
            Log.e("GoogleSignError","Sign in with google failed, Exception: $e")
            Result.failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        }
        catch (e: FirebaseAuthInvalidUserException){

            when(e.errorCode){
                FirebaseErrorCode.ERROR_USER_DISABLED.name -> Result.failure(AuthenticationException.UserDisabledException())
                FirebaseErrorCode.ERROR_USER_NOT_FOUND.name -> Result.failure(AuthenticationException.NoUserException())
                FirebaseErrorCode.ERROR_USER_TOKEN_EXPIRED.name -> Result.failure(AuthenticationException.UserTokenExpiredException())
                FirebaseErrorCode.ERROR_INVALID_USER_TOKEN.name -> Result.failure(AuthenticationException.InvalidUserTokenException())
                else -> Result.failure(Exception())
            }
        }
        catch (e: FirebaseNetworkException){
            Result.failure(NetworkErrorException())
        }
        catch (e: Exception){
            Log.e("PasswordReset","Password reset failed, Exception: $e")
            Result.failure(e)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}

enum class FirebaseErrorCode{
    ERROR_USER_TOKEN_EXPIRED,
    ERROR_USER_DISABLED,
    ERROR_USER_NOT_FOUND,
    ERROR_EMAIL_ALREADY_IN_USE,
    ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL,
    ERROR_CREDENTIAL_ALREADY_IN_USE,
    ERROR_INVALID_USER_TOKEN;

}