package com.waffle.localmovieapplication.module

import android.annotation.SuppressLint
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreferences(val context: Context) {
    companion object {
        private const val PREF_NAME ="waffle.localMovieApplication"
        private const val NOTIFICATION = "notification"
    }
    @SuppressLint("NewApi")
    private val masterKeyAlias= MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val pref = EncryptedSharedPreferences.create(context, PREF_NAME, masterKeyAlias, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)


    var notification : Boolean
        get() = pref.getBoolean(NOTIFICATION, false)
        set(value) = pref.edit().putBoolean(NOTIFICATION,value).apply()

}