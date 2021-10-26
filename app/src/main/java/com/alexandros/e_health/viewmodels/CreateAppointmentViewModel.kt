package com.alexandros.e_health.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.CreateAppointmentResponse
import com.alexandros.e_health.api.responseModel.HospitalDepartmentsResponse
import com.alexandros.e_health.api.responseModel.HospitalsUserResponse
import com.alexandros.e_health.api.responseModel.TimeslotsResponse
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.utils.SingleLiveEvent

class CreateAppointmentViewModel(private val authRepo: AuthRepository) : ViewModel(){


    private val _hospitals = MutableLiveData<HospitalsUserResponse>()
    val hospitals: LiveData<HospitalsUserResponse>
        get()=_hospitals

    //these functions are used to get the hospitals in get request

    fun requestHospitals(){
        authRepo.requestHospitals()
    }

    fun getHospitalsFromRepo(): MutableLiveData<HospitalsUserResponse> {
        return authRepo.hospitalsFromRemoteData
    }

    fun requestTimeslots(id: String, date: String){
        authRepo.requestTimeslots(id, date)
    }

    fun getTimeSlots(): MutableLiveData<TimeslotsResponse> {
        return authRepo.timeslotsFromRemoteData
    }

    fun requestHospitalDepartments(id: String){
        authRepo.requestHospitalDepartments(id)
    }

    fun getHospitalDepartmentsFromRepo(): MutableLiveData<HospitalDepartmentsResponse>{
        return authRepo.hospitalDepartmentFromRemoteData
    }

    fun requestToCreateAppointment(date: String, department: String, timeslot: String){
        authRepo.requestToCreateAppointmentFromRemoteData(date, department, timeslot)
    }

    fun getResponseDataFromCreateAppointment(): SingleLiveEvent<CreateAppointmentResponse> {
        return authRepo.appointmentData
    }

    fun getFailureMessageFromCreateAppointment(): SingleLiveEvent<String> {
        return authRepo.failureMessageFromCreateAppointment
    }

}