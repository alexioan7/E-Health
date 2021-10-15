package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentLoginBinding
import com.alexandros.e_health.databinding.FragmentPersonalinfoBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.AuthScreenViewModel
import com.alexandros.e_health.viewmodels.PersonalinfoViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class PersonalinfoFragment : Fragment(R.layout.fragment_personalinfo) {

    private lateinit var viewModel: PersonalinfoViewModel
    private lateinit var binding: FragmentPersonalinfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalinfoBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(PersonalinfoViewModel::class.java)
        binding.personalinfoviewmodel = viewModel

        viewModel.getUserInfoFromRepo().observe(requireActivity(), {
            binding.personalinfoviewmodel.firstname = it.data.user.name
            binding.personalinfoviewmodel.amka = it.data.user.amka
            binding.personalinfoviewmodel.bloodType = it.data.user.bloodtype
            binding.personalinfoviewmodel.email = it.data.user.email
            binding.personalinfoviewmodel.surname = it.data.user.surname
            binding.personalinfoviewmodel.phoneNumber = it.data.user.phoneNumber
            binding.personalinfoviewmodel.familyDoctor = it.data.user.familyDoctor


        })

    }


}