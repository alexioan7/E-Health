package com.alexandros.e_health.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.ErrorResponse
import com.alexandros.e_health.api.responseModel.HospitalsUserResponse
import com.alexandros.e_health.repositories.AuthRepository

class DiagnosesShareViewModel(private val authRepo: AuthRepository): ViewModel() {


    var authListenerdiag: AuthFunctionsShareDiagnoses? = null
    var authHospitalListenerpresc: AuthFunctionsHospitalByPresc? = null

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


    fun getStatusFromShareDiagnoses(): ErrorResponse?{
        return authRepo.statusFromShareDiagnoses
    }

    fun getHospitalsByDiag() : MutableLiveData<HospitalsUserResponse> {
        return authRepo.hospitalsByDiagFromRemoteData
    }

    fun requestHospitalsBySharedDiagnoses(diagnosisID: String){
        authRepo.requestHospitalsByDiag(diagnosisID,fun(){
            if(getStatusFromShareDiagnoses() !=null){
                Log.d("ON FAILED","Share prescription failed")
                authRepo.statusFromHospitalByDiag?.let {
                    authListenerdiag?.onFailureDiagnosesShare(
                        it.message)
                }

                //Toast.makeText(, "Something went wrong",Toast.LENGTH_LONG).show()

            }else{
                authHospitalListenerpresc?.onSuccessHospitalsByPresc(authRepo.hospitalsByDiagFromRemoteData)
                Log.d("On Success","Share prescription succeed")


            }

        })

    }


    fun shareDiagnoses(hospitalName: String,hospitalID: String, diagnosisID: String){
        authRepo.requestShareDiagnoses(hospitalID,diagnosisID,fun(){
            if(getStatusFromShareDiagnoses()!= null){
                Log.d("ON FAILED","Share diagnoses failed")

                authRepo.statusFromShareDiagnoses?.let {
                    authListenerdiag?.onFailureDiagnosesShare(
                        it.message)
                }

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