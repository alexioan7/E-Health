package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.Department
import com.alexandros.e_health.api.responseModel.HospitalsDetails
import com.alexandros.e_health.databinding.FragmentCreateAppointmentBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.CreateAppointmentViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class CreateAppointmentFragment : Fragment(R.layout.fragment_create_appointment) {

    private lateinit var viewModel: CreateAppointmentViewModel
    private lateinit var binding: FragmentCreateAppointmentBinding

    private var hospitalList = mutableListOf<HospitalsDetails>()
    private var hospitalDepartmentsList = mutableListOf<Department>()
    private lateinit  var arrayHospitalDepartmentsAdapter: ArrayAdapter<*>


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

                            val hospitaldepartments = it.data.departments
//                            hospitaldepartments.forEach { it2 ->
//                                hospitalDepartmentsList.add(
//                                    Department(
//                                        it2.__v,
//                                        it2._id,
//                                        it2.hospital,
//                                        it2.name
//                                    )
//                                )
//                                arrayOfHospitalDepartments.add(it2.name)
//
//                            }

                            try {
                                arrayHospitalDepartmentsAdapter = ArrayAdapter(
                                    requireActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    hospitaldepartments

                                )
                                hospitalDepartmentsSpinner.adapter = arrayHospitalDepartmentsAdapter
                            }catch (e: Exception){
                                Log.d("ArrayAdapter", e.toString())
                        }



//                            try {
//                                arrayHospitalDepartmentsAdapter = ArrayAdapter(
//                                    requireActivity(),
//                                    android.R.layout.simple_spinner_dropdown_item,
//                                    arrayOfHospitalDepartments
//
//                                )




//                            } catch (e: Exception) {
//                                Log.d("ArrayAdapter", e.toString())
//
//                            }
                        }


                        )


                    }
                }


        })
    }
}