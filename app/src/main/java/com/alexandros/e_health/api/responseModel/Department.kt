package com.alexandros.e_health.api.responseModel

data class Department(
    val __v: Int,
    val _id: String,
    val hospital: Hospital,
    val name: String
){
    override fun toString(): String {
        return name
    }
}