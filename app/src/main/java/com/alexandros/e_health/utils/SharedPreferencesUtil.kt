package com.alexandros.e_health.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {

    private lateinit var sharedPreferences: SharedPreferences
    @JvmStatic
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    @JvmStatic
    fun writeToSharedPreferences(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
    @JvmStatic
    fun readFromSharedPreferences(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
    @JvmStatic
    fun saveAccessToken(accessToken: String) {
        writeToSharedPreferences(Constants.ACCESS_TOKEN, accessToken)
    }
    @JvmStatic
    fun getAccessToken(): String? {
        return readFromSharedPreferences(Constants.ACCESS_TOKEN)
    }
    @JvmStatic
    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

}