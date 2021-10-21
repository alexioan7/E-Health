package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandros.e_health.R
import com.alexandros.e_health.adapters.AppointmentsAdapter
import com.alexandros.e_health.api.responseModel.Appointment
import com.alexandros.e_health.databinding.FragmentAppointmentsBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.AppointmentsViewModel
import com.alexandros.e_health.viewmodels.PrescriptionsAdapter
import com.alexandros.e_health.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_appointments.*
import kotlinx.android.synthetic.main.fragment_prescriptions.*

class AppointmentsFragment : Fragment(R.layout.fragment_appointments) {

    private lateinit var viewmodel: AppointmentsViewModel
    private lateinit var binding: FragmentAppointmentsBinding

    private val appointments = mutableListOf<Appointment>()
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAppointmentsBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(
            AppointmentsViewModel::class.java
        )

        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI

        binding.appointmentsviewmodel = viewmodel
        viewmodel.requestUserAppointments()

        viewmodel.getUserAppointmentsromRepo().observe(requireActivity(), {
            Log.d("APPOINTMENTS!!", it.data.appointments.toString())
            val prescriptions = it.data.appointments
            prescriptions.forEach { it2 ->
                appointments.add(
                    Appointment(
                        it2._id,
                        it2.active,
                        it2.date,
                        it2.department,
                        it2.id,
                        it2.user
                    )
                )
                initRecyclerView()
            }
        })
    }

    private fun initRecyclerView() {
        recyclerview_appointments.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AppointmentsAdapter(appointments)
        }
    }
}