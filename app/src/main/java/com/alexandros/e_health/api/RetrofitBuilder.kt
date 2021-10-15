package com.alexandros.e_health.api

import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        private const val BASE_URL = "http://52.174.109.99:8000/api/v1/"
    }
//    private lateinit var authToken: String


    fun getRetrofit():ApiInterface{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level =HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(ApiInterface::class.java)
    }
}