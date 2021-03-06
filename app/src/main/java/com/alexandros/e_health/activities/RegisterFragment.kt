package com.alexandros.e_health.activities

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentRegisterBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.utils.toast
import com.alexandros.e_health.viewmodels.AuthFunctions
import com.alexandros.e_health.viewmodels.AuthScreenViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory
import java.time.Duration

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
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    override fun OnStarted() {
        Toast.makeText(requireContext(),"Registering...", Toast.LENGTH_LONG).show()
        binding.btnRegister.setEnabled(false)
        binding.textViewHealthIdError.visibility = View.INVISIBLE
        binding.textViewEmailError.visibility = View.INVISIBLE
        binding.textViewPhoneNumberError.visibility = View.INVISIBLE
        binding.textViewFirstNameError.visibility = View.INVISIBLE
        binding.textViewLastNameError.visibility = View.INVISIBLE
        binding.textViewPasswordError.visibility = View.INVISIBLE
        binding.textViewPasswordConfirmError.visibility = View.INVISIBLE
        Log.d("register fragment", "Signing up...")
//        Toast.makeText(activity, "Login", Toast.LENGTH_LONG).show()
//        toast("Sign up...", activity)
    }

    override fun OnSuccess() {
        Log.d("RegisterFragment", "Succeed..")

        //Log.i("VIEWMODEL", viewmodel.getRegisteredUserDataFromRepo().value?.status.toString())
        viewmodel.getRegisteredUserDataFromRepo().observe(requireActivity(), {
            Log.i("VIEWMODEL", "OnSuccess: ${it?.token}")
            Toast.makeText(requireContext(),"Signed up!", Toast.LENGTH_LONG).show()
            navRegister()
        })
        viewmodel.getFailureMessageFromRegister().observe(requireActivity(), {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        binding.btnRegister.setEnabled(true)
    }

    override fun OnFailure(errorCodes: MutableList<Int>) {
        binding.btnRegister.setEnabled(true)
        for(error in errorCodes) {
            if (error == 910) {
                binding.textViewHealthIdError.visibility = View.VISIBLE
                binding.textViewHealthIdError.text = getString(R.string.HealthID_not_valid_error)
            }
            if (error == 911){
                binding.textViewHealthIdError.visibility = View.VISIBLE
                binding.textViewHealthIdError.text = getString(R.string.field_required)
            }
            if (error == 920){
                binding.textViewEmailError.visibility = View.VISIBLE
                binding.textViewEmailError.text = getString(R.string.email_not_valid_error)
            }
            if (error == 921){
                binding.textViewEmailError.visibility = View.VISIBLE
                binding.textViewEmailError.text = getString(R.string.field_required)
            }
            if (error == 930){
                binding.textViewPhoneNumberError.visibility = View.VISIBLE
                binding.textViewPhoneNumberError.text = getString(R.string.phone_number_not_valid_error)
            }
            if (error == 931){
                binding.textViewPhoneNumberError.visibility = View.VISIBLE
                binding.textViewPhoneNumberError.text = getString(R.string.field_required)
            }
            if (error == 940){
                binding.textViewFirstNameError.visibility = View.VISIBLE
                binding.textViewFirstNameError.text = getString(R.string.field_required)
            }
            if (error == 950){
                binding.textViewLastNameError.visibility = View.VISIBLE
                binding.textViewLastNameError.text = getString(R.string.field_required)
            }
            if (error == 960){
                binding.textViewPasswordError.visibility = View.VISIBLE
                binding.textViewPasswordError.text = getString(R.string.password_length_error)
            }
            if (error == 961){
                binding.textViewPasswordConfirmError.visibility = View.VISIBLE
                binding.textViewPasswordConfirmError.text = getString(R.string.password_not_match_error)
            }

        }



    }
}