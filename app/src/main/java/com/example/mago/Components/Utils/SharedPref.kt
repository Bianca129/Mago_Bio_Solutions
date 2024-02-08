package com.example.mago.Components.Utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private lateinit var sharedPreferences: SharedPreferences
    private val LOCK = Any()

    fun init(context: Context) {
        synchronized(LOCK) {
            sharedPreferences = context.getSharedPreferences("appPref", Context.MODE_PRIVATE)
        }
    }

    //Authtoken
    fun getAuthToken(): String? {
        synchronized(LOCK) {
            return sharedPreferences.getString("token", null)
        }
    }

    fun setAuthToken(token: String) {
        synchronized(LOCK) {
            sharedPreferences.edit().putString("token", token).apply()
        }
    }

    //admin
    fun isAdmin(): Boolean {
        synchronized(LOCK) {
            return sharedPreferences.getString("admin", "false")?.toBoolean() ?: false
        }
    }

    fun setAdmin(isAdmin: Boolean) {
        synchronized(LOCK) {
            sharedPreferences.edit().putString("admin", isAdmin.toString()).apply()
        }
    }


    //Userid
    fun setUserId(userId: String) {
        synchronized(LOCK) {
            sharedPreferences.edit().putString("userId", userId).apply()
        }
    }

    fun getUserId(): String {
        synchronized(LOCK) {
            return sharedPreferences.getString("userId", "") ?: ""
        }
    }

    //username
    fun setUsername(username: String) {
        synchronized(LOCK) {
            sharedPreferences.edit().putString("username", username).apply()
        }
    }

    fun getUsername(): String? {
        synchronized(LOCK) {
            return sharedPreferences.getString("username", null)
        }
    }

    //profile picture
    fun setProfilePicture(picture: String) {
        synchronized(LOCK) {
            sharedPreferences.edit().putString("picture", picture).apply()
        }
    }

    fun getProfilePicture(): String? {
        synchronized(LOCK) {
            return sharedPreferences.getString("picture", null)
        }
    }
}
