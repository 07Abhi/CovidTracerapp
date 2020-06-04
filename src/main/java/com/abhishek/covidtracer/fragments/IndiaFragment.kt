package com.abhishek.covidtracer.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.abhishek.covidtracer.R
import com.abhishek.covidtracer.connectionamanager.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class IndiaFragment : Fragment() {
    lateinit var indiacases:TextView
    lateinit var indiatoday:TextView
    lateinit var indiadeaths:TextView
    lateinit var indiarecover:TextView
    lateinit var indiaactive:TextView
    lateinit var indiatdeaths:TextView
    lateinit var iprogressbarLayout:RelativeLayout
    lateinit var iprogressbar:ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_india, container, false)
        indiacases = view.findViewById(R.id.indiacases)
        indiatoday = view.findViewById(R.id.indiatoday)
        indiadeaths = view.findViewById(R.id.indiadeaths)
        indiarecover = view.findViewById(R.id.indiarecover)
        indiaactive = view.findViewById(R.id.indiaactive)
        indiatdeaths  = view.findViewById(R.id.indiatdeaths)
        iprogressbarLayout = view.findViewById(R.id.iprogressbarLayout)
        iprogressbar = view.findViewById(R.id.iprogressbar)
        iprogressbarLayout.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(context)
        val url = "https://coronavirus-19-api.herokuapp.com/countries/India"
        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonreq = StringRequest(Request.Method.GET,url
                    ,Response.Listener {
                try{
                    val jsonobj = JSONObject(it.toString())
                    iprogressbarLayout.visibility = View.GONE
                    indiacases.text = jsonobj.getString("cases")
                    indiatoday.text = jsonobj.getString("todayCases")
                    indiadeaths.text = jsonobj.getString("deaths")
                    indiarecover.text = jsonobj.getString("recovered")
                    indiaactive.text = jsonobj.getString("active")
                    indiatdeaths.text = jsonobj.getString("todayDeaths")

                }catch (e:Exception){
                    Toast.makeText(context,"Error in Context!!",Toast.LENGTH_SHORT).show()
                }
            },Response.ErrorListener {
                Toast.makeText(context,"Server Down!!",Toast.LENGTH_SHORT).show()
            })
            queue.add(jsonreq)

        }else{
            Toast.makeText(context,"No Internet Available!!!",Toast.LENGTH_SHORT).show()
        }
        return view
    }
}