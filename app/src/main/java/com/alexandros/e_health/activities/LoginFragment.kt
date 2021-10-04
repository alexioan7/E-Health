package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentLoginBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.AuthFunctions
import com.alexandros.e_health.viewmodels.AuthScreenViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login), AuthFunctions {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewmodel: AuthScreenViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(),ViewModelFactory(AuthRepository)).get(AuthScreenViewModel::class.java)

        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI

        binding.loginviewmodel = viewmodel

        viewmodel.authListener = this


        binding.textViewGoToRegister.setOnClickListener() {
            navRegister()
        }


    }

    private fun navRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun OnStarted() {
        Log.d("Login fragment", "Login...")
        Toast.makeText(activity, "Login", Toast.LENGTH_LONG).show()


    }

    override fun OnSuccess() {
        //toast("Succeed login")
        Log.d("Login fragment", "Succeed")
        Toast.makeText(activity,"Login Succeed",Toast.LENGTH_LONG).show()
    }

    override fun OnFailure(errorCode: MutableList<Int>) {
        //toast("Wrong Id and Password")
        Log.d("Login fragment", "Wrong Id or Password")
        Toast.makeText(activity,"Login Failed",Toast.LENGTH_LONG).show()
    }


}