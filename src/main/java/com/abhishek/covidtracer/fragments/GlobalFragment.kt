package com.abhishek.covidtracer.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.abhishek.covidtracer.R
import com.abhishek.covidtracer.connectionamanager.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_global.*
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception
import java.lang.StringBuilder


class GlobalFragment : Fragment() {
    lateinit var progressbarLayout:RelativeLayout
    lateinit var progressbar:ProgressBar
    lateinit var casesdata:TextView
    lateinit var todayscases:TextView
    lateinit var deathsdata:TextView
    lateinit var recoverdata:TextView
    lateinit var activedata:TextView
    lateinit var todaysdeaths:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_global, container, false)
        progressbarLayout = view.findViewById(R.id.progressbarLayout)
        progressbar = view.findViewById(R.id.progressbar)
        casesdata = view.findViewById(R.id.casesdata)
        todayscases = view.findViewById(R.id.todayscases)
        deathsdata = view.findViewById(R.id.deathsdata)
        recoverdata = view.findViewById(R.id.recoverdata)
        activedata = view.findViewById(R.id.activedata)
        todaysdeaths = view.findViewById(R.id.todaysdeaths)
        progressbarLayout.visibility = View.VISIBLE
        //Retrofit Builder

        val queue = Volley.newRequestQueue(context)
        val url = "https://corona.lmao.ninja/v2/all"
        if(ConnectionManager().checkConnectivity(activity as Context)){
            try {
                val jsonreq = StringRequest(Request.Method.GET, url
                        , Response.Listener {
                    val jsonobj = JSONObject(it.toString())
                    progressbarLayout.visibility = View.GONE
                    casesdata.text = jsonobj.getString("cases")
                    todayscases.text = jsonobj.getString("todayCases")
                    deathsdata.text = jsonobj.getString("deaths")
                    recoverdata.text = jsonobj.getString("recovered")
                    activedata.text = jsonobj.getString("active")
                    todaysdeaths.text = jsonobj.getString("todayDeaths")

                }, Response.ErrorListener {
                    Toast.makeText(context, "Error from Server!!", Toast.LENGTH_SHORT).show()
                })
                queue.add(jsonreq)
            }catch (e:Exception){
                Toast.makeText(context,"Error in Context!!",Toast.LENGTH_SHORT).show()
            }
        }else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("No-Connection")
            dialog.setMessage("Application Requires Internet")
            dialog.setPositiveButton("Open Settings"){
                text,listener->
                val openSetting = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(openSetting)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){
                text,listener->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }
        return view
    }
}