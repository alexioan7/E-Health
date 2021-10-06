package com.alexandros.e_health.activities

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexandros.e_health.R

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = this.getSharedPreferences(this.packageName, Activity.MODE_PRIVATE)

    }

    override fun onDestroy() {
        super.onDestroy()
        clearPreferences()

    }
    private fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}