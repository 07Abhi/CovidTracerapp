package com.abhishek.covidtracer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.covidtracer.Model.StateModel
import com.abhishek.covidtracer.R
import org.w3c.dom.Text

class StateAdapter(val context: Context,val itemList:List<StateModel>)
    :RecyclerView.Adapter<StateAdapter.StateViewHolder>() {
    class StateViewHolder(view: View):RecyclerView.ViewHolder(view){
        val loctag:TextView = view.findViewById(R.id.loctag)
        val confirmdata:TextView = view.findViewById(R.id.confirmdata)
        val foreigndata:TextView = view.findViewById(R.id.foreigndata)
        val deathcount:TextView = view.findViewById(R.id.deathcount)
        val discharge:TextView = view.findViewById(R.id.discharge)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.states_card_views,parent,false)
        return StateViewHolder(view)
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val data = itemList[position]
        holder.loctag.text = data.state
        holder.confirmdata.text = data.confirmcase
        holder.foreigndata.text = data.foreigncases
        holder.deathcount.text = data.death
        holder.discharge.text = data.discharge
    }
}