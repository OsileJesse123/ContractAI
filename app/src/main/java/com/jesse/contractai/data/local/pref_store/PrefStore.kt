package com.jesse.contractai.data.local.pref_store

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val SHARED_PREF = "com.jesse.contractai.shared_pref"
private const val REMEMBER_PASSWORD = "com.jesse.contractai.remember_password"
private const val SIGN_IN_METHOD = "com.jesse.contractai.sign_in_method"

@Singleton
class PrefStore @Inject constructor(
    @ApplicationContext
    private val context: Context
){

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    var rememberPassword: Boolean
        set(value) = editor.putBoolean(REMEMBER_PASSWORD, value).apply()
        get() = sharedPreferences.getBoolean(REMEMBER_PASSWORD, false)

    var signInMethod: String?
        set(value) = editor.putString(SIGN_IN_METHOD, value).apply()
        get() = sharedPreferences.getString(SIGN_IN_METHOD, null)
}