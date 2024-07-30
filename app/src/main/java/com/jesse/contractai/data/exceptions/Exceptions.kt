package com.jesse.contractai.data.exceptions

class NoResponseException: Exception()

// Network Error Exception
class NetworkErrorException: Exception()

// Authentication Exceptions
sealed class AuthenticationException: Exception(){
    class NoUserException: AuthenticationException()
    class EmailAlreadyInUseException: AuthenticationException()
    class AccountExistWithDifferentCredentialException: AuthenticationException()
    class CredentialAlreadyInUseException: AuthenticationException()
    class InvalidCredentialsException: AuthenticationException()
    class UserDisabledException: AuthenticationException()
    class UserTokenExpiredException: AuthenticationException()
    class InvalidUserTokenException: AuthenticationException()
    class TooManyRequestsException: AuthenticationException()
    class AuthUserCollisionException: AuthenticationException()
    class AuthRecentLoginRequiredException: AuthenticationException()
    class WeakPasswordException: AuthenticationException()
    class InvalidUserException: AuthenticationException()
    class SignInCancelledException: AuthenticationException()
}