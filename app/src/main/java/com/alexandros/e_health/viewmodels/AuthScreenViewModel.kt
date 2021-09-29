package com.alexandros.e_health.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel

class AuthScreenViewModel: ViewModel(){

    //variables
    var id: String? = null
    var password: String? = null

    //register variables
    var email:String? = null
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var passwordConfirm:String? = null






    var authListener:AuthFunctions?=null

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

    fun onRegisterButtonClick(view: View){
        authListener?.OnStarted()



    }


}