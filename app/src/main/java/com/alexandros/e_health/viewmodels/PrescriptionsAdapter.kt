package com.alexandros.e_health.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.PrescriptionDetails
import com.alexandros.e_health.databinding.PrescriptionItemBinding
import com.alexandros.e_health.utils.MongoDateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.format.DateTimeFormatter

class PrescriptionsAdapter (
    private val prescriptionDetails: List<PrescriptionDetails>,
    private val coroutineScope: CoroutineScope

) : RecyclerView.Adapter<PrescriptionsAdapter.PrescriptionsViewHolder>(){

    override fun getItemCount()= prescriptionDetails.size
    private lateinit var binding: PrescriptionItemBinding

    private val shareClicksChannel = Channel<PrescriptionDetails>(RENDEZVOUS)
    val shareClicks: Flow<PrescriptionDetails> = shareClicksChannel.receiveAsFlow()


    //creates the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        PrescriptionsViewHolder(

            DataBindingUtil.inflate<PrescriptionItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.prescription_item,
                parent,
                false
            )
        ,shareClicksChannel,coroutineScope
        )

    //binds the data to the view holder
    override fun onBindViewHolder(holder: PrescriptionsViewHolder, position: Int) {
        holder.bind(prescriptionDetails.get(position))
    }


    inner class PrescriptionsViewHolder(

        val recyclerviewPrescriptionsBinding: PrescriptionItemBinding,
        private val shareClicksChannel:Channel<PrescriptionDetails>,
        private val coroutineScope: CoroutineScope


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
            recyclerviewPrescriptionsBinding.shareButton.setOnClickListener {
                shareClicksChannel.offer(presc)
            }

        }

    }
}
