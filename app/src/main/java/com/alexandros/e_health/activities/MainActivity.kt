package com.alexandros.e_health.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexandros.e_health.R
import com.alexandros.e_health.utils.SharedPreferencesUtil


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferencesUtil.init(applicationContext)


    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPreferencesUtil.clearPreferences()

    }

}