package com.alexandros.e_health.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonalinfoViewModel : ViewModel() {

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
    var familyDoctor: String?=null
}