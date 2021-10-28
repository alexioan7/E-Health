package com.alexandros.e_health.utils

import android.widget.Toast
import androidx.fragment.app.FragmentActivity

//extension function named toast to display toasts



fun toast(message:String, context: FragmentActivity?){
    Toast.makeText(context,message, Toast.LENGTH_LONG).show()

}