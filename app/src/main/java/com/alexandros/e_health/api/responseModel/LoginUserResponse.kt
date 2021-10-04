package com.alexandros.e_health.api.responseModel

data class LoginUserResponse (
    val data: Data,
    val ststus: String,
    val token: String
)