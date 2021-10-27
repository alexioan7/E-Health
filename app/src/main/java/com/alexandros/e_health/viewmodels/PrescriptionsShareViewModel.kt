package com.alexandros.e_health.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.api.responseModel.ErrorResponse
import com.alexandros.e_health.api.responseModel.HospitalsUserResponse
import com.alexandros.e_health.repositories.AuthRepository

class PrescriptionsShareViewModel(private val authRepo: AuthRepository): ViewModel() {


    var authListenerpresc: AuthFunctionsSharePrescriptions? = null
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

//    fun requestSharePrescriptions(){
//        authRepo.requestSharePrescriptions()
//    }

    fun getStatusFromSharePrescriptions(): ErrorResponse?{
        return authRepo.statusFromSharePrescriptions
    }

    fun getStatusFromHospitalByPresc(): ErrorResponse?{
        return authRepo.statusFromHospitalByPresc
    }

    fun getHospitalsByPresc() : MutableLiveData<HospitalsUserResponse> {
        return authRepo.hospitalsByPrescFromRemoteData
    }

    fun requestHospitalsBySharedPrescriptions(prescriptionsID: String){
        authRepo.requestHospitalsByPresc(prescriptionsID,fun(){
            if(getStatusFromSharePrescriptions() !=null){
                Log.d("ON FAILED","Share prescription failed")
                authRepo.statusFromHospitalByPresc?.let {
                    authListenerpresc?.onFailurePrescriptionShare(
                        it.message)
                }

                //Toast.makeText(, "Something went wrong",Toast.LENGTH_LONG).show()

            }else{
                authHospitalListenerpresc?.onSuccessHospitalsByPresc(authRepo.hospitalsByPrescFromRemoteData)
                Log.d("On Success","Share prescription succeed")


            }

        })

    }

    fun sharePrescriptions(hospitalName: String,hospitalID: String, prescriptionsID: String){
        authRepo.requestSharePrescriptions(hospitalID,prescriptionsID,fun(){
            if(getStatusFromSharePrescriptions() !=null){
                Log.d("ON FAILED","Share prescription failed")
                authRepo.statusFromSharePrescriptions?.let {
                    authListenerpresc?.onFailurePrescriptionShare(
                        it.message)
                }

                //Toast.makeText(, "Something went wrong",Toast.LENGTH_LONG).show()

            }else{
                authListenerpresc?.onSuccessPrescriptionShare(hospitalName)
                Log.d("On Success","Share prescription succeed")


            }

        })

    }

    fun onShareButtonClick(hospital_name:String,hospital_id: String,prescription_id:String){

        sharePrescriptions(hospital_name,hospital_id,prescription_id)
    }




}