package com.alexandros.e_health.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.HospitalsUserResponse
import com.alexandros.e_health.repositories.AuthRepository

class PrescriptionsShareViewModel(private val authRepo: AuthRepository): ViewModel() {


    var authListener: AuthFunctionsSharePrescriptions? = null

    private val _hospitals=MutableLiveData<HospitalsUserResponse>()
    val hospitals: LiveData<HospitalsUserResponse>
        get()=_hospitals

    //these functions are used to get the hospitals in get request

    fun requestHospitals(){
        authRepo.requestHospitals()
    }

    fun getHospitalsFromRepo(): MutableLiveData<HospitalsUserResponse>{
        return authRepo.hospitalsFromRemoteData
    }

    //these funcions are used to share the prescriptions in post request and receive the status

//    fun requestSharePrescriptions(){
//        authRepo.requestSharePrescriptions()
//    }

    fun getStatusFromSharePrescriptions(): String{
        return authRepo.statusFromSharePrescriptions
    }

    fun sharePrescriptions(hospitalName: String,hospitalID: String, prescriptionsID: String){
        authRepo.requestSharePrescriptions(hospitalID,prescriptionsID,fun(){
            if(getStatusFromSharePrescriptions()=="fail"){
                Log.d("ON FAILED","Share prescription failed")
                authListener?.OnFailurePrescriptionShare()

                //Toast.makeText(, "Something went wrong",Toast.LENGTH_LONG).show()

            }else{
                authListener?.OnSuccessPrescriptionShare(hospitalName)
                Log.d("On Success","Share prescription succeed")
            }

        })

    }

    fun onShareButtonClick(hospital_name:String,hospital_id: String,prescription_id:String){

        sharePrescriptions(hospital_name,hospital_id,prescription_id)
    }


}