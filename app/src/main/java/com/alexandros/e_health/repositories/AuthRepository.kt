package com.alexandros.e_health.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alexandros.e_health.api.RetrofitBuilder
import com.alexandros.e_health.api.responseModel.*
import com.alexandros.e_health.utils.SingleLiveEvent
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthRepository {
    private const val TAG = "AuthRepository"
    val gson = Gson()

    val userDataFromRegister: SingleLiveEvent<RegisterUserResponse> = SingleLiveEvent()
    val failureMessageFromRegister: SingleLiveEvent<String> = SingleLiveEvent()

    val userDataFromLogin: SingleLiveEvent<LoginUserResponse> = SingleLiveEvent()
    var statusFromLogin: String = ""
    var statusFromSharePrescriptions: String = " "
    var statusFromShareDiagnoses: String=" "

    val userInfoFromRemoteData: MutableLiveData<MyProfileResponse> = MutableLiveData()
    val userPrescriptionsFromRemoteData: MutableLiveData<PrescriptionsUserResponse> = MutableLiveData()
    val userDiagnosesFromRemoteData: MutableLiveData<DiagnosesUserResponse> = MutableLiveData()
    val userAppointmentsFromRemoteData: MutableLiveData<UserApointmentsResponse> = MutableLiveData()
    val hospitalsFromRemoteData: MutableLiveData<HospitalsUserResponse> =MutableLiveData()
    val prescriptionsShareResponse: SingleLiveEvent<PrescriptionsShareResponse> = SingleLiveEvent()
    val diagnosisShareResponse: SingleLiveEvent<DiagnosesShareResponse> = SingleLiveEvent()

    fun getDataFromRegisteredUser(): SingleLiveEvent<RegisterUserResponse> {
        return userDataFromRegister
    }

    fun getDataFromLoginUser(): SingleLiveEvent<LoginUserResponse> {
        return userDataFromLogin
    }

    fun requestToRegister(amka: String, password: String, confirmPassword: String, name: String, surname: String, email: String, phoneNumber: String) {
        val dataSource = RetrofitBuilder()

        Log.i(TAG, "registerUser: Call is started")
        val body = RegisterBody(amka, password, confirmPassword, name, surname, email, phoneNumber)
        dataSource.getRetrofit()
            .registerUser(body)
            .enqueue(object : Callback<RegisterUserResponse> {
                override fun onResponse(
                    call: Call<RegisterUserResponse>,
                    response: Response<RegisterUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "onResponse: Response Successful")
                        userDataFromRegister.postValue(response.body())

                    } else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            failureMessageFromRegister.postValue(responseObj.message)
                        } catch (e: Exception) {
                            failureMessageFromRegister.postValue("Something Went Wrong")
                        }

                    }
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                }
            })

    }

    fun requestToLogin(
        amka: String,
        password: String,
        callback: () ->Unit
    ) {
        val dataSource = RetrofitBuilder()

        Log.i(TAG, "Login user: Call started")
        val body = LoginBody(amka, password)
        dataSource.getRetrofit()
            .loginUser(body)
            .enqueue(object : Callback<LoginUserResponse> {
                override fun onResponse(
                    call: Call<LoginUserResponse>,
                    response: Response<LoginUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "onResponse: Response Successful")
                        userDataFromLogin.postValue(response.body())
                        statusFromLogin=""
                        callback()

                    } else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            statusFromLogin = responseObj.status
                            Log.d("FAILURE MESSAGE", statusFromLogin.toString())
                            callback()
                        } catch (e: Exception) {
                            statusFromLogin = "Something Went Wrong"
                            Log.d("IN CATCH", statusFromLogin.toString())
                            callback()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    callback()
                }
            })


    }


    fun requestUserInfo() {
        val dataSource = RetrofitBuilder()
        Log.i(TAG, "My Profile response: Call is started")
        dataSource.getRetrofit()
            .getUserInfo()
            .enqueue(object : Callback<MyProfileResponse> {
                override fun onResponse(
                    call: Call<MyProfileResponse>,
                    response: Response<MyProfileResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "onResponse: Response Successful")
                        userInfoFromRemoteData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MyProfileResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                }
            })

    }

    fun requestPrescriptions() {
        val dataSource = RetrofitBuilder()
        Log.i(AuthRepository.TAG, "Prescriptions response: Call is started")
        dataSource.getRetrofit()
            .getUserPrescriptions()
            .enqueue(object : Callback<PrescriptionsUserResponse> {
                override fun onResponse(
                    call: Call<PrescriptionsUserResponse>,
                    response: Response<PrescriptionsUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        userPrescriptionsFromRemoteData.postValue(response.body())

                    }
                }

                override fun onFailure(call: Call<PrescriptionsUserResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)

                }
            })

    }

    fun requestHospitals() {
        val dataSource = RetrofitBuilder()
        Log.i(AuthRepository.TAG, "get Hospitals response: Call is started")
        dataSource.getRetrofit()
            .getShareHospitals()
            .enqueue(object : Callback<HospitalsUserResponse> {
                override fun onResponse(
                    call: Call<HospitalsUserResponse>,
                    response: Response<HospitalsUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                       hospitalsFromRemoteData.postValue(response.body())

                    }
                }

                override fun onFailure(call: Call<HospitalsUserResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)

                }
            })

    }

    fun requestSharePrescriptions(hospital: String, prescription: String, callback: () -> Unit){
        val dataSource = RetrofitBuilder()

        Log.i(TAG, "Share prescriptions: Call started")
        val body = PrescriptionsShareBody(hospital,prescription)
        dataSource.getRetrofit()
            .sharePrescriptions(body)
            .enqueue(object : Callback<PrescriptionsShareResponse> {
                override fun onResponse(
                    call: Call<PrescriptionsShareResponse>,
                    response: Response<PrescriptionsShareResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "onResponse: Response Successful")
                        prescriptionsShareResponse.postValue(response.body())
                        statusFromSharePrescriptions=""
                        callback()

                    } else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            statusFromSharePrescriptions = responseObj.status
                            Log.d("FAILURE MESSAGE", statusFromSharePrescriptions.toString())
                            callback()
                        } catch (e: Exception) {
                            statusFromSharePrescriptions = "Something Went Wrong"
                            Log.d("IN CATCH", statusFromSharePrescriptions.toString())
                            callback()
                        }
                    }
                }

                override fun onFailure(call: Call<PrescriptionsShareResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    callback()
                }
            })


    }





    fun requestDiagnoses() {
        val dataSource = RetrofitBuilder()
        Log.i(TAG, "Diagnoses response: Call is started")
        dataSource.getRetrofit()
            .getUserDiagnoses()
            .enqueue(object : Callback<DiagnosesUserResponse> {
                override fun onResponse(
                    call: Call<DiagnosesUserResponse>,
                    response: Response<DiagnosesUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        userDiagnosesFromRemoteData.postValue(response.body())

                    }
                }

                override fun onFailure(call: Call<DiagnosesUserResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)
                }

            })

    }

    fun requestShareDiagnoses(hospital: String, diagnosis: String, callback: () -> Unit){
        val dataSource = RetrofitBuilder()

        Log.i(TAG, "Share diagnoses: Call started")
        val body = DiagnosesShareBody(hospital,diagnosis)
        dataSource.getRetrofit()
            .shareDiagnoses(body)
            .enqueue(object : Callback<DiagnosesShareResponse> {
                override fun onResponse(
                    call: Call<DiagnosesShareResponse>,
                    response: Response<DiagnosesShareResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "onResponse: Response Successful")
                        diagnosisShareResponse.postValue(response.body())
                        statusFromShareDiagnoses=""
                        callback()

                    } else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            statusFromShareDiagnoses= responseObj.status
                            Log.d("FAILURE MESSAGE", statusFromShareDiagnoses.toString())
                            callback()
                        } catch (e: Exception) {
                            statusFromShareDiagnoses= "Something Went Wrong"
                            Log.d("IN CATCH", statusFromShareDiagnoses.toString())
                            callback()
                        }
                    }
                }

                override fun onFailure(call: Call<DiagnosesShareResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    callback()
                }
            })


    }

    fun requestAppointments() {
        val dataSource = RetrofitBuilder()
        Log.i(AuthRepository.TAG, "Appointments response: Call is started")
        dataSource.getRetrofit()
            .getUserAppointments()
            .enqueue(object : Callback<UserApointmentsResponse> {
                override fun onResponse(
                    call: Call<UserApointmentsResponse>,
                    response: Response<UserApointmentsResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        userAppointmentsFromRemoteData.postValue(response.body())

                    }
                }

                override fun onFailure(call: Call<UserApointmentsResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)

                }
            })

    }

}




