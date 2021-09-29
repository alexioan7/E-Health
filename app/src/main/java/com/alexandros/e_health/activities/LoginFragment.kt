package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentLoginBinding
import com.alexandros.e_health.viewmodels.LoginFunctions
import com.alexandros.e_health.viewmodels.LoginScreenViewModel

class LoginFragment : Fragment(R.layout.fragment_login), LoginFunctions {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        val viewmodel= ViewModelProviders.of(this).get(LoginScreenViewModel::class.java)

        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI

        binding.loginviewmodel=viewmodel

        viewmodel.authListener=this


        binding.textViewGoToRegister.setOnClickListener(){
            navRegister()
        }




    }

    private fun navRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun OnStarted() {
        Log.d("Login fragment","Login...")
        //Toast.makeText(MainActivity, "Login", Toast.LENGTH_LONG).show()
      //toast("Login..")
    }

    override fun OnSuccess() {
        //toast("Succeed login")
        Log.d("Login fragment","Succeed")
    }

    override fun OnFailure(message: String) {
        //toast("Wrong Id and Password")
        Log.d("Login fragment","Wrong Id or Password")
    }


}