package com.alexandros.e_health.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.HospitalsDetails
import com.alexandros.e_health.databinding.FragmentCreateAppointmentBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.AppointmentsViewModel
import com.alexandros.e_health.viewmodels.CreateAppointmentViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class CreateAppointmentFragment : Fragment(R.layout.fragment_create_appointment) {

    private lateinit var viewModel: CreateAppointmentViewModel
    private lateinit var binding: FragmentCreateAppointmentBinding

    private lateinit var hospitalList: List<HospitalsDetails>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateAppointmentBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(CreateAppointmentViewModel::class.java)
        val arrayOfHospitals = ArrayList<String>()
    }





}