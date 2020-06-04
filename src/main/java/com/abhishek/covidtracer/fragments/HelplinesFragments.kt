package com.abhishek.covidtracer.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.covidtracer.Model.Helplines
import com.abhishek.covidtracer.R
import com.abhishek.covidtracer.activity.NationalActivity
import com.abhishek.covidtracer.adapter.HelpAdapter
import com.abhishek.covidtracer.connectionamanager.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.lang.Exception


class HelplinesFragments : Fragment() {
    lateinit var btnational:Button
    lateinit var helprecyclerView:RecyclerView
    lateinit var helpprogressLayout:RelativeLayout
    lateinit var helprogressBar:ProgressBar
    lateinit var helplineAdpter:HelpAdapter
    var arrdata = arrayListOf<Helplines>()
    lateinit var layoutmanager:RecyclerView.LayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=  inflater.inflate(R.layout.fragment_helplines_fragments, container, false)
        btnational = view.findViewById(R.id.btnational)
        helprecyclerView = view.findViewById(R.id.helprecyclerView)
        helpprogressLayout = view.findViewById(R.id.helpprogressLayout)
        helprogressBar = view.findViewById(R.id.helprogressBar)
        layoutmanager = LinearLayoutManager(activity as Context)
        helpprogressLayout.visibility = View.VISIBLE
        btnational.setOnClickListener {
            Toast.makeText(context,"National Services!!",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context,NationalActivity::class.java))
        }

        val qu = Volley.newRequestQueue(context)
        val url = "https://api.rootnet.in/covid19-in/contacts"
        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonret = JsonObjectRequest(Request.Method.GET,url,null,
                    Response.Listener {
                        try {
                            val success = it.getBoolean("success")
                            if (success) {
                                helpprogressLayout.visibility = View.GONE
                                val jsonobj = it.getJSONObject("data")
                                val jsonobj1  =jsonobj.getJSONObject("contacts")
                                val arr1 = jsonobj1.getJSONArray("regional")
                                for (i in 0 until arr1.length()) {
                                    val data = arr1.getJSONObject(i)
                                    val help = Helplines(
                                            data.getString("loc"),
                                            data.getString("number")
                                    )
                                    arrdata.add(help)
                                    helplineAdpter = HelpAdapter(activity as Context,arrdata)
                                    helprecyclerView.adapter = helplineAdpter
                                    helprecyclerView.layoutManager = layoutmanager
                                }
                            } else {
                                Toast.makeText(context, "Unsuccessful Request!!", Toast.LENGTH_SHORT).show()
                            }
                        }catch (e:Exception){
                            Toast.makeText(context,"Error In Context!!",Toast.LENGTH_SHORT).show()
                        }
                    },Response.ErrorListener {
                Toast.makeText(context,"Server Error!!",Toast.LENGTH_SHORT).show()
            })
            qu.add(jsonret)

        }else{
            Toast.makeText(context,"No Internet!!",Toast.LENGTH_SHORT).show()
        }
        return view
    }


}