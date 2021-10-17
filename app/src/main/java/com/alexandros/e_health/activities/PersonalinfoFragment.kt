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
import java.lang.StringBuilder

class PersonalinfoFragment : Fragment(R.layout.fragment_personalinfo) {

    private lateinit var viewModel: PersonalinfoViewModel
    private lateinit var binding: FragmentPersonalinfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalinfoBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity(),ViewModelFactory(AuthRepository)).get(PersonalinfoViewModel::class.java)
        binding.personalinfoviewmodel = viewModel

        viewModel.getUserInfoFromRepo().observe(requireActivity(), {

            binding.personalinfoviewmodel.apply {

                this?.firstname = it?.data?.user?.name
                this?.amka = it?.data?.user?.amka
                this?.bloodType = it?.data?.user?.bloodtype
                this?.email = it?.data?.user?.email
                this?.surname = it?.data?.user?.surname
                this?.phoneNumber = it?.data?.user?.phoneNumber
                this?.familyDoctor =it?.data?.user?.familyDoctor
                this?.familyDoctorFullName =  String.format("%s %s" ,it?.data?.user?.familyDoctor?.name.toString(), it?.data?.user?.familyDoctor?.surname.toString())



            }

           val familyDoctorStringName =  String.format("%s %s" ,it?.data?.user?.familyDoctor?.name.toString(), it?.data?.user?.familyDoctor?.surname.toString())



        })

    }


}