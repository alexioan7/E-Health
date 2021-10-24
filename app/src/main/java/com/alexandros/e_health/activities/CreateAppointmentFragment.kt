package com.alexandros.e_health.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentCreateAppointmentBinding

class CreateAppointmentFragment : Fragment(R.layout.fragment_create_appointment) {

    private lateinit var binding: FragmentCreateAppointmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateAppointmentBinding.bind(view)
    }








}