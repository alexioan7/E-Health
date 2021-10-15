package com.alexandros.e_health.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alexandros.e_health.api.RetrofitBuilder
import com.alexandros.e_health.api.responseModel.MyProfileResponse
import com.alexandros.e_health.api.responseModel.RegisterUserResponse
import com.alexandros.e_health.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserInfoRepository {
    private const val TAG = "UserInfoRepository"

    val userInfoFromRemoteData: MutableLiveData<MyProfileResponse> = MutableLiveData()


    fun requestUserInfo() {
        val dataSource = RetrofitBuilder()
        Log.i(UserInfoRepository.TAG, "My Profile response: Call is started")
        dataSource.getRetrofit()
            .getUserInfo()
            .enqueue(object : Callback<MyProfileResponse> {
                override fun onResponse(
                    call: Call<MyProfileResponse>,
                    response: Response<MyProfileResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(UserInfoRepository.TAG, "onResponse: Response Successful")
                        userInfoFromRemoteData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MyProfileResponse>, t: Throwable) {
                    Log.i(UserInfoRepository.TAG, "onFailure: " + t.message)
                }
            })

    }

}
