package com.alexandros.e_health.utils

import android.util.Patterns
import java.util.regex.Matcher

class HealthIdValidator {
    companion object {
        fun isValid(healthid: String): Boolean {
            if (healthid.toString().length != 11) {
                return false
            }
            return true
        }
    }
}

class EmailValidator{
    companion object{
        fun isValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

    }
}

class PhoneNumberValidator{
    companion object{
        fun isValid(phoneNumber: String): Boolean {
            // Patterns
            var mobilePhonePattern = "^69[0-9]{8}"
            var PhoneNumberWithCodePatter = "^[+]30[0-9]{10}"
            var landlinePattern = "^2[0-9]{9}"

            //Checking phone number
            if(Regex(mobilePhonePattern).matches(phoneNumber) || Regex(PhoneNumberWithCodePatter).matches(phoneNumber) ||  Regex(landlinePattern).matches(phoneNumber))
                return true
            return false
        }
    }
}

class PasswordValidator{
    companion object{
        fun isValid(password: String): Boolean{
            var minLength = 8
            if( password.length >= minLength)
                return true
            return false
        }
    }
}

class PasswordConfirmValidator{
    companion object{
        fun isValid(password: String, passwordConfirm: String): Boolean{
            if(password.equals(passwordConfirm)) {
                return true
            }
            return false

        }
    }
}
