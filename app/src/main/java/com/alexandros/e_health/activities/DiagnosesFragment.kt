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
import com.alexandros.e_health.adapters.DiagnosesAdapter
import com.alexandros.e_health.api.responseModel.DiagnosisDetails
import com.alexandros.e_health.databinding.FragmentDiagnosesBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.DiagnosesViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

class DiagnosesFragment : Fragment(R.layout.fragment_diagnoses), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentDiagnosesBinding
    private lateinit var viewmodel: DiagnosesViewModel

    private lateinit var diagnoses : List<DiagnosisDetails>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiagnosesBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(DiagnosesViewModel::class.java)

        binding.diagnosesviewmodel = viewmodel
        viewmodel.requestUserDiagnoses()

        viewmodel.getUserDiagnosesFromRepo().observe(requireActivity(), {
            Log.d("DIAGNOSES!!",it.data.diagnoses.toString())
            val diagn = it.data.diagnoses
            diagnoses = diagn
            initRecyclerView()

            val adapter2= DiagnosesAdapter(diagnoses,lifecycleScope)
            binding.recyclerviewDiagnoses.adapter=adapter2

            adapter2.shareClicks.onEach {
                //toast("Test for click channel",requireActivity())
                val bundle= bundleOf("diagnosisID" to it._id)

                findNavController().navigate(R.id.action_diagnosesFragment_to_diagnosesShareFragment,bundle)

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

    private fun initRecyclerView(){
        binding.recyclerviewDiagnoses.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = DiagnosesAdapter(diagnoses,lifecycleScope)
        }
    }

    private fun updateAdapter(){
        binding.recyclerviewDiagnoses.apply {
            adapter = DiagnosesAdapter(diagnoses,lifecycleScope)
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun clearFilter(){
        binding.buttonClear.visibility = View.INVISIBLE
        binding.TextViewFilter.setText("")

        viewmodel.requestUserDiagnoses()
        viewmodel.getUserDiagnosesFromRepo().observe(requireActivity(), {
            Log.d("DIAGNOSES!!",it.data.diagnoses.toString())
            val diagn = it.data.diagnoses
            diagnoses = diagn
            updateAdapter()

            val adapter2= DiagnosesAdapter(diagnoses,lifecycleScope)
            binding.recyclerviewDiagnoses.adapter=adapter2

            adapter2.shareClicks.onEach {
                //toast("Test for click channel",requireActivity())
                val bundle= bundleOf("diagnosisID" to it._id)

                findNavController().navigate(R.id.action_diagnosesFragment_to_diagnosesShareFragment,bundle)

            }.launchIn(lifecycleScope)
        })
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        binding.buttonClear.visibility = View.VISIBLE
        binding.TextViewFilter.setText(p3.toString() + "/" + (p2+1).toString() + "/" + p1.toString() )
        viewmodel.requestUserDiagnosesFromDate(p1.toString() + "-" + (p2+1).toString() + "-" + p3.toString())

        viewmodel.getUserDiagnosesFromRepo().observe(requireActivity(), {
            Log.d("DIAGNOSES!!",diagnoses.toString())
            val diagn = it.data.diagnoses
            diagnoses = diagn

            updateAdapter()

            val adapter2= DiagnosesAdapter(diagnoses,lifecycleScope)
            binding.recyclerviewDiagnoses.adapter=adapter2

            adapter2.shareClicks.onEach {
                val bundle= bundleOf("diagnosisID" to it._id)

                findNavController().navigate(R.id.action_diagnosesFragment_to_diagnosesShareFragment,bundle)

            }.launchIn(lifecycleScope)
        })
    }

}