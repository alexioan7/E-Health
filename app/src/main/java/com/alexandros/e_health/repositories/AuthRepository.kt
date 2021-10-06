package com.alexandros.e_health.repositories

import android.util.Log
import com.alexandros.e_health.api.RetrofitBuilder
import com.alexandros.e_health.api.responseModel.*
import com.alexandros.e_health.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson;



object AuthRepository {
    private const val TAG = "AuthRepository"
    val gson = Gson()

    val userDataFromRegister: SingleLiveEvent<RegisterUserResponse> = SingleLiveEvent()
    val failureMessageFromRegister: SingleLiveEvent<String> = SingleLiveEvent()


     fun getDataFromRegisteredUser(): SingleLiveEvent<RegisterUserResponse> {
        return userDataFromRegister
    }



    fun requestToRegister(
        amka: String,
        password: String,
        confirmPassword: String,
        name: String,
        surname: String,
        email: String,
        phoneNumber: String
    ) {
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

                    }else{
                        val responseObj:ErrorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                        failureMessageFromRegister.postValue(responseObj.message)
                    }
                }
                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                }
            })

    }

    fun requestToLogin(
        amka: String,
        password: String
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

                    }
                }

                override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                }


            })


    }
}



