package com.alexandros.e_health.viewmodels

import androidx.lifecycle.MutableLiveData
import com.alexandros.e_health.api.responseModel.HospitalsUserResponse

interface AuthFunctionsHospitalByPresc {
    fun onSuccessHospitalsByPresc(responseList: MutableLiveData<HospitalsUserResponse>)
}