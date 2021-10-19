package com.alexandros.e_health.viewmodels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.PrescriptionsUserResponse
import com.alexandros.e_health.databinding.RecyclerviewPrescriptionsBinding

class PrescriptionsAdapter (
    private val prescription: ArrayList<PrescriptionsUserResponse>

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
        holder.bind(prescription.get(position))
    }


    inner class PrescriptionsViewHolder(

        val recyclerviewPrescriptionsBinding: RecyclerviewPrescriptionsBinding

    ): RecyclerView.ViewHolder(recyclerviewPrescriptionsBinding.root){

        fun bind(presc:PrescriptionsUserResponse){

            recyclerviewPrescriptionsBinding.medicine.setText(presc.medicine)
            recyclerviewPrescriptionsBinding.date.setText(presc.createdAt)
            recyclerviewPrescriptionsBinding.hospitalPrefecture.setText(presc.hospital.prefecture)
            recyclerviewPrescriptionsBinding.hospitalName.setText(presc.hospital.name)
            recyclerviewPrescriptionsBinding.hospitalDepartment.setText(presc.hospital.department)
            recyclerviewPrescriptionsBinding.doctorSurname.setText(presc.doctor.surname)
            recyclerviewPrescriptionsBinding.doctorName.setText(presc.doctor.name)
            recyclerviewPrescriptionsBinding.description.setText(presc.description)

        }

    }
}
