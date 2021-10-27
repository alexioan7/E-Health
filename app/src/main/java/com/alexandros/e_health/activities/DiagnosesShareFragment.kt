package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.HospitalsDetails
import com.alexandros.e_health.api.responseModel.HospitalsUserResponse
import com.alexandros.e_health.databinding.FragmentDiagnosesShareBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.utils.toast
import com.alexandros.e_health.viewmodels.AuthFunctionsHospitalByPresc
import com.alexandros.e_health.viewmodels.AuthFunctionsShareDiagnoses
import com.alexandros.e_health.viewmodels.DiagnosesShareViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class DiagnosesShareFragment: Fragment(R.layout.fragment_diagnoses_share) ,AuthFunctionsShareDiagnoses, AuthFunctionsHospitalByPresc{

    private lateinit var binding: FragmentDiagnosesShareBinding
    private lateinit var viewmodel: DiagnosesShareViewModel
    private val hosp = mutableListOf<HospitalsDetails>()
    private lateinit var arrayAdapterShared: ArrayAdapter<*>
    private val arrayOfSharedDiagnoses = mutableListOf<HospitalsDetails>()
    private lateinit var arrayAdapter: ArrayAdapter<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val diagid = arguments?.getString("diagnosisID")
        Log.d("Diagnosis id:", "$diagid")
        binding = FragmentDiagnosesShareBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(DiagnosesShareViewModel::class.java)

        viewmodel.authListenerdiag=this
        viewmodel.authHospitalListenerpresc=this

        if(diagid != null){

            viewmodel.requestHospitalsBySharedDiagnoses(diagid)

        }
        viewmodel.getHospitalsByDiag().observe(viewLifecycleOwner,{
            arrayOfSharedDiagnoses.clear()
            arrayOfSharedDiagnoses.addAll(it.data.hospitals)
            var myHospitalByDiagList = binding.hospitalsByDiagList

            try{
                arrayAdapterShared = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_list_item_1,
                    arrayOfSharedDiagnoses
                )
                myHospitalByDiagList.adapter = arrayAdapterShared
            } catch (e: Exception) {
                Log.d("ArrayAdapter", e.toString())
            }

            viewmodel.requestHospitals()
            viewmodel.getHospitalsFromRepo().observe(viewLifecycleOwner, {
                Log.d("HOSPITALS", it.data.hospitals.toString())

                var tempHosp = mutableListOf<HospitalsDetails>()
                tempHosp.addAll(it.data.hospitals)
                if(arrayOfSharedDiagnoses.size != 0) {
                    tempHosp.removeAll(arrayOfSharedDiagnoses)
                }
                hosp.clear()
                hosp.addAll(tempHosp)

                var myHospitalList= binding.hospitalsList

                try {
                    arrayAdapter = ArrayAdapter(
                        requireActivity(),
                        android.R.layout.simple_list_item_checked,
                        hosp
                    )
                    myHospitalList.adapter = arrayAdapter
                } catch (e: Exception) {
                    Log.d("ArrayAdapter", e.toString())
                }
            })
        })


        binding.sharebutton.setOnClickListener {
            if(hosp.size != 0) {
                val position = binding.hospitalsList.checkedItemPosition
                var hospital_id = hosp[position]._id
                var diagnosis_id = diagid
                var hospital_name = hosp[position].name
                viewmodel.onShareButtonClick(hospital_name, hospital_id, diagnosis_id.toString())
            }
        }

        binding.backToPrescriptions.setOnClickListener {
            findNavController().navigate(R.id.action_diagnosesShareFragment_to_diagnosesFragment)
        }


    }

    override fun onSuccessDiagnosesShare(hospitalName: String)  {
        Log.d("In success","In onsuccssPrescriptionShare")
        toast("You have successfully shared your diagnosis with $hospitalName", activity)
        //Toast.makeText(activity, "You successfully shared your prescription with $hospitalName", Toast.LENGTH_SHORT).show()
        for(hospital in hosp) {
            if(hospitalName.contains(hospital.name)) {
                arrayOfSharedDiagnoses.add(hospital)
                hosp.remove(hospital)
                break
            }
        }
        arrayAdapter.notifyDataSetChanged()
        arrayAdapterShared.notifyDataSetChanged()
    }

    override fun onFailureDiagnosesShare(failuremessage: String){
        toast("$failuremessage",activity)

    }

    override fun onSuccessHospitalsByPresc(responseList: MutableLiveData<HospitalsUserResponse>) {


    }


    override fun onResume() {
        super.onResume()
        hosp.clear()
        arrayOfSharedDiagnoses.clear()
    }



}