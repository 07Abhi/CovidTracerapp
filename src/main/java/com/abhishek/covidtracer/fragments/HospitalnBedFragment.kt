package com.abhishek.covidtracer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import  android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.covidtracer.Model.Hospitals
import com.abhishek.covidtracer.R
import com.abhishek.covidtracer.adapter.HospitalAdapter
import com.abhishek.covidtracer.connectionamanager.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.lang.Exception


class HospitalnBedFragment : Fragment() {
    lateinit var hospitalrecyclerView:RecyclerView
    lateinit var hospitalprogressLayout:RelativeLayout
    lateinit var hospitalprogressBar:ProgressBar
    lateinit var hospitalAdapter:HospitalAdapter
    lateinit var layoutmanager:RecyclerView.LayoutManager
    val arraydata = arrayListOf<Hospitals>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_hospitaln_bed, container, false)
        hospitalrecyclerView = view.findViewById(R.id.hospitalrecyclerView)
        hospitalprogressLayout = view.findViewById(R.id.hospitalprogressLayout)
        hospitalprogressBar = view.findViewById(R.id.hospitalprogressBar)
        layoutmanager = LinearLayoutManager(activity as Context)
        hospitalprogressLayout.visibility = View.VISIBLE
        val queue2 = Volley.newRequestQueue(context)
        val url = "https://api.rootnet.in/covid19-in/hospitals/beds"
        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonreq2 = JsonObjectRequest(Request.Method.GET,url,null
                    ,Response.Listener {
                try{
                    val success = it.getBoolean("success")
                    if(success){
                        hospitalprogressLayout.visibility = View.GONE
                        val jsonobj = it.getJSONObject("data")
                        val arr = jsonobj.getJSONArray("regional")

                        for(k in 0 until arr.length()){
                            val data = arr.getJSONObject(k)
                            val host = Hospitals(
                                    data.getString("state"),
                                    data.getString("ruralHospitals"),
                                    data.getString("ruralBeds"),
                                    data.getString("urbanHospitals"),
                                    data.getString("urbanBeds"),
                                    data.getString("totalHospitals"),
                                    data.getString("totalBeds")
                            )
                            arraydata.add(host)
                            hospitalAdapter = HospitalAdapter(activity as Context,arraydata)
                            hospitalrecyclerView.adapter = hospitalAdapter
                            hospitalrecyclerView.layoutManager = layoutmanager
                        }

                    }else{
                        Toast.makeText(context,"Unsuccessful request!!",Toast.LENGTH_SHORT).show()
                    }

                }catch (e:Exception){
                    Toast.makeText(context,"Error in Context!!",Toast.LENGTH_SHORT).show()
                }
            },Response.ErrorListener {
                Toast.makeText(context,"Server Down!!",Toast.LENGTH_SHORT).show()
            })
            queue2.add(jsonreq2)

        }else{
            Toast.makeText(context,"No Internet Available",Toast.LENGTH_SHORT).show()
        }
        return view
    }
}