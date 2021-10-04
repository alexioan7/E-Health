package com.alexandros.e_health.api.responseModel

data class LoginUserResponse (
    val data: Data,
    val status: String,
    val token: String
)