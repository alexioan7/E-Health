package com.alexandros.e_health.api.responseModel

data class User(
    val __v: Int,
    val _id: String,
    val amka: String,
    val bloodtype: String,
    val email: String,
    val familyDoctor: FamilyDoctor,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val surname: String

)