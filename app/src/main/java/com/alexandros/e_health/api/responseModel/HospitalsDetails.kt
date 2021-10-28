package com.alexandros.e_health.api.responseModel

data class HospitalsDetails(
    val _id: String,
    val name: String,
    val prefecture: String


) {
    override fun toString(): String {
        return this.name+", "+this.prefecture
    }

    override fun equals(other: Any?): Boolean {
        val other1 = other as HospitalsDetails
        return this._id == other1._id
    }

    override fun hashCode(): Int {
        return this._id.hashCode()
    }
}
