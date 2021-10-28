package com.alexandros.e_health.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.Appointment
import com.alexandros.e_health.api.responseModel.Doctor
import com.alexandros.e_health.api.responseModel.Hospital
import com.alexandros.e_health.api.responseModel.UserApointmentsResponse
import com.alexandros.e_health.repositories.AuthRepository

class AppointmentsViewModel(private val authRepo: AuthRepository): ViewModel() {
    val createdAt: String? = null
    val hospital: Hospital? = null
    val doctor: Doctor? = null
    val description: String? = null

    private val _appointments = MutableLiveData<UserApointmentsResponse>()
    val appointments: LiveData<UserApointmentsResponse>
        get() = _appointments


    fun requestUserAppointments() {
        authRepo.requestAppointments()

    }


    fun getUserAppointmentsFromRepo(): MutableLiveData<UserApointmentsResponse> {
        return authRepo.userAppointmentsFromRemoteData

    }

}