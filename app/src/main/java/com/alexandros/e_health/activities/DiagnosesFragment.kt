package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandros.e_health.R
import com.alexandros.e_health.adapters.DiagnosesAdapter
import com.alexandros.e_health.api.responseModel.DiagnosisDetails
import com.alexandros.e_health.api.responseModel.PrescriptionDetails
import com.alexandros.e_health.databinding.FragmentDiagnosesBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.DiagnosesViewModel
import com.alexandros.e_health.viewmodels.PrescriptionsAdapter
import com.alexandros.e_health.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_diagnoses.*
import kotlinx.android.synthetic.main.fragment_prescriptions.*

class DiagnosesFragment : Fragment(R.layout.fragment_diagnoses) {

    private lateinit var binding: FragmentDiagnosesBinding
    private lateinit var viewmodel: DiagnosesViewModel

    private val diagnoses = mutableListOf<DiagnosisDetails>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiagnosesBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity(), ViewModelFactory(AuthRepository)).get(DiagnosesViewModel::class.java)

        binding.diagnosesviewmodel = viewmodel
        viewmodel.requestUserDiagnoses()

        viewmodel.getUserDiagnosesFromRepo().observe(requireActivity(), {
            Log.d("DIAGNOSES!!",it.data.diagnoses.toString())
            val diagn = it.data.diagnoses
            diagn.forEach {it2 ->
                diagnoses.add(DiagnosisDetails(it2.createdAt, it2._id, it2.department, it2.user, it2.doctor, it2.description))
                initRecyclerView()
            }
        })
        initRecyclerView()

    }

    private fun initRecyclerView(){
        recyclerview_diagnoses.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = DiagnosesAdapter(diagnoses)
        }
    }
}