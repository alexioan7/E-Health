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
import com.alexandros.e_health.databinding.FragmentPrescriptionsShareBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.utils.toast
import com.alexandros.e_health.viewmodels.AuthFunctionsHospitalByPresc
import com.alexandros.e_health.viewmodels.AuthFunctionsSharePrescriptions
import com.alexandros.e_health.viewmodels.PrescriptionsShareViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory

class PrescriptionsShareFragment: Fragment(R.layout.fragment_prescriptions_share) ,AuthFunctionsSharePrescriptions, AuthFunctionsHospitalByPresc {

    private lateinit var binding: FragmentPrescriptionsShareBinding
    private lateinit var viewmodel: PrescriptionsShareViewModel
    private val hosp = mutableListOf<HospitalsDetails>()
    private lateinit var arrayAdapterShared: ArrayAdapter<*>
    private val arrayOfSharedPrescriptions = mutableListOf<HospitalsDetails>()
    private lateinit var arrayAdapter: ArrayAdapter<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prescid = arguments?.getString("prescriptionID")
        Log.d("Prescription id:", "$prescid")
        val prescname = arguments?.getString("prescriptionName")
        Log.d("Prescription name:", "$prescname")
        binding = FragmentPrescriptionsShareBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(
            PrescriptionsShareViewModel::class.java
        )


        viewmodel.authListenerpresc = this
        viewmodel.authHospitalListenerpresc = this

        if (prescid != null) {
            viewmodel.requestHospitalsBySharedPrescriptions(prescid)
        }
        viewmodel.getHospitalsByPresc().observe(requireActivity(), {

            arrayOfSharedPrescriptions.addAll(it.data.hospitals)

            Log.d("Prescription id", it.data.hospitals.toString())

            var myHospitalByPrescList = binding.hospitalsByPrescList
            try {
                arrayAdapterShared = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_list_item_1,
                    arrayOfSharedPrescriptions
                )
                myHospitalByPrescList.adapter = arrayAdapterShared
            } catch (e: Exception) {
                Log.d("ArrayAdapter", e.toString())
            }

            Log.d("arrayd of shared prescriptions",arrayOfSharedPrescriptions.toString())
        })

        viewmodel.requestHospitals()
        viewmodel.getHospitalsFromRepo().observe(requireActivity(), {

            Log.d("HOSPITALS", it.data.hospitals.toString())

            hosp.addAll(it.data.hospitals)
            hosp.removeAll(arrayOfSharedPrescriptions)
//            Log.d("hosp1", hosp[0]._id)

            var myHospitalList = binding.hospitalsList

            try {

                arrayAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_list_item_checked,
                    hosp
                )
                //


                myHospitalList.adapter = arrayAdapter

            } catch (e: Exception) {
                Log.d("ArrayAdapter", e.toString())
            }

            binding.hospitalsList.setOnItemClickListener { parent, view, position, id ->
                val element = parent.getItemAtPosition(position)
                Log.d("Item in hospitals", "$element")
                var hospital_id = hosp[position]._id
                var prescription_id = prescid
                var hospital_name = hosp[position].name
                Log.d("Hospital id","$hospital_id")
                Log.d("Prescription id","$prescid")

                binding.sharebutton.setOnClickListener {
                    viewmodel.onShareButtonClick(
                        hospital_name,
                        hospital_id,
                        prescription_id.toString()
                    )
                }
            }
            binding.backToPrescriptions.setOnClickListener {

                findNavController().navigate(R.id.action_prescriptionsShareFragment_to_prescriptionsFragment)
            }
        })

    }

    override fun onSuccessPrescriptionShare(hospitalName: String) {
        Log.d("In success", "In onsuccssPrescriptionShare")
        toast("You successfully shared your prescription with $hospitalName", activity)
        arrayAdapterShared.notifyDataSetChanged()
        //Toast.makeText(activity, "You successfully shared your prescription with $hospitalName", Toast.LENGTH_SHORT).show()
    }

    override fun onFailurePrescriptionShare(failuremessage: String) {
        toast("$failuremessage", activity)
    }

    override fun onSuccessHospitalsByPresc(responseList: MutableLiveData<HospitalsUserResponse>) {

    }


    override fun onResume() {
        super.onResume()
        hosp.clear()
        arrayOfSharedPrescriptions.clear()
    }




}