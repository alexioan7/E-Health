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
import com.alexandros.e_health.databinding.FragmentPrescriptionsShareBinding
import com.alexandros.e_health.viewmodels.PrescriptionsShareViewModel

class PrescriptionsShareFragment: Fragment(R.layout.fragment_prescriptions_share){

    private lateinit var binding: FragmentPrescriptionsShareBinding
    private lateinit var viewmodel: PrescriptionsShareViewModel
    private val hosp= mutableListOf<HospitalsDetails>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrescriptionsShareBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(PrescriptionsShareViewModel::class.java)
        val arrayOfHospitals=ArrayList<String>()
        //val arrayOfHospitalPrefectures=ArrayList<String>()

        viewmodel.requestSharePrescriptions()
        viewmodel.getHospitalsFromRepo().observe(requireActivity(), {
            Log.d("HOSPITALS", it.data.hospitals.toString())
            val hospitals=it.data.hospitals
            hospitals.forEach{it2->
                hosp.add(HospitalsDetails(it2._id,it2.name,it2.prefecture))
                arrayOfHospitals.add(it2.name+","+it2.prefecture)


            }

            val arrayAdapter: ArrayAdapter<*>
            var myHospitalList=binding.hospitalsList
            try{
                arrayAdapter= ArrayAdapter(requireActivity(),android.R.layout.simple_list_item_checked,arrayOfHospitals)
                myHospitalList.adapter=arrayAdapter
            }catch (e:Exception){
                Log.d("ArrayAdapter", e.toString())
            }



            binding.backToPrescriptions.setOnClickListener{
                findNavController().navigate(R.id.action_prescriptionsShareFragment_to_prescriptionsFragment)
            }

//            requireActivity().onBackPressedDispatcher.addCallback{
//                findNavController().navigate(R.id.action_prescriptionsShareFragment_to_prescriptionsFragment)
//            }



        })



    }


}