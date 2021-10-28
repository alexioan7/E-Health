package com.alexandros.e_health.api.responseModel

data class CreateAppointmentBody(
    val date: String,
    val timeslot: String,
    val department: String
)
