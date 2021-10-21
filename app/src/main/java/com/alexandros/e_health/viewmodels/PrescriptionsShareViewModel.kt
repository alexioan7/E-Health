package com.alexandros.e_health.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.HospitalsUserResponse
import com.alexandros.e_health.repositories.AuthRepository

class PrescriptionsShareViewModel(private val authRepo: AuthRepository): ViewModel() {

    private val _hospitals=MutableLiveData<HospitalsUserResponse>()
    val hospitals: LiveData<HospitalsUserResponse>
        get()=_hospitals

    fun requestSharePrescriptions(){
        authRepo.requestHospitals()
    }

    fun getHospitalsFromRepo(): MutableLiveData<HospitalsUserResponse>{
        return authRepo.hospitalsFromRemoteData
    }
}