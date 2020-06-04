package com.abhishek.covidtracer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.covidtracer.Model.Helplines
import com.abhishek.covidtracer.R

class HelpAdapter(val context: Context, val listItem:List<Helplines>)
    :RecyclerView.Adapter<HelpAdapter.HelplineViewHolder>() {
    class HelplineViewHolder(view: View):RecyclerView.ViewHolder(view){
        val locat:TextView = view.findViewById(R.id.locat)
        val helpnum:TextView = view.findViewById(R.id.helpnum)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelplineViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.helplines_cards,parent,false)
        return HelplineViewHolder(view)
    }
    override fun getItemCount(): Int {
        return listItem.size
    }
    override fun onBindViewHolder(holder: HelplineViewHolder, position: Int) {
        val info = listItem[position]
        holder.locat.text = info.location
        holder.helpnum.text = info.number
    }
}