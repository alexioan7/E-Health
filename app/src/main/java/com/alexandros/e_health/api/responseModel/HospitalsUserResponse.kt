package com.alexandros.e_health.api.responseModel

data class HospitalsUserResponse(
    val status: String,
    val results: Int,
    val hospitals: List<Hospitalx>
)
