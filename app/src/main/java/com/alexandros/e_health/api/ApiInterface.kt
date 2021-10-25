package com.alexandros.e_health.api


import com.alexandros.e_health.api.responseModel.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {


    @POST("users/signup")
    fun registerUser(
        @Body body: RegisterBody
    ): Call<RegisterUserResponse>

    @POST("users/login")
    fun loginUser(
        @Body body: LoginBody
    ): Call<LoginUserResponse>


    @GET("users/myProfile")
    fun getUserInfo(): Call<MyProfileResponse>

    @GET("prescriptions")
    fun getUserPrescriptions(): Call<PrescriptionsUserResponse>

    @GET("hospitals")
    fun getShareHospitals(): Call<HospitalsUserResponse>

    @POST("sharePrescriptions")
    fun sharePrescriptions(
        @Body body: PrescriptionsShareBody
    ): Call<PrescriptionsShareResponse>

    @GET("diagnoses")
    fun getUserDiagnoses(): Call<DiagnosesUserResponse>

    @GET("diagnoses")
    fun getUserDiagnosesFromDate(@Query("createdAt[gt]") date:String):  Call<DiagnosesUserResponse>

    @POST("shareDiagnoses")
    fun shareDiagnoses(
        @Body body:DiagnosesShareBody
    ): Call<DiagnosesShareResponse>

    @GET("appointments")
    fun getUserAppointments(): Call<UserApointmentsResponse>

    @GET("departments")
    fun getHospitalDepartments(@Query("hospital") id: String): Call<HospitalDepartmentsResponse>






}