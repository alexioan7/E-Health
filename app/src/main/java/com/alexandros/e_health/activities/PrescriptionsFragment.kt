package com.alexandros.e_health.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.databinding.FragmentPersonalinfoBinding
import com.alexandros.e_health.databinding.FragmentPrescriptionsBinding
import com.alexandros.e_health.viewmodels.PersonalinfoViewModel
import com.alexandros.e_health.viewmodels.PrescriptionsViewModel

class PrescriptionsFragment : Fragment() {

    private lateinit var viewmodel: PrescriptionsViewModel
    private lateinit var binding: FragmentPrescriptionsBinding

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrescriptionsBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(PrescriptionsViewModel::class.java)

        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI

//        binding.viewmodel = viewmodel
    }
}