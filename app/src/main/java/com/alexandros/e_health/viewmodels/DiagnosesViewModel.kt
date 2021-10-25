package com.alexandros.e_health.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.*
import com.alexandros.e_health.repositories.AuthRepository

class DiagnosesViewModel(private val authRepo: AuthRepository) : ViewModel() {

    val createdAt: String? = null
    val hospital: Hospital? = null
    val doctor: Doctor? = null
    val description: String? = null

    private val _diagnoses = MutableLiveData<DiagnosesUserResponse>()
    val diagnoses: LiveData<DiagnosesUserResponse>
        get() = _diagnoses


    fun requestUserDiagnoses() {
        authRepo.requestDiagnoses()

    }

    fun requestUserDiagnosesFromDate(date:String){
        authRepo.requestDiagnosesFromDate(date)
    }


    fun getUserDiagnosesFromRepo(): MutableLiveData<DiagnosesUserResponse> {
        return authRepo.userDiagnosesFromRemoteData

    }
}