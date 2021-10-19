package com.alexandros.e_health.api


import com.alexandros.e_health.api.responseModel.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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

}