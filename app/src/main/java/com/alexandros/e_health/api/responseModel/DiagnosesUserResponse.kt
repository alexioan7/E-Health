package com.alexandros.e_health.api.responseModel

data class DiagnosesUserResponse(
    val status: String,
    val results: Int,
    val data: DataDiagnoses
)