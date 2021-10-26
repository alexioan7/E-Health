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
import com.alexandros.e_health.api.responseModel.Timeslots
import com.alexandros.e_health.databinding.FragmentCreateAppointmentBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.CreateAppointmentViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList

class CreateAppointmentFragment : Fragment(R.layout.fragment_create_appointment) {

    private lateinit var viewModel: CreateAppointmentViewModel
    private lateinit var binding: FragmentCreateAppointmentBinding

    private var hospitalList = mutableListOf<HospitalsDetails>()
    private var hospitalDepartmentsList = mutableListOf<Department>()

    private var timeslotList = mutableListOf<String>()
    private lateinit var timeslots : List<String>

    private lateinit  var arrayHospitalDepartmentsAdapter: ArrayAdapter<*>
    private val departmentIdList = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateAppointmentBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(
            CreateAppointmentViewModel::class.java
        )
        val arrayOfHospitals = ArrayList<String>()

        viewModel.requestHospitals()
        viewModel.getHospitalsFromRepo().observe(requireActivity(), {
            val hospitals = it.data.hospitals
            hospitals.forEach { it2 ->
                hospitalList.add(HospitalsDetails(it2._id, it2.name, it2.prefecture))
                arrayOfHospitals.add(it2.name + ", " + it2.prefecture)

            }
            var arrayHospitlasAdapter: ArrayAdapter<*>
            var myHospitalList = binding.hospitalsSpinner

            var arrayOfHospitalDepartments = mutableListOf<String>()
            var hospitalDepartmentsSpinner = binding.departmentSpinner



            try {
                arrayHospitlasAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    arrayOfHospitals
                )

                myHospitalList.adapter = arrayHospitlasAdapter
                arrayHospitalDepartmentsAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    arrayOfHospitalDepartments

                )
                hospitalDepartmentsSpinner.adapter = arrayHospitalDepartmentsAdapter



            } catch (e: Exception) {
                Log.d("ArrayAdapter", e.toString())
            }

            binding.hospitalsSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(adapterView: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val hospital_id = hospitalList[position]._id
                        val hospital_name = hospitalList[position].name
//                        var arrayOfHospitalDepartments = mutableListOf<String>()
//                        var arrayHospitalDepartmentsAdapter: ArrayAdapter<*

                        Log.d("Hospital Departments List",hospitalDepartmentsList.toString())
                        viewModel.requestHospitalDepartments(hospital_id)
                        viewModel.getHospitalDepartmentsFromRepo().observe(requireActivity(), {
                            hospitalDepartmentsList.clear()
                            arrayOfHospitalDepartments.clear()
                            departmentIdList.clear()

                            val hospitaldepartments = it.data.departments
                            hospitaldepartments.forEach { it2 ->
                                hospitalDepartmentsList.add(
                                    Department(
                                        it2.__v,
                                        it2._id,
                                        it2.hospital,
                                        it2.name
                                    )
                                )
                                arrayOfHospitalDepartments.add(it2.name)
                                Log.d("ADDDEPARTMENTID", it2._id)
                                departmentIdList.add(it2._id)

                            }

                            arrayHospitalDepartmentsAdapter.notifyDataSetChanged()

                        }


                        )

                    }
                }
        })

        var selectedDepartmentId = ""
        binding.departmentSpinner.onItemSelectedListener =
            object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedDepartmentId = departmentIdList[position]
                    Log.d("DEPARTMETNTID", selectedDepartmentId)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
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


                setSpinnerTimeslots(selectedDepartmentId,year.toString() + "-" + (monthOfYear+1).toString() + "-" + dayOfMonth.toString())
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
                    selectedTimeslot = timeslotList[position]
                    Log.d("SelectedTimeSlot", selectedTimeslot)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }


            }

        binding.confirmAppointmentButton.setOnClickListener{
            viewModel.requestToCreateAppointment(selectedDate,selectedTimeslot,selectedDepartmentId)
            viewModel.getResponseDataFromCreateAppointment().observe(requireActivity(),{
                if(it?.status == "success"){
                    goToAppointmentFragment()
                }else{

                    Log.d("ERRORRRRRRRR",  viewModel.getFailureMessageFromCreateAppointment().value.toString())
                    Toast.makeText(requireContext(), viewModel.getFailureMessageFromCreateAppointment().value.toString(), Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun setSpinnerTimeslots(id: String, date: String){
        timeslotList.clear()
        viewModel.requestTimeslots(id, date)
        viewModel.getTimeSlots().observe(requireActivity(), {
            Log.d("appointmentList", it.data.appointmentList.toString())
            timeslots = it.data.appointmentList
            timeslots.forEach { it2 ->
                timeslotList.add(it2)
            }
            var timeslotArrayAdapter: ArrayAdapter<*>
            var timeslotsSpinner = binding.timeSelectionSpinner

            try {
                timeslotArrayAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    timeslotList
                )

                timeslotsSpinner.adapter = timeslotArrayAdapter


            } catch (e: Exception) {
                Log.d("ArrayAdapter", e.toString())
            }
        })
    }

    private fun goToAppointmentFragment() {
        try {
            findNavController().navigate(R.id.action_createAppointmentFragment_to_navigation_appointments)
        }catch (e: Exception){
            Log.d("NavController", e.toString())
        }
    }
}