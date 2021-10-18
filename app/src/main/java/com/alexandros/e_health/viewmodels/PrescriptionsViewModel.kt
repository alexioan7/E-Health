package com.alexandros.e_health.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.Doctor
import com.alexandros.e_health.api.responseModel.Hospital
import com.alexandros.e_health.api.responseModel.PrescriptionsUserResponse
import com.alexandros.e_health.repositories.AuthRepository

class PrescriptionsViewModel (private val authRepo: AuthRepository): ViewModel() {

    val medicine: String? = null
    val dispensed: Boolean = false
    val createdAt: String? = null
    val hospital: Hospital? = null
    val doctor: Doctor? = null
    val description: String? = null
    val active: Boolean = false

    fun getUserPrescriptionsFromRepo(): MutableLiveData<List<PrescriptionsUserResponse>> {
        return authRepo.userPrescriptionsFromRemoteData
    }

    fun requestUserPrescriptions(){
        authRepo.requestPrescriptions()
    }





}