package com.abhishek.covidtracer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.covidtracer.Model.Hospitals
import com.abhishek.covidtracer.R

class HospitalAdapter(val context: Context, val itemList:List<Hospitals>)
    :RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {
    class HospitalViewHolder(view: View):RecyclerView.ViewHolder(view){
        val location:TextView = view.findViewById(R.id.location)
        val ruralHospitals:TextView = view.findViewById(R.id.ruralHospitals)
        val ruralBeds:TextView = view.findViewById(R.id.ruralBeds)
        val urbanHospitals:TextView = view.findViewById(R.id.urbanHospitals)
        val urbanBeds:TextView = view.findViewById(R.id.urbanBeds)
        val totalHospitals:TextView = view.findViewById(R.id.totalHospitals)
        val totalBeds:TextView = view.findViewById(R.id.totalBeds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hospitals_beds_cards,parent,false)
        return HospitalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val info = itemList[position]
        holder.location.text = info.location
        holder.ruralHospitals.text = info.ruralHospital
        holder.ruralBeds.text = info.ruralBeds
        holder.urbanHospitals.text = info.urbanHospital
        holder.urbanBeds.text = info.urbanBed
        holder.totalHospitals.text = info.totalHospital
        holder.totalBeds.text = info.totalBeds
    }
}