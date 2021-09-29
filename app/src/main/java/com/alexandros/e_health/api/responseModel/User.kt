package com.alexandros.e_health.api.responseModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  User(
    val __v: Int,
    val _id: String,
    val amka: String,
    val bloodtype: String,
    val email: String,
    val familyDoctor: String,
    val name: String,
    val password: String,
    val phoneNumber: Int,
    val surname: String
) : Parcelable