package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandros.e_health.R
import com.alexandros.e_health.adapters.AppointmentsAdapter
import com.alexandros.e_health.adapters.PrescriptionsAdapter
import com.alexandros.e_health.api.responseModel.Appointment
import com.alexandros.e_health.databinding.FragmentAppointmentsBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.AppointmentsViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class AppointmentsFragment : Fragment(R.layout.fragment_appointments) {

    private lateinit var viewmodel: AppointmentsViewModel
    private lateinit var binding: FragmentAppointmentsBinding

    private lateinit var appointments : List<Appointment>
    private var futureAppointments = mutableListOf<Appointment>()
    private var pastAppointments = mutableListOf<Appointment>()
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
            appointments = prescriptions
            var temList = mutableListOf<Appointment>()
            appointments.forEach {
                if(it.active){
                    temList.add(it)
                }
            }
            futureAppointments = temList

            Log.d("Future", futureAppointments.toString())
            Log.d("Future", futureAppointments.toString())
            initRecyclerView()
        })

        binding.createAppointmentButton.setOnClickListener {
            goToCreateAppointmentFragment()
        }

        binding.buttonFuture.setOnClickListener{
            appointments.forEach {
                if(it.active){
                    futureAppointments.add(it)
                }
            }
        }

        binding.buttonFuture.setOnClickListener{
            var temList = mutableListOf<Appointment>()
            appointments.forEach {
                if(it.active){
                    temList.add(it)
                }
            }
            futureAppointments = temList
            Log.d("Future", futureAppointments.toString())
            if(!futureAppointments.isNullOrEmpty()) {
                updateAdapter(futureAppointments)
            }

        }

        binding.buttonPast.setOnClickListener{
            var temList = mutableListOf<Appointment>()
            appointments.forEach {it2->
                if(!it2.active){
                    temList.add(it2)
                }
            }
            pastAppointments = temList

            Log.d("Future", pastAppointments.toString())
            if(!pastAppointments.isNullOrEmpty()) {
                pastAppointments = pastAppointments.sortedByDescending { it2 ->
                    it2.date
                } as MutableList<Appointment>
                updateAdapter(pastAppointments)
            }
        }
    }

    private fun updateAdapter(list: MutableList<Appointment>){
        binding.recyclerviewAppointments.apply {
            if(!list.isNullOrEmpty()) {
                adapter = AppointmentsAdapter(list)
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerviewAppointments.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AppointmentsAdapter(futureAppointments)
        }
    }


    private fun goToCreateAppointmentFragment() {
        findNavController().navigate(R.id.action_navigation_appointments_to_createAppointmentFragment)
    }
}