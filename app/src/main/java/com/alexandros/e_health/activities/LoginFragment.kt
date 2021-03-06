package com.alexandros.e_health.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentLoginBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.utils.SharedPreferencesUtil
import com.alexandros.e_health.utils.toast
import com.alexandros.e_health.viewmodels.AuthFunctions
import com.alexandros.e_health.viewmodels.AuthScreenViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login), AuthFunctions {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewmodel: AuthScreenViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(
            AuthScreenViewModel::class.java
        )
        sharedPreferences = requireActivity().getSharedPreferences(
            requireActivity().packageName,
            Activity.MODE_PRIVATE
        )

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
        binding.textViewError.visibility = View.INVISIBLE
        Log.d("Login fragment", "Login...")
//        Toast.makeText(activity, "Login", Toast.LENGTH_LONG).show()
       toast("Login...", activity)

    }

    override fun OnSuccess() {
        Log.d("Login fragment", "Succeed")

        viewmodel.getLoginUserDataFromRepo().observe(requireActivity(), {
            SharedPreferencesUtil.saveAccessToken(it?.token.toString())
            val intent = Intent(activity, BottomNavigationActivity::class.java)
            startActivity(intent)
        })

    }

    override fun OnFailure(errorCode: MutableList<Int>) {
        Log.d("Login fragment", "Wrong Id or Password")
        binding.textViewError.visibility = View.VISIBLE
        binding.textViewError.setText("Wrong Health Identification Number or Password.")
        toast("Wrong Health Id or Password", activity)
    }


}