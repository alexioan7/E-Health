package com.alexandros.e_health.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentDiagnosesBinding
import com.alexandros.e_health.viewmodels.DiagnosesViewModel

class DiagnosesFragment : Fragment(R.layout.fragment_diagnoses) {

    private lateinit var binding: FragmentDiagnosesBinding
    private lateinit var viewmodel: DiagnosesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiagnosesBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(DiagnosesViewModel::class.java)

        binding.diagnosesviewmodel = viewmodel

    }

}