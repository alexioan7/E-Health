package com.alexandros.e_health.api.responseModel

data class PrescriptionDetails (
    val dispensed: Boolean,
    val createdAt: String,
    val _id: String,
    val department: Department,
    val user: String,
    val doctor: Doctor,
    val medicine: String,
    val description: String,
    val _v: Int,
    val active: Boolean,
    val id: String
)