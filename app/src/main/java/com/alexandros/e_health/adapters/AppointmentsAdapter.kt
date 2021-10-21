package com.alexandros.e_health.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandros.e_health.R
import com.alexandros.e_health.api.responseModel.Appointment
import kotlinx.android.synthetic.main.apointment_card_layout.view.*

class AppointmentsAdapterAdapter( private val appointments: List<Appointment>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppointmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.apointment_card_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AppointmentViewHolder -> {
                holder.bind(appointments.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    class AppointmentViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        fun bind(appointment: Appointment){
            itemView.deparmtent_slot_textView.setText(appointment.department.name)
            itemView.hospital_slot_textView.setText(appointment.department.hospital.name)
            itemView.perfecture_slot_textView.setText(appointment.department.hospital.prefecture)
            itemView.date_slot_textView.setText(appointment.date)
            itemView.time_slot_textView.setText("")
            if(appointment.active)
                itemView.status_slot_textView.setText("Future")
            else
                itemView.status_slot_textView.setText("Past")
        }
    }
}