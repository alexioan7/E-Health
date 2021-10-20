package com.alexandros.e_health.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiagnosesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is diagnoses Fragment"
    }
    val text: LiveData<String> = _text
}