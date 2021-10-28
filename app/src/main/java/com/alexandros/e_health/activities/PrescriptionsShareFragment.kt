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
import okhttp3.internal.wait
import kotlin.concurrent.thread

class PrescriptionsShareFragment: Fragment(R.layout.fragment_prescriptions_share) ,AuthFunctionsSharePrescriptions, AuthFunctionsHospitalByPresc {

    private lateinit var binding: FragmentPrescriptionsShareBinding
    private lateinit var viewmodel: PrescriptionsShareViewModel
    private val hosp = mutableListOf<HospitalsDetails>()
    private val arrayOfSharedPrescriptions = mutableListOf<HospitalsDetails>()
    private lateinit var arrayAdapter: ArrayAdapter<*>
    private lateinit var arrayAdapterShared: ArrayAdapter<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hosp.clear()
        arrayOfSharedPrescriptions.clear()

        arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_checked, hosp)
        arrayAdapterShared = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, arrayOfSharedPrescriptions)

        arrayAdapter.notifyDataSetChanged()
        arrayAdapterShared.notifyDataSetChanged()

        val prescid = arguments?.getString("prescriptionID")
        Log.d("Prescription id:", "$prescid")
        val prescname = arguments?.getString("prescriptionName")
        Log.d("Prescription name:", "$prescname")

        binding = FragmentPrescriptionsShareBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(PrescriptionsShareViewModel::class.java)

        var myHospitalList = binding.hospitalsList
        myHospitalList.adapter = arrayAdapter
        var myHospitalByPrescList = binding.hospitalsByPrescList
        myHospitalByPrescList.adapter = arrayAdapterShared

        if (prescid != null) {
            viewmodel.requestHospitalsBySharedPrescriptions(prescid)
        }

        viewmodel.authListenerpresc = this
        viewmodel.authHospitalListenerpresc = this

        viewmodel.getHospitalsFromRepo().observe(viewLifecycleOwner, {
            var temp = mutableListOf<HospitalsDetails>()
            temp.addAll(it.data.hospitals)
            if(arrayOfSharedPrescriptions.size != 0) {
                temp.removeAll(arrayOfSharedPrescriptions)
            }

            hosp.clear()
            arrayAdapter.notifyDataSetChanged()
            hosp.addAll(temp)
            Log.d("HOSPITALS", it.data.hospitals.toString())

            arrayAdapter.notifyDataSetChanged()

        })

        viewmodel.getHospitalsByPresc().observe(viewLifecycleOwner, {

            viewmodel.requestHospitals()
            arrayOfSharedPrescriptions.clear()
            arrayAdapterShared.notifyDataSetChanged()
            arrayOfSharedPrescriptions.addAll(it.data.hospitals)
            arrayAdapterShared.notifyDataSetChanged()

            Log.d("Prescription id", it.data.hospitals.toString())
            Log.d("arrayd of shared prescriptions",arrayOfSharedPrescriptions.toString())
        })

        viewmodel.getErrorFromRepo().observe(viewLifecycleOwner, {
            viewmodel.requestHospitals()
        })


        binding.sharebutton.setOnClickListener {
            var position = binding.hospitalsList.checkedItemPosition
            if(hosp.size != 0 && position!= -1) {
                var hospital_id = hosp[position]._id
                var prescription_id = prescid
                var hospital_name = hosp[position].name
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

    }

    override fun onSuccessPrescriptionShare(hospitalName: String) {
        Log.d("In success", "In onsuccssPrescriptionShare")
        toast("You successfully shared your prescription with $hospitalName", activity)
        for(hospital in hosp) {
            if(hospitalName.contains(hospital.name)) {
                arrayOfSharedPrescriptions.add(hospital)
                hosp.remove(hospital)
                break
            }
        }
        arrayAdapter.notifyDataSetChanged()
        arrayAdapterShared.notifyDataSetChanged()

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
        arrayAdapter.notifyDataSetChanged()
        arrayAdapterShared.notifyDataSetChanged()
    }


}