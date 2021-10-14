package com.alexandros.e_health.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentAppointmentsBinding
import com.alexandros.e_health.viewmodels.AppointmentsViewModel

class AppointmentsFragment : Fragment(R.layout.fragment_appointments) {

    private lateinit var viewmodel: AppointmentsViewModel
    private lateinit var binding: FragmentAppointmentsBinding

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAppointmentsBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(AppointmentsViewModel::class.java)

        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI

        binding.appointmentsviewmodel = viewmodel
    }
}