package com.alexandros.e_health.api


import com.alexandros.e_health.utils.HeaderInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import okhttp3.OkHttpClient


class RetrofitBuilder {
    companion object {
        private const val BASE_URL = "http://52.174.109.99:8000/api/v1/"
    }





    fun getRetrofit():ApiInterface{

        val interceptor = HttpLoggingInterceptor()
        val headerInterceptor = HeaderInterceptor()

        interceptor.level =HttpLoggingInterceptor.Level.BODY


        val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(headerInterceptor).build()





        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(ApiInterface::class.java)
    }
}