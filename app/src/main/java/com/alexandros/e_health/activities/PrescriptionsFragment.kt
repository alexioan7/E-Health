package com.alexandros.e_health.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentPrescriptionsBinding
import com.alexandros.e_health.viewmodels.PrescriptionsAdapter
import com.alexandros.e_health.viewmodels.PrescriptionsViewModel
import kotlinx.android.synthetic.main.fragment_prescriptions.*

class PrescriptionsFragment : Fragment(R.layout.fragment_prescriptions) {

    private lateinit var viewmodel: PrescriptionsViewModel
    private lateinit var binding: FragmentPrescriptionsBinding

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrescriptionsBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(PrescriptionsViewModel::class.java)
        binding.apply {
            recyclerview_prescriptions.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.HORIZONTAL))
        }

        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI

        binding.prescriptionsviewmodel = viewmodel

        viewmodel.requestUserPrescriptions()
        viewmodel.getUserPrescriptionsFromRepo()
        viewmodel.prescriptions.observe(viewLifecycleOwner, Observer {
                prescriptions -> recyclerview_prescriptions.also {
                it.layoutManager=LinearLayoutManager(requireContext())
                it.setHasFixedSize(false)
                it.adapter=PrescriptionsAdapter(prescriptions)


        } })
    }
}