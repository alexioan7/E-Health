package com.alexandros.e_health.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.FamilyDoctor
import com.alexandros.e_health.api.responseModel.MyProfileResponse
import com.alexandros.e_health.repositories.AuthRepository

class PersonalinfoViewModel(private val authRepo: AuthRepository) : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    var amka: String? = null
    var firstname: String? = null
    var surname: String? = null
    //register variables
    var email: String? = null
    var phoneNumber: String? = null
    var bloodType: String?=null
    var familyDoctor: FamilyDoctor?=null
    var familyDoctorFullName :String? = null

    fun getUserInfoFromRepo(): MutableLiveData<MyProfileResponse> {
        return authRepo.userInfoFromRemoteData
    }


    fun requestUserInfo(){
        authRepo.requestUserInfo()
    }

}