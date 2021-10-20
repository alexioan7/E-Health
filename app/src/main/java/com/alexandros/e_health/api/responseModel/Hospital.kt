package com.alexandros.e_health.api.responseModel

data class Hospital(
    val department: List<String>?,
    val id: String,
    val name: String,
    val prefecture: String,
    val _v: Int
)
