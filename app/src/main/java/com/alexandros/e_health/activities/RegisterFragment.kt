package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentRegisterBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.AuthFunctions
import com.alexandros.e_health.viewmodels.AuthScreenViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register), AuthFunctions {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewmodel: AuthScreenViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(),ViewModelFactory(AuthRepository)).get(AuthScreenViewModel::class.java)


        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI

        binding.registerviewmodel = viewmodel

        viewmodel.authListener = this
        binding.tvLogin.setOnClickListener {
            navRegister()
        }
    }

    private fun navRegister() {
        findNavController().popBackStack()
    }

    override fun OnStarted() {

        binding.textViewHealthIdError.visibility = View.INVISIBLE
        binding.textViewEmailError.visibility = View.INVISIBLE
        binding.textViewPhoneNumberError.visibility = View.INVISIBLE
        binding.textViewFirstNameError.visibility = View.INVISIBLE
        binding.textViewLastNameError.visibility = View.INVISIBLE
        binding.textViewPasswordError.visibility = View.INVISIBLE
        binding.textViewPasswordConfirmError.visibility = View.INVISIBLE

    }

    override fun OnSuccess() {
        Log.d("RegisterFragment", "Succeed..")
    }

    override fun OnFailure(errorCodes: MutableList<Int>) {
        for(error in errorCodes) {
            if (error == 910) {
                binding.textViewHealthIdError.visibility = View.VISIBLE
                binding.textViewHealthIdError.text = "HealthId must be 11 numbers"
            }
            if (error == 911){
                binding.textViewHealthIdError.visibility = View.VISIBLE
                binding.textViewHealthIdError.text = "HealthId must not be empty"
            }
            if (error == 920){
                binding.textViewEmailError.visibility = View.VISIBLE
                binding.textViewEmailError.text = "Email is not valid"
            }
            if (error == 921){
                binding.textViewEmailError.visibility = View.VISIBLE
                binding.textViewEmailError.text = "Email must not be empty"
            }
            if (error == 930){
                binding.textViewPhoneNumberError.visibility = View.VISIBLE
                binding.textViewPhoneNumberError.text = "Phone Number is not valid"
            }
            if (error == 931){
                binding.textViewPhoneNumberError.visibility = View.VISIBLE
                binding.textViewPhoneNumberError.text = "Phone Number must not be empty"
            }
            if (error == 940){
                binding.textViewFirstNameError.visibility = View.VISIBLE
                binding.textViewFirstNameError.text = "First Name must not be empty"
            }
            if (error == 950){
                binding.textViewLastNameError.visibility = View.VISIBLE
                binding.textViewLastNameError.text = "Last Name must not be empty"
            }
            if (error == 960){
                binding.textViewPasswordError.visibility = View.VISIBLE
                binding.textViewPasswordError.text = "Password must be at least 8 characters"
            }
            if (error == 961){
                binding.textViewPasswordConfirmError.visibility = View.VISIBLE
                binding.textViewPasswordConfirmError.text = "Passwords does not match"
            }

        }
    }
}