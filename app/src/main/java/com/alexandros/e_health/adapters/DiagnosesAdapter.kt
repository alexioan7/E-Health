package com.alexandros.e_health.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.DiagnosisDetails
import com.alexandros.e_health.utils.MongoDateAdapter
import kotlinx.android.synthetic.main.diagnosis_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.format.DateTimeFormatter

class DiagnosesAdapter(
    private val diagnoses: List<DiagnosisDetails>,
    private val coroutineScope: CoroutineScope

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val shareClicksChannel = Channel<DiagnosisDetails>(Channel.RENDEZVOUS)
    val shareClicks: Flow<DiagnosisDetails> = shareClicksChannel.receiveAsFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiagnosesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.diagnosis_item, parent, false)
        ,shareClicksChannel,coroutineScope)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is DiagnosesViewHolder -> {
                holder.bind(diagnoses.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return diagnoses.size
    }

    class DiagnosesViewHolder constructor(
        itemView: View,
        private val shareClicksChannel:Channel<DiagnosisDetails>,
        private val coroutineScope: CoroutineScope
    ): RecyclerView.ViewHolder(itemView){

        fun bind(diagnosis: DiagnosisDetails){

            val  diagnosesDate = MongoDateAdapter(diagnosis.createdAt).dateToLocalZone()

            itemView.diagnosis.setText(diagnosesDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
            itemView.hospital_prefecture.setText(diagnosis.department.hospital.prefecture)
            itemView.hospital_name.setText(diagnosis.department.hospital.name)
            itemView.hospital_department.setText("Department: "+diagnosis.department.name)
            itemView.doctor_name.setText("Dr "+diagnosis.doctor.surname+" "+ diagnosis.doctor.name)
            itemView.description.setText(diagnosis.description)

            itemView.shareButton.setOnClickListener {
                shareClicksChannel.offer(diagnosis)
            }



        }
    }
}