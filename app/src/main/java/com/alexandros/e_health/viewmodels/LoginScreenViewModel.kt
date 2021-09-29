package com.alexandros.e_health.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel

class LoginScreenViewModel: ViewModel(){

    var id: String?=null
    var password: String?=null
    var authListener:LoginFunctions?=null

    fun onLoginButtonClick(view: View){
        authListener?.OnStarted()

        if(id.isNullOrEmpty() || password.isNullOrEmpty()){

            //returns error to ui
            authListener?.OnFailure("Invalid Id or password")
            return


        }
        //success;authentication from backend
        authListener?.OnSuccess()


    }


}