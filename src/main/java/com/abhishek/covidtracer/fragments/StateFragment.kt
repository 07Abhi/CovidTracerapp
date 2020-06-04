package com.abhishek.covidtracer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.covidtracer.Model.StateModel
import com.abhishek.covidtracer.R
import com.abhishek.covidtracer.adapter.StateAdapter
import com.abhishek.covidtracer.connectionamanager.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.lang.Exception


class StateFragment : Fragment() {
    lateinit var staterecyclerView:RecyclerView
    lateinit var stateprogressLayout:RelativeLayout
    lateinit var stateprogressBar:ProgressBar
    lateinit var stateAdpater:StateAdapter
    lateinit var layoutmanager:RecyclerView.LayoutManager
    val arraydata = arrayListOf<StateModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_state, container, false)
        staterecyclerView = view.findViewById(R.id.staterecyclerView)
        stateprogressLayout = view.findViewById(R.id.stateprogressLayout)
        stateprogressBar = view.findViewById(R.id.stateprogressBar)
        layoutmanager = LinearLayoutManager(activity)
        stateprogressLayout.visibility = View.VISIBLE
        val queue1 = Volley.newRequestQueue(activity as Context)
        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonreq1 = JsonObjectRequest(Request.Method.GET,url,null,
                    Response.Listener {
                        try{
                            stateprogressLayout.visibility = View.GONE
                            val success = it.getBoolean("success")
                            if(success){
                                val dataObject = it.getJSONObject("data")
                                val datarray = dataObject.getJSONArray("regional")
                                for (i in 0 until datarray.length()){
                                    val info = datarray.getJSONObject(i)
                                    val model = StateModel(
                                            info.getString("loc"),
                                            info.getString("deaths"),
                                            info.getString("discharged"),
                                            info.getString("confirmedCasesIndian"),
                                            info.getString("confirmedCasesForeign")
                                    )
                                    arraydata.add(model)
                                    stateAdpater = StateAdapter(activity as Context,arraydata)
                                    staterecyclerView.adapter = stateAdpater
                                    staterecyclerView.layoutManager =layoutmanager
                                }

                            }else{
                                Toast.makeText(context,"Unsuccessful Request!!",Toast.LENGTH_SHORT).show()
                            }

                        }catch (e:Exception){
                            Toast.makeText(context,"Context error!!",Toast.LENGTH_SHORT).show()
                        }
                    },Response.ErrorListener {
                Toast.makeText(context,"Server Down",Toast.LENGTH_SHORT).show()
            })
            queue1.add(jsonreq1)
        }
        return view
    }


}