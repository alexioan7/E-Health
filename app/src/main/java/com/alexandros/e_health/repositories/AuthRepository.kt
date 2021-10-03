package com.alexandros.e_health.repositories

import android.util.Log
import com.alexandros.e_health.api.RemoteDataSource
import com.alexandros.e_health.api.responseModel.LoginBody
import com.alexandros.e_health.api.responseModel.LoginUserResponse
import com.alexandros.e_health.api.responseModel.RegisterBody
import com.alexandros.e_health.api.responseModel.RegisterUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthRepository {
    private const val TAG = "AuthRepository"


    fun registerUser(
        amka: String,
        password: String,
        confirmPassword: String,
        name: String,
        surname: String,
        email: String,
        phoneNumber: String
    ) {
        val dataSource = RemoteDataSource()

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
                    }
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                }
            })

    }

    fun loginUser(
        amka: String,
        password: String
    ) {
        val dataSource = RemoteDataSource()

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



