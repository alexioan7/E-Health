package com.alexandros.e_health.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.DiagnosisDetails
import kotlinx.android.synthetic.main.layout_diagnosis.view.*

class DiagnosesAdapter( private val diagnoses: List<DiagnosisDetails>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiagnosesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_diagnosis, parent, false)
        )
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
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        fun bind(diagnosis: DiagnosisDetails){
            itemView.diagnosis.setText(diagnosis.createdAt)
            itemView.date.setText(diagnosis.createdAt)
            itemView.hospital_prefecture.setText(diagnosis.department.hospital.prefecture)
            itemView.hospital_name.setText(diagnosis.department.hospital.name)
            itemView.hospital_department.setText(diagnosis.department.name)
            itemView.doctor_surname.setText(diagnosis.doctor.surname)
            itemView.doctor_name.setText(diagnosis.doctor.name)
            itemView.description.setText(diagnosis.description)



        }
    }
}