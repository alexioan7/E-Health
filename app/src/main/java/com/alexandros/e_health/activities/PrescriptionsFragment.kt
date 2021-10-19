package com.alexandros.e_health.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandros.e_health.R
import com.alexandros.e_health.RecyclerViewDecorations.TopSpacingItemDecoration
import com.alexandros.e_health.api.responseModel.Doctor
import com.alexandros.e_health.api.responseModel.Hospital
import com.alexandros.e_health.api.responseModel.PrescriptionsUserResponse
import com.alexandros.e_health.databinding.FragmentPrescriptionsBinding
import com.alexandros.e_health.viewmodels.PrescriptionsAdapter
import com.alexandros.e_health.viewmodels.PrescriptionsViewModel
import kotlinx.android.synthetic.main.fragment_prescriptions.*

class PrescriptionsFragment : Fragment(R.layout.fragment_prescriptions) {

    private lateinit var viewmodel: PrescriptionsViewModel
    private lateinit var binding: FragmentPrescriptionsBinding

    private val presc: ArrayList<PrescriptionsUserResponse> = ArrayList<PrescriptionsUserResponse>()

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrescriptionsBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(PrescriptionsViewModel::class.java)


        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI

       // binding.prescriptionsviewmodel = viewmodel

        presc.add(PrescriptionsUserResponse(false,"21-02-2013","shhsuwhsw",
            Hospital("geia sas","blahbla","lorem ipsum","Thessaloniki",1),"user", Doctor("261267262","Mosxos","Pipis"),"depon","every day",false))

        presc.add(PrescriptionsUserResponse(false,"21-02-2013","shhsuwhsw",
            Hospital("geia sas","blahbla","lorem ipsum","Thessaloniki",1),"user", Doctor("261267262","Mosxos","Pipis"),"depon","every day",false))
        presc.add(PrescriptionsUserResponse(false,"21-02-2013","shhsuwhsw",
            Hospital("geia sas","blahbla","lorem ipsum","Thessaloniki",1),"user", Doctor("261267262","Mosxos","Pipis"),"depon","every day",false))
        presc.add(PrescriptionsUserResponse(false,"21-02-2013","shhsuwhsw",
            Hospital("geia sas","blahbla","lorem ipsum","Thessaloniki",1),"user", Doctor("261267262","Mosxos","Pipis"),"depon","every day",false))
        presc.add(PrescriptionsUserResponse(false,"21-02-2013","shhsuwhsw",
            Hospital("geia sas","blahbla","lorem ipsum","Thessaloniki",1),"user", Doctor("261267262","Mosxos","Pipis"),"depon","every day",false))



        viewmodel.requestUserPrescriptions()
        viewmodel.getUserPrescriptionsFromRepo()
        initRecyclerView()

    }

    private fun initRecyclerView(){
        recyclerview_prescriptions.apply {
            layoutManager = LinearLayoutManager(activity)
           val topSpacingItemDecoration = TopSpacingItemDecoration(32)
            addItemDecoration(topSpacingItemDecoration)
            adapter = PrescriptionsAdapter(presc)
        }
    }
}