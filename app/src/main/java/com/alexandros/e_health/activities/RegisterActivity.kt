package com.alexandros.e_health.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexandros.e_health.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tvLogin.setOnClickListener(){
            this.finish()
        }
    }
}