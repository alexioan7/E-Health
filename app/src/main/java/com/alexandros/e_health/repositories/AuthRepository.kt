package com.alexandros.e_health.repositories

import com.alexandros.e_health.api.AuthApiInterface

class AuthRepository(
    private val api: AuthApiInterface
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