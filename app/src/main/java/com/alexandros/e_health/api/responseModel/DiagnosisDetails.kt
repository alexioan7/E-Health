package com.alexandros.e_health.api.responseModel

data class DiagnosisDetails(
    val createdAt: String,
    val _id: String,
    val department: Department,
    val user: String,
    val doctor: Doctor,
    val description: String
)