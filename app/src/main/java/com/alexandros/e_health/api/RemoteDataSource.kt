package com.alexandros.e_health.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object {
        private const val BASE_URL = "http://192.168.1.7:8000/api/v1/"
    }

    fun getRetrofit():AuthApiInterface{

       val interceptor = HttpLoggingInterceptor()
        interceptor.level =HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(AuthApiInterface::class.java)
    }
}