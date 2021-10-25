package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.HospitalsDetails
import com.alexandros.e_health.databinding.FragmentDiagnosesShareBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.utils.toast
import com.alexandros.e_health.viewmodels.AuthFunctionsShareDiagnoses
import com.alexandros.e_health.viewmodels.DiagnosesShareViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class DiagnosesShareFragment: Fragment(R.layout.fragment_diagnoses_share) ,AuthFunctionsShareDiagnoses{

    private lateinit var binding: FragmentDiagnosesShareBinding
    private lateinit var viewmodel: DiagnosesShareViewModel
    private val hosp = mutableListOf<HospitalsDetails>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiagnosesShareBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(DiagnosesShareViewModel::class.java)
        val arrayOfHospitals = ArrayList<String>()
        //val arrayOfHospitalPrefectures=ArrayList<String>()
        viewmodel.authListenerdiag=this

        viewmodel.requestHospitals()
        viewmodel.getHospitalsFromRepo().observe(requireActivity(), {
            Log.d("HOSPITALS", it.data.hospitals.toString())
            val hospitals = it.data.hospitals
            hospitals.forEach { it2 ->
                hosp.add(HospitalsDetails(it2._id, it2.name, it2.prefecture))
                arrayOfHospitals.add(it2.name + "," + it2.prefecture)


            }

            Log.d("hosp1", hosp[0]._id)


            var arrayAdapter: ArrayAdapter<*>
            var myHospitalList = binding.hospitalsList
            //arrayAdapter= ArrayAdapter(requireActivity(),android.R.layout.simple_list_item_checked,arrayOfHospitals)
            try {
                arrayAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_list_item_checked,
                    arrayOfHospitals
                )
                myHospitalList.adapter = arrayAdapter
            } catch (e: Exception) {
                Log.d("ArrayAdapter", e.toString())
            }

            val diagid = arguments?.getString("diagnosisID")
            Log.d("Diagnosis id:", "$diagid")

            binding.hospitalsList.setOnItemClickListener { parent, view, position, id ->
                val element = parent.getItemAtPosition(position)
                Log.d("Item in hospitals", "$element")
                var hospital_id = hosp[position]._id
                var diagnosis_id = diagid
                var hospital_name=hosp[position].name

                binding.sharebutton.setOnClickListener {
                    viewmodel.onShareButtonClick(hospital_name,hospital_id,diagnosis_id.toString())

                }


            }



            binding.backToPrescriptions.setOnClickListener {
                findNavController().navigate(R.id.action_diagnosesShareFragment_to_diagnosesFragment)
            }


        })


    }

    override fun onSuccessDiagnosesShare(hospitalName: String)  {
        Log.d("In success","In onsuccssPrescriptionShare")
        toast("You have successfully shared your diagnosis with $hospitalName", activity)
        //Toast.makeText(activity, "You successfully shared your prescription with $hospitalName", Toast.LENGTH_SHORT).show()
    }

    override fun onFailureDiagnosesShare(failuremessage: String){
        toast("$failuremessage",activity)
    }



}