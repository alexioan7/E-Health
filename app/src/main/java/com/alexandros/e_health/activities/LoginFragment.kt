package com.alexandros.e_health.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentLoginBinding
import com.alexandros.e_health.databinding.FragmentRegisterBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.tvRegister.setOnClickListener(){
            navRegister()
        }
    }

    private fun navRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }


}