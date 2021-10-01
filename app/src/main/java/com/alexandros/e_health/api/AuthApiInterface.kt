package com.alexandros.e_health.api


import com.alexandros.e_health.api.responseModel.RegisterBody
import com.alexandros.e_health.api.responseModel.RegisterUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiInterface {


    @POST("users/signup")
    fun registerUser(@Body body: RegisterBody
    ): Call<RegisterUserResponse>
}