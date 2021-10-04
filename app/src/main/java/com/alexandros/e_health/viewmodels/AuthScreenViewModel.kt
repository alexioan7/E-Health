package com.alexandros.e_health.viewmodels

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.utils.*

//ERROR CODES
//910 -> HealthId must be 11 chars
//911 -> HealthId must not be empty

//920 -> email address is not valid
//921 -> email address must not be empty

//930 -> phone number is not valid
//931 -> phone number must not  be empty

//940 -> First name must not be empty

//950 -> Last name must not be empty

//960 -> Password must be at least 8 chars
//961 -> Password does not match

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

    fun loginUser(
        id:String,
        password: String
    ) {
        var repoId = id
        var repoPassword = password
        authRepo.loginUser(repoId,repoPassword)
    }

    fun onLoginButtonClick(view: View) {
        var errorCodes: MutableList<Int> = mutableListOf()
        authListener?.OnStarted()

        if (id.isNullOrEmpty() || password.isNullOrEmpty()) {

            //returns error to ui
            authListener?.OnFailure(errorCodes)
            return

        }
        //success;authentication from backend
        authListener?.OnSuccess()
        loginUser(id.toString(),password.toString())


    }

    fun onRegisterButtonClick(view: View) {
        var errorCodes = mutableListOf<Int>()

        authListener?.OnStarted()

        if(id.isNullOrEmpty()){
            errorCodes.add(911)
        }else{
            if(!HealthIdValidator.isValid(id.toString())){
                errorCodes.add(910)
                Log.d("HealthId", "Wrong HealthId")
            }else{
                Log.d("HealthId", "healthId is ok")
            }
        }

        if(email.isNullOrEmpty()){
            errorCodes.add(921)
        }else{
            if(!EmailValidator.isValid(email.toString())){
                errorCodes.add(920)
                Log.d("Email", "Wrong Email")
            }else{
                Log.d("Email", "email is ok")
            }
        }

        if(phoneNumber.isNullOrEmpty()){
            errorCodes.add(931)
        }else{
            if(!PhoneNumberValidator.isValid(phoneNumber.toString())){
                errorCodes.add(930)
                Log.d("Phone Number", "Wrong Phone Number")
            }else{
                Log.d("Phone Number", "Phone Number is ok")
            }
        }

        if(firstName.isNullOrEmpty()){
            errorCodes.add(940)
        }

        if(firstName.isNullOrEmpty()){
            errorCodes.add(950)
        }

        if(!PasswordValidator.isValid(password.toString())){
            errorCodes.add(960)
        }

        if(!PasswordConfirmValidator.isValid(password.toString(), passwordConfirm.toString())){
            errorCodes.add(961)
        }

        if(errorCodes.size == 0){
            authListener?.OnSuccess()
            registerUser(id.toString(), password.toString(), passwordConfirm.toString(),
                firstName.toString(), lastName.toString(), email.toString(), phoneNumber.toString())
        }else {
            authListener?.OnFailure(errorCodes)
        }
    }
}