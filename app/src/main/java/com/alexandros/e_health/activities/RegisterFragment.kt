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
        Log.d("RegisterFragment", "Signin up..")
    }

    override fun OnSuccess() {
        Log.d("RegisterFragment", "Succeed..")
    }

    override fun OnFailure(message: String) {
        Log.d("RegisterFragment", "Something went wrong")
    }
}