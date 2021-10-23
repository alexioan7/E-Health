package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandros.e_health.R
import com.alexandros.e_health.adapters.PrescriptionsAdapter
import com.alexandros.e_health.api.responseModel.PrescriptionDetails
import com.alexandros.e_health.databinding.FragmentPrescriptionsBinding
import com.alexandros.e_health.viewmodels.PrescriptionsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PrescriptionsFragment : Fragment(R.layout.fragment_prescriptions) {

    private lateinit var viewmodel: PrescriptionsViewModel
    private lateinit var binding: FragmentPrescriptionsBinding


    private lateinit var presc: List<PrescriptionDetails>

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrescriptionsBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(PrescriptionsViewModel::class.java)


        viewmodel.requestUserPrescriptions()

        viewmodel.getUserPrescriptionsFromRepo().observe(requireActivity(), {
            Log.d("PRESCRIPTIONS!!", it.data.prescriptions.toString())
            val prescriptions = it.data.prescriptions

            presc = prescriptions
            initRecyclerView()
//            prescriptions.forEach {it2 ->
//                presc.add(PrescriptionDetails(it2.dispensed, it2.createdAt, it2._id,it2.department,it2.user,it2.doctor,it2.medicine,it2.description,it2._v,it2.active,it2.id))
//                initRecyclerView()
//            }
            val adapter2= PrescriptionsAdapter(presc,lifecycleScope)
            binding.recyclerviewPrescriptions.adapter=adapter2

            adapter2.shareClicks.onEach {
                //toast("Test for click channel",requireActivity())
                val bundle= bundleOf("prescriptionID" to it._id)


                findNavController().navigate(R.id.action_prescriptionsFragment_to_prescriptionsShareFragment,bundle)

            }.launchIn(lifecycleScope)
        })

    }



    private fun initRecyclerView() {
        binding.recyclerviewPrescriptions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PrescriptionsAdapter(presc,lifecycleScope)
        }
    }
}