package com.alexandros.e_health.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object{
        private const val BASE_URL = "http://localhost:8000/api/v1/"
    }


    fun <Api> buildApi(
        api: Class<Api>
    ): Api{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}