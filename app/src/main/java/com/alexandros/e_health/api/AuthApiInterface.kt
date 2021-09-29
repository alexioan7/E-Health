package com.alexandros.e_health.api

import com.alexandros.e_health.api.responseModel.RegisterUserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiInterface {



    @FormUrlEncoded
    @POST("users")
    suspend fun registerUser(
        @Field("amka") amka: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("surname") surname: String,
        @Field("email") email: String,
        @Field("phone number") phoneNumber: Number
    ) : RegisterUserResponse
}