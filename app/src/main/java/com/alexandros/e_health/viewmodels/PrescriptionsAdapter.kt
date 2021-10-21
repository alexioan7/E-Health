package com.alexandros.e_health.viewmodels

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.PrescriptionDetails
import com.alexandros.e_health.databinding.RecyclerviewPrescriptionsBinding
import com.alexandros.e_health.utils.MongoDateAdapter
import java.time.format.DateTimeFormatter

class PrescriptionsAdapter (
    private val prescription: List<PrescriptionDetails>

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


        fun bind(presc:PrescriptionDetails){

            val prescDate = MongoDateAdapter(presc.createdAt).dateToLocalZone()

            recyclerviewPrescriptionsBinding.medicine.setText(presc.medicine)
            recyclerviewPrescriptionsBinding.date.setText(prescDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
            recyclerviewPrescriptionsBinding.hospitalPrefecture.setText(presc.department.hospital.prefecture)
            recyclerviewPrescriptionsBinding.hospitalName.setText(presc.department.hospital.name)
            recyclerviewPrescriptionsBinding.hospitalDepartment.setText(presc.department.name)
            recyclerviewPrescriptionsBinding.doctorSurname.setText(presc.doctor.surname)
            recyclerviewPrescriptionsBinding.doctorName.setText(presc.doctor.name)
            recyclerviewPrescriptionsBinding.description.setText(presc.description)
            if(presc.dispensed){
                recyclerviewPrescriptionsBinding.dispensed.visibility= View.VISIBLE
            }


        }

    }
}
