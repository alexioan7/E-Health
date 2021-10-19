package com.alexandros.e_health.viewmodels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.PrescriptionsUserResponse
import com.alexandros.e_health.databinding.RecyclerviewPrescriptionsBinding

class PrescriptionsAdapter (
    private val prescription: List<PrescriptionsUserResponse>

) : RecyclerView.Adapter<PrescriptionsAdapter.PrescriptionsViewHolder>(){

    override fun getItemCount()= prescription.size
    private lateinit var binding: RecyclerviewPrescriptionsBinding

    //creates the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        PrescriptionsViewHolder(

            DataBindingUtil.inflate<RecyclerviewPrescriptionsBinding>(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_prescriptions,
                parent,
                false
            )
        )

    //binds the data to the view holder
    override fun onBindViewHolder(holder: PrescriptionsViewHolder, position: Int) {
        holder.recyclerviewPrescriptionsBinding.prescriptions=prescription[position]
    }


    inner class PrescriptionsViewHolder(

        val recyclerviewPrescriptionsBinding: RecyclerviewPrescriptionsBinding

    ): RecyclerView.ViewHolder(recyclerviewPrescriptionsBinding.root)
}
