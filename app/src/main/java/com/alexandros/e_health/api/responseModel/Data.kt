package com.alexandros.e_health.api.responseModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val user: User
) : Parcelable