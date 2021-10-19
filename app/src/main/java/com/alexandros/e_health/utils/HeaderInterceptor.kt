package com.alexandros.e_health.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor() : Interceptor{

lateinit var response:Response

    override fun intercept(chain: Interceptor.Chain): Response {

          val  request= chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${SharedPreferencesUtil.getAccessToken()}")
                .build()

        Log.i("TOKEN", "intercept: ${SharedPreferencesUtil.getAccessToken()}")
        response = chain.proceed(request)
        Log.i("info ", "intercept: ${response.code}")

        return response
    }





}