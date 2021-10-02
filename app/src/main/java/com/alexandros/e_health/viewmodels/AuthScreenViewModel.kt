package com.alexandros.e_health.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.repositories.AuthRepository


class AuthScreenViewModel(private val authRepo: AuthRepository) : ViewModel() {

    //variables
    var id: String? = null
    var password: String? = null

    //register variables
    var email: String? = null
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var passwordConfirm: String? = null

    var authListener: AuthFunctions? = null

     fun registerUser(
         id: String,
         password: String,
         confirmPassword: String,
         firstName: String,
         lastName: String,
         email: String,
         phoneNumber: String
    ) {
        var repoId = id
        var repoPassword = password
        var repoConfirmPassword = confirmPassword
        var repoFirstName = firstName
        var repoLastname = lastName
        var repoEmail = email
        var repoPhoneNumber = phoneNumber
        authRepo.registerUser(
            repoId,
            repoPassword,
            repoConfirmPassword,
            repoFirstName,
            repoLastname,
            repoEmail,
            repoPhoneNumber
        )
    }

    fun onLoginButtonClick(view: View) {
        authListener?.OnStarted()

        if (id.isNullOrEmpty() || password.isNullOrEmpty()) {

            //returns error to ui
            authListener?.OnFailure("Invalid Id or password")
            return

        }
        //success;authentication from backend
        authListener?.OnSuccess()


    }

    fun onRegisterButtonClick(view: View) {
        authListener?.OnStarted()
        authListener?.OnSuccess()
        authListener?.OnFailure("Wrong")
        registerUser(id.toString(), password.toString(), passwordConfirm.toString(), firstName.toString(), lastName.toString(), email.toString(), phoneNumber.toString())


    }


}