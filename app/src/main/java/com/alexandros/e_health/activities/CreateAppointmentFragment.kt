package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.Department
import com.alexandros.e_health.api.responseModel.HospitalsDetails
import com.alexandros.e_health.databinding.FragmentCreateAppointmentBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.CreateAppointmentViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*
import android.app.Activity




class CreateAppointmentFragment : Fragment(R.layout.fragment_create_appointment) {

    private lateinit var viewModel: CreateAppointmentViewModel
    private lateinit var binding: FragmentCreateAppointmentBinding

    private var hospitals = mutableListOf<HospitalsDetails>()
    private var hospitalDepartments = mutableListOf<Department>()

    private var timeslots = mutableListOf<String>()

    private lateinit  var arrayHospitalDepartmentsAdapter: ArrayAdapter<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateAppointmentBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(
            CreateAppointmentViewModel::class.java
        )

        viewModel.requestHospitals()
        viewModel.getHospitalsFromRepo().observe(requireActivity(), {
            hospitals = it.data.hospitals as MutableList<HospitalsDetails>

            var arrayHospitalsAdapter: ArrayAdapter<*>
            var myHospitalList = binding.hospitalsSpinner

            val activity: Activity? = activity
            if (activity != null) {
                arrayHospitalsAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    hospitals
                )

                myHospitalList.adapter = arrayHospitalsAdapter
            }
        })

        binding.hospitalsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(adapterView: AdapterView<*>?) {}

                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val hospital_id = hospitals[position]._id

                    Log.d("Hospital Departments List",hospitalDepartments.toString())
                    viewModel.requestHospitalDepartments(hospital_id)
                    viewModel.getHospitalDepartmentsFromRepo().observe(requireActivity(), {

                        hospitalDepartments = it.data.departments as MutableList<Department>

                        val activity: Activity? = activity
                        if (activity != null) {
                            arrayHospitalDepartmentsAdapter = ArrayAdapter(
                                activity,
                                android.R.layout.simple_spinner_dropdown_item,
                                hospitalDepartments
                            )
                        }

                        var hospitalDepartmentsSpinner = binding.departmentSpinner
                        hospitalDepartmentsSpinner.adapter = arrayHospitalDepartmentsAdapter
                    })
                }
            }

        var selectedDepartment = ""
        binding.departmentSpinner.onItemSelectedListener=
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ){
                    selectedDepartment = hospitalDepartments[position]._id
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

        var selectedDate = ""
        binding.selectedDateTextView.setOnClickListener{
            val tmro = Calendar.getInstance()
            tmro.set(tmro.get(Calendar.YEAR), tmro.get(Calendar.MONTH), tmro.get(Calendar.DATE)+1)
            val max = Calendar.getInstance()
            max.set(tmro.get(Calendar.YEAR), max.get(Calendar.MONTH)+12, max.get(Calendar.DATE))

            val dpd = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->

                selectedDate = year.toString() + "-" + (monthOfYear+1).toString() +  "-" + dayOfMonth.toString()

                binding.selectedDateTextView.setText(dayOfMonth.toString() + "-" + (monthOfYear+1).toString() + "-" + year.toString())

                viewModel.requestTimeslots(selectedDepartment, selectedDate)
                viewModel.getTimeSlots().observe(requireActivity(), {
                    Log.d("appointmentList", it.data.appointmentList.toString())
                    timeslots = it.data.appointmentList as MutableList<String>

                    var timeslotArrayAdapter: ArrayAdapter<*>
                    var timeslotsSpinner = binding.timeSelectionSpinner

                    val activity: Activity? = activity
                    if (activity != null) {
                        timeslotArrayAdapter = ArrayAdapter(
                            requireActivity(),
                            android.R.layout.simple_spinner_dropdown_item,
                            timeslots
                        )

                        timeslotsSpinner.adapter = timeslotArrayAdapter
                    }
                })
            }

            dpd.minDate = tmro
            dpd.maxDate = max

            //disable Weekends
            var loopdate: Calendar = tmro
            while (tmro.before(max)) {
                val dayOfWeek = loopdate[Calendar.DAY_OF_WEEK]
                if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                    val disabledDays = arrayOfNulls<Calendar>(1)
                    disabledDays[0] = loopdate
                    dpd.disabledDays = disabledDays
                }
                tmro.add(Calendar.DATE,1)
                loopdate = tmro
            }

            getFragmentManager()?.let { it1 -> dpd.show(it1, "Datepickerdialog") };
        }

        var selectedTimeslot = ""
        binding.timeSelectionSpinner.onItemSelectedListener =
            object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedTimeslot = timeslots[position]
                    Log.d("SelectedTimeSlot", selectedTimeslot)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }


            }

        binding.confirmAppointmentButton.setOnClickListener{
            viewModel.requestToCreateAppointment(selectedDate,selectedTimeslot,selectedDepartment)
            viewModel.getResponseDataFromCreateAppointment().observe(requireActivity(),{
                if(it?.status == "success"){
//                    Toast.makeText(requireContext(), "Appointment confirmed", Toast.LENGTH_SHORT).show()
                    goToAppointmentFragment()
                }else{

                    Log.d("ERRORRRRRRRR",  viewModel.getFailureMessageFromCreateAppointment().value.toString())
//                    Toast.makeText(requireContext(), viewModel.getFailureMessageFromCreateAppointment().value.toString(), Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.cancelAppointmentButton.setOnClickListener{
            goToAppointmentFragment()
        }
    }

    private fun goToAppointmentFragment() {
            findNavController().navigate(R.id.action_createAppointmentFragment_to_navigation_appointments)
    }

}