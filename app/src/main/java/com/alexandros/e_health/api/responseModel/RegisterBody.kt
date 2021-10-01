package com.alexandros.e_health.api.responseModel

data class RegisterBody(
  val amka: String,
  val password: String,
  val name: String,
  val surname: String,
  val email: String,
  val phoneNumber: Int
)
