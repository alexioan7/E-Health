package com.alexandros.e_health.util

import android.content.Context
import android.widget.Toast

//extension function named toast to display toasts

fun Context.toast(message:String){
   Toast.makeText(this, message,Toast.LENGTH_LONG).show()

}