package com.alexandros.e_health.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
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
import java.util.*

class PrescriptionsFragment : Fragment(R.layout.fragment_prescriptions), DatePickerDialog.OnDateSetListener {

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

        viewmodel.getUserPrescriptionsFromRepo().observe(viewLifecycleOwner, {
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
                //val bundle= bundleOf("prescriptionID" to it._id)

                val bundle = Bundle()
                bundle.putString("prescriptionID",it._id)
                bundle.putString("prescriptionName",it.medicine)

                findNavController().navigate(R.id.action_prescriptionsFragment_to_prescriptionsShareFragment,bundle)

            }.launchIn(lifecycleScope)
        })

        binding.TextViewFilter.setOnClickListener {
            showDatePickerDialog()
        }

        binding.buttonClear.setOnClickListener {
            clearFilter()
        }

    }

    private fun showDatePickerDialog(){
        var datePickerDialog: DatePickerDialog = DatePickerDialog(
            requireActivity(),
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }

    private fun updateAdapter(){
        binding.recyclerviewPrescriptions.apply {
            adapter = PrescriptionsAdapter(presc,lifecycleScope)
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun clearFilter(){
        binding.textViewErrorMessage.visibility = View.INVISIBLE
        binding.buttonClear.visibility = View.INVISIBLE
        binding.TextViewFilter.setText("")

        viewmodel.requestUserPrescriptions()
        viewmodel.getUserPrescriptionsFromRepo().observe(requireActivity(), {
            Log.d("DIAGNOSES!!",it.data.prescriptions.toString())
            val prescriptions = it.data.prescriptions
            presc = prescriptions
            updateAdapter()

            val adapter2= PrescriptionsAdapter(presc,lifecycleScope)
            binding.recyclerviewPrescriptions.adapter=adapter2

            adapter2.shareClicks.onEach {
                //toast("Test for click channel",requireActivity())
                val bundle= bundleOf("diagnosisID" to it._id)

                findNavController().navigate(R.id.action_diagnosesFragment_to_diagnosesShareFragment,bundle)

            }.launchIn(lifecycleScope)
        })
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        binding.textViewErrorMessage.visibility = View.INVISIBLE
        binding.buttonClear.visibility = View.VISIBLE
        binding.TextViewFilter.setText(p3.toString() + "/" + (p2+1).toString() + "/" + p1.toString() )
        viewmodel.requestUserPrescriptionsFromDate(p1.toString() + "-" + (p2+1).toString() + "-" + p3.toString())

        viewmodel.getUserPrescriptionsFromRepo().observe(requireActivity(), {
            Log.d("PRESCRIPTIONS",presc.toString())
            val prescriptions = it.data.prescriptions
            presc = prescriptions

            updateAdapter()

            val adapter2= PrescriptionsAdapter(presc,lifecycleScope)
            binding.recyclerviewPrescriptions.adapter=adapter2

            adapter2.shareClicks.onEach {
                val bundle= bundleOf("diagnosisID" to it._id)

                findNavController().navigate(R.id.action_diagnosesFragment_to_diagnosesShareFragment,bundle)

            }.launchIn(lifecycleScope)
        })

        viewmodel.getFailureMessageFromPrescriptions().observe(requireActivity(), {
            presc = emptyList<PrescriptionDetails>()
            binding.textViewErrorMessage.visibility = View.VISIBLE
            binding.textViewErrorMessage.text = it.toString()
            updateAdapter()
        })
    }



    private fun initRecyclerView() {
        binding.recyclerviewPrescriptions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PrescriptionsAdapter(presc,lifecycleScope)
        }
    }
}