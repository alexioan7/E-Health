package com.alexandros.e_health.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandros.e_health.R
import com.alexandros.e_health.RecyclerViewDecorations.TopSpacingItemDecoration
import com.alexandros.e_health.api.responseModel.*
import com.alexandros.e_health.databinding.FragmentPrescriptionsBinding
import com.alexandros.e_health.viewmodels.PrescriptionsAdapter
import com.alexandros.e_health.viewmodels.PrescriptionsViewModel
import kotlinx.android.synthetic.main.fragment_prescriptions.*

class PrescriptionsFragment : Fragment(R.layout.fragment_prescriptions) {

    private lateinit var viewmodel: PrescriptionsViewModel
    private lateinit var binding: FragmentPrescriptionsBinding

    private val presc = mutableListOf<PrescriptionDetails>()

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrescriptionsBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(PrescriptionsViewModel::class.java)


        //the loginviewmodel is the variable from the activity_main.xml (sth like object of type loginScreenViewmodel)
        //this will bind our data with the UI


        viewmodel.requestUserPrescriptions()

        viewmodel.getUserPrescriptionsFromRepo().observe(requireActivity(), {

            //putDataIntoTextViews(it)
            Log.d("PRESCRIPTIONS!!",it.data.prescriptions.toString())
            val prescriptions = it.data.prescriptions
            prescriptions.forEach {it2 ->
                presc.add(PrescriptionDetails(it2.dispensed, it2.createdAt, it2._id, it2.hospital, it2.user, it2.doctor, it2.medicine, it2.description, it2.active))

            }
        })

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