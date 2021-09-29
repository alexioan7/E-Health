package com.alexandros.e_health.api.responseModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterUserResponse(
    val data: Data,
    val status: String
) : Parcelable