package com.alexandros.e_health.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.PrescriptionDetails
import com.alexandros.e_health.databinding.PrescriptionItemBinding
import com.alexandros.e_health.utils.MongoDateAdapter
import kotlinx.android.synthetic.main.prescription_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.format.DateTimeFormatter

class PrescriptionsAdapter(
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
            recyclerviewPrescriptionsBinding.hospitalName.setText(presc.department.hospital.name+","+presc.department.name)
            recyclerviewPrescriptionsBinding.hospitalDepartment.setText("Department: "+presc.department.name)
            //recyclerviewPrescriptionsBinding.doctorSurname.setText("Dr "+presc.doctor.surname)
            recyclerviewPrescriptionsBinding.doctorName.setText("Dr "+presc.doctor.surname+" "+presc.doctor.name)
            recyclerviewPrescriptionsBinding.description.setText(presc.description)

            if(!presc.dispensed && !presc.active){
                recyclerviewPrescriptionsBinding.dispensed.setTextColor(Color.RED)
                recyclerviewPrescriptionsBinding.dispensed.setText("Expired")
            }
            if(presc.dispensed){
                recyclerviewPrescriptionsBinding.dispensed.setTextColor(Color.BLUE)
                recyclerviewPrescriptionsBinding.dispensed.dispensed.setText("Dispensed")
            }
            recyclerviewPrescriptionsBinding.shareButton.setOnClickListener {
                shareClicksChannel.offer(presc)
            }

        }

    }
}
