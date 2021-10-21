package com.alexandros.e_health.api.responseModel

data class Appointment(
    val _id: String,
    val active: Boolean,
    val date: String,
    val department: DepartmentX,
    val id: String,
    val user: String
)