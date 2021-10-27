package com.alexandros.e_health.api.responseModel

data class HospitalsDetails(
    val _id: String,
    val name: String,
    val prefecture: String


) {
    override fun toString(): String {
        return this.name + ", " + this.prefecture
    }
}
