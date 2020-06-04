package com.abhishek.covidtracer.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.abhishek.covidtracer.R
import com.abhishek.covidtracer.connectionamanager.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception


class SearchFragment : Fragment() {
    lateinit var searchbar:EditText
    lateinit var btnsearch:Button
    lateinit var txtcountry:TextView
    lateinit var scasesdata:TextView
    lateinit var stodayscases:TextView
    lateinit var sdeathsdata:TextView
    lateinit var srecoverdata:TextView
    lateinit var sactivedata:TextView
    lateinit var stodaysdeaths:TextView
    lateinit var sprogressbarLayout:RelativeLayout
    lateinit var sprogressbar:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_search, container, false)
        searchbar = view.findViewById(R.id.searchbar)
        btnsearch = view.findViewById(R.id.btnsearch)
        txtcountry = view.findViewById(R.id.txtcountry)
        scasesdata = view.findViewById(R.id.scasesdata)
        stodayscases = view.findViewById(R.id.stodayscases)
        sdeathsdata = view.findViewById(R.id.sdeathsdata)
        srecoverdata = view.findViewById(R.id.srecoverdata)
        sactivedata = view.findViewById(R.id.sactivedata)
        stodaysdeaths = view.findViewById(R.id.stodaysdeaths)
        sprogressbarLayout = view.findViewById(R.id.sprogressbarLayout)
        sprogressbar = view.findViewById(R.id.sprogressbar)
        sprogressbarLayout.visibility = View.VISIBLE
        btnsearch.setOnClickListener {
            val country = searchbar.text.toString()
            if(country==""){
                Toast.makeText(context,"Enter Country!!!",Toast.LENGTH_SHORT).show()
            }else{
                if(ConnectionManager().checkConnectivity(activity as Context)){

                    val que = Volley.newRequestQueue(context)
                    val url = "https://coronavirus-19-api.herokuapp.com/countries/${country}"
                    val jsonreq  = StringRequest(Request.Method.GET,url,
                            Response.Listener {
                                try{
                                    val jsonob = JSONObject(it.toString())
                                    sprogressbarLayout.visibility = View.GONE
                                    txtcountry.text = jsonob.getString("country")
                                    scasesdata.text = jsonob.getString("cases")
                                    stodayscases.text = jsonob.getString("todayCases")
                                    sdeathsdata.text = jsonob.getString("deaths")
                                    srecoverdata.text = jsonob.getString("recovered")
                                    sactivedata.text = jsonob.getString("active")
                                    stodaysdeaths.text = jsonob.getString("todayDeaths")

                                }catch (e:Exception){
                                    Toast.makeText(context,"Context Error!!",Toast.LENGTH_SHORT).show()
                                }

                            },Response.ErrorListener {
                        Toast.makeText(context,"Server Down!!",Toast.LENGTH_SHORT).show()
                    })
                    que.add(jsonreq)

                }else{
                    Toast.makeText(context,"No Internet Connection!!",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }
}