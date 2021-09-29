package com.alexandros.e_health.repositories

import android.provider.ContactsContract
import com.alexandros.e_health.api.AuthApi

class AuthRepository(
    private val api: AuthApi
) : BaseRepository() {

    suspend fun registerUser(
        amka: String,
        password: String,
        name: String,
        surname: String,
        email: String,
        phoneNumber: Number
    ) = safeApiCall {
        api.registerUser(amka, password, name, surname, email, phoneNumber)
    }
}