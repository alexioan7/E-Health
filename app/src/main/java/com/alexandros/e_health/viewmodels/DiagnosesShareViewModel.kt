package com.alexandros.e_health.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.HospitalsUserResponse
import com.alexandros.e_health.repositories.AuthRepository

class DiagnosesShareViewModel(private val authRepo: AuthRepository): ViewModel() {


    var authListenerdiag: AuthFunctionsShareDiagnoses? = null

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

    fun getStatusFromShareDiagnoses(): String{
        return authRepo.statusFromShareDiagnoses
    }

    fun shareDiagnoses(hospitalName: String,hospitalID: String, diagnosisID: String){
        authRepo.requestShareDiagnoses(hospitalID,diagnosisID,fun(){
            if(getStatusFromShareDiagnoses()=="fail"){
                Log.d("ON FAILED","Share diagnoses failed")
                authListenerdiag?.onFailureDiagnosesShare(authRepo.failureMessageFromShareDiagnoses.toString())

                //Toast.makeText(, "Something went wrong",Toast.LENGTH_LONG).show()

            }else{
                authListenerdiag?.onSuccessDiagnosesShare(hospitalName)
                Log.d("On Success","Share diagnoses succeed")
            }

        })

    }

    fun onShareButtonClick(hospital_name:String,hospital_id: String,diagnoses_id:String){

        shareDiagnoses(hospital_name,hospital_id,diagnoses_id)
    }


}