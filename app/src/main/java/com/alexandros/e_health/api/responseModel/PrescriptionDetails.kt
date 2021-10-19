package com.alexandros.e_health.api.responseModel

data class PrescriptionDetails (
    val dispensed: Boolean,
    val createdAt: String,
    val _id:String,
    val hospital: Hospital,
    val user: String,
    val doctor: Doctor,
    val medicine: String,
    val description: String,
    val active: Boolean
)