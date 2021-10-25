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
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.utils.toast
import com.alexandros.e_health.viewmodels.AuthFunctionsSharePrescriptions
import com.alexandros.e_health.viewmodels.PrescriptionsShareViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class PrescriptionsShareFragment: Fragment(R.layout.fragment_prescriptions_share) ,AuthFunctionsSharePrescriptions{

    private lateinit var binding: FragmentPrescriptionsShareBinding
    private lateinit var viewmodel: PrescriptionsShareViewModel
    private val hosp = mutableListOf<HospitalsDetails>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrescriptionsShareBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(PrescriptionsShareViewModel::class.java)
        val arrayOfHospitals = ArrayList<String>()
        val arrayOfSharedPrescriptions = ArrayList<String>()
        //val arrayOfHospitalPrefectures=ArrayList<String>()
        viewmodel.authListenerpresc=this

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

            //for the list with the shared prescriptions
           var arrayAdapterShared: ArrayAdapter<*>
           //var mySharedPrescriptions=binding.listWithSharedHospitals
//            arrayAdapter= ArrayAdapter(requireActivity(),android.R.layout.simple_list_item_checked,arrayOfHospitals)

            try {
                arrayAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_list_item_checked,
                    arrayOfHospitals
                )

                myHospitalList.adapter = arrayAdapter
//                arrayAdapterShared= ArrayAdapter(
//                    requireActivity(),
//                    android.R.layout.simple_list_item_1,
//                    arrayOfSharedPrescriptions
//                )

            } catch (e: Exception) {
                Log.d("ArrayAdapter", e.toString())
            }

            val prescid = arguments?.getString("prescriptionID")
            Log.d("Prescription id:", "$prescid")
            val prescname=arguments?.getString("prescriptionName")
            Log.d("Prescription name:", "$prescname")


            binding.hospitalsList.setOnItemClickListener { parent, view, position, id ->
                val element = parent.getItemAtPosition(position)
                Log.d("Item in hospitals", "$element")
                var hospital_id = hosp[position]._id
                var prescription_id = prescid
                var hospital_name=hosp[position].name

                binding.sharebutton.setOnClickListener {
                    viewmodel.onShareButtonClick(hospital_name,hospital_id,prescription_id.toString())


                }


            }



            binding.backToPrescriptions.setOnClickListener {
                findNavController().navigate(R.id.action_prescriptionsShareFragment_to_prescriptionsFragment)
            }


        })


    }

    override fun onSuccessPrescriptionShare(hospitalName: String) {
       Log.d("In success","In onsuccssPrescriptionShare")
        toast("You successfully shared your prescription with $hospitalName", activity)
        //Toast.makeText(activity, "You successfully shared your prescription with $hospitalName", Toast.LENGTH_SHORT).show()
    }

    override fun onFailurePrescriptionShare(){
        toast("You have already shared your prescription with this hospital",activity)
    }



}