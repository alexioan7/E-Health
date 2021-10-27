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
    var statusFromSharePrescriptions: ErrorResponse? = null
    var statusFromHospitalByPresc: ErrorResponse? = null
    var statusFromHospitalByDiag: ErrorResponse? = null
    var statusFromShareDiagnoses: ErrorResponse? = null

    val userInfoFromRemoteData: MutableLiveData<MyProfileResponse> = MutableLiveData()
    val userPrescriptionsFromRemoteData: MutableLiveData<PrescriptionsUserResponse> = MutableLiveData()
    val failureMessageFromPrescriptions: SingleLiveEvent<String> = SingleLiveEvent()
    val userDiagnosesFromRemoteData: MutableLiveData<DiagnosesUserResponse> = MutableLiveData()
    val failureMessageFromDiagnoses: SingleLiveEvent<String> = SingleLiveEvent()
    val userAppointmentsFromRemoteData: MutableLiveData<UserApointmentsResponse> = MutableLiveData()
    val hospitalsFromRemoteData: MutableLiveData<HospitalsUserResponse> =MutableLiveData()
    val hospitalDepartmentFromRemoteData: MutableLiveData<HospitalDepartmentsResponse> = MutableLiveData()
    val timeslotsFromRemoteData: MutableLiveData<TimeslotsResponse> = MutableLiveData()
    val prescriptionsShareResponse: SingleLiveEvent<PrescriptionsShareResponse> = SingleLiveEvent()
    val diagnosisShareResponse: SingleLiveEvent<DiagnosesShareResponse> = SingleLiveEvent()
    val failureMessageFromSharePrescriptions: SingleLiveEvent<String> = SingleLiveEvent()
    val failureMessageFromShareDiagnoses: SingleLiveEvent<String> = SingleLiveEvent()
    val hospitalsByPrescFromRemoteData: MutableLiveData<HospitalsUserResponse> = MutableLiveData()
    val hospitalsByDiagFromRemoteData: MutableLiveData<HospitalsUserResponse> = MutableLiveData()

    val appointmentData: SingleLiveEvent<CreateAppointmentResponse> = SingleLiveEvent()
    val failureMessageFromCreateAppointment: SingleLiveEvent<String> = SingleLiveEvent()

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

    fun requestPrescriptionsFromDate(date: String) {
        val dataSource = RetrofitBuilder()
        Log.i(TAG, "Prescriptions response: Call is started")
        dataSource.getRetrofit()
            .getUserPrescriptionsFromDate(date)
            .enqueue(object : Callback<PrescriptionsUserResponse> {
                override fun onResponse(
                    call: Call<PrescriptionsUserResponse>,
                    response: Response<PrescriptionsUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        userPrescriptionsFromRemoteData.postValue(response.body())
                    }else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            failureMessageFromPrescriptions.postValue(responseObj.message)
                        } catch (e: Exception) {
                            failureMessageFromPrescriptions.postValue("Something Went Wrong")
                        }

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
                        statusFromSharePrescriptions=null
                        callback()

                    } else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            statusFromSharePrescriptions = responseObj
                            //failureMessageFromSharePrescriptions.postValue(responseObj.message)
                            //Log.d("FAILURE MESSAGE", statusFromSharePrescriptions.toString())
                            callback()
                        } catch (e: Exception) {
                            statusFromSharePrescriptions = ErrorResponse("Error", "Something went wrong")
                            Log.d("IN CATCH", statusFromSharePrescriptions.toString())
                            failureMessageFromSharePrescriptions.postValue("Something Went Wrong")
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

    fun requestHospitalsByPresc(prescId: String, callback: () -> Unit) {
        val dataSource = RetrofitBuilder()
        Log.i(TAG, "Hospitals response: Call is started")
        dataSource.getRetrofit()
            .getHospitalsByPresc(prescId)
            .enqueue(object : Callback<HospitalsUserResponse> {
                override fun onResponse(
                    call: Call<HospitalsUserResponse>,
                    response: Response<HospitalsUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        hospitalsByPrescFromRemoteData.postValue(response.body())
                        callback()
                    }
                }

                override fun onFailure(call: Call<HospitalsUserResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)
                    callback()
                }

            })

    }

    fun requestHospitalsByDiag(diagId: String, callback: () -> Unit) {
        val dataSource = RetrofitBuilder()
        Log.i(TAG, "Hospitals response by diagnosis: Call is started")
        dataSource.getRetrofit()
            .getHospitalsByDiag(diagId)
            .enqueue(object : Callback<HospitalsUserResponse> {
                override fun onResponse(
                    call: Call<HospitalsUserResponse>,
                    response: Response<HospitalsUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        hospitalsByDiagFromRemoteData.postValue(response.body())
                        callback()
                    }
                }

                override fun onFailure(call: Call<HospitalsUserResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)
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

    fun requestDiagnosesFromDate(date: String) {
        val dataSource = RetrofitBuilder()
        Log.i(TAG, "Diagnoses response: Call is started")
        dataSource.getRetrofit()
            .getUserDiagnosesFromDate(date)
            .enqueue(object : Callback<DiagnosesUserResponse> {
                override fun onResponse(
                    call: Call<DiagnosesUserResponse>,
                    response: Response<DiagnosesUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        userDiagnosesFromRemoteData.postValue(response.body())
                    }else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            failureMessageFromDiagnoses.postValue(responseObj.message)
                        } catch (e: Exception) {
                            failureMessageFromDiagnoses.postValue("Something Went Wrong")
                        }

                    }
                }

                override fun onFailure(call: Call<DiagnosesUserResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)
                }

            })

    }

    fun requestShareDiagnoses(hospital: String, diagnosis: String, callback: () -> Unit){
        val dataSource = RetrofitBuilder()

        Log.i(TAG, "Share prescriptions: Call started")
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
                        statusFromShareDiagnoses=null
                        callback()

                    } else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            statusFromShareDiagnoses = responseObj
                            //failureMessageFromSharePrescriptions.postValue(responseObj.message)
                            //Log.d("FAILURE MESSAGE", statusFromSharePrescriptions.toString())
                            callback()
                        } catch (e: Exception) {
                            statusFromShareDiagnoses = ErrorResponse("Error", "Something went wrong")
                            Log.d("IN CATCH", statusFromShareDiagnoses.toString())
                            failureMessageFromShareDiagnoses.postValue("Something Went Wrong")
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

    fun requestHospitalDepartments( id: String){
        val dataSource = RetrofitBuilder()
        Log.i(AuthRepository.TAG, "Appointments response: Call is started")
        dataSource.getRetrofit()
            .getHospitalDepartments(id)
            .enqueue(object : Callback<HospitalDepartmentsResponse> {
                override fun onResponse(
                    call: Call<HospitalDepartmentsResponse>,
                    response: Response<HospitalDepartmentsResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        hospitalDepartmentFromRemoteData.postValue(response.body())

                    }
                }

                override fun onFailure(call: Call<HospitalDepartmentsResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)

                }
            })




    }

    fun requestTimeslots(id:String, date:String) {
        val dataSource = RetrofitBuilder()
        Log.i(AuthRepository.TAG, "get Timeslots response: Call is started")
        dataSource.getRetrofit()
            .getAvailableTimeslots(id, date)
            .enqueue(object : Callback<TimeslotsResponse> {
                override fun onResponse(
                    call: Call<TimeslotsResponse>,
                    response: Response<TimeslotsResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(AuthRepository.TAG, "onResponse: Response Successful")
                        timeslotsFromRemoteData.postValue(response.body())

                    }
                }

                override fun onFailure(call: Call<TimeslotsResponse>, t: Throwable) {
                    Log.i(AuthRepository.TAG, "onFailure: " + t.message)

                }
            })

    }

    fun requestToCreateAppointmentFromRemoteData(date: String, timeslots:String, department: String) {
        val dataSource = RetrofitBuilder()

        Log.i(TAG, "createAppointment: Call is started")
        val body = CreateAppointmentBody(date, timeslots, department)
        dataSource.getRetrofit()
            .creteAppointment(body)
            .enqueue(object : Callback<CreateAppointmentResponse> {
                override fun onResponse(
                    call: Call<CreateAppointmentResponse>,
                    response: Response<CreateAppointmentResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "onResponse: Response Successful")
                        appointmentData.postValue(response.body())

                    } else {
                        try {
                            val responseObj: ErrorResponse = gson.fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            failureMessageFromCreateAppointment.postValue(responseObj.message)
                        } catch (e: Exception) {
                            failureMessageFromCreateAppointment.postValue("Something Went Wrong")
                        }

                    }
                }

                override fun onFailure(call: Call<CreateAppointmentResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                }
            })

    }

}




