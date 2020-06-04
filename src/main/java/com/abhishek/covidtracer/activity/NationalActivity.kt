package com.abhishek.covidtracer.activity


import android.os.Bundle
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.covidtracer.R
import com.abhishek.covidtracer.connectionamanager.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.lang.Exception

class NationalActivity : AppCompatActivity() {

    lateinit var txtnumber:TextView
    lateinit var txttollfree:TextView
    lateinit var txtadd:TextView
    lateinit var txttwitter:TextView
    lateinit var txtfb:TextView
    lateinit var txtmedia:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_national)
        txtnumber = findViewById(R.id.txtnumber)
        txttollfree = findViewById(R.id.txttollfree)
        txtadd = findViewById(R.id.txtadd)
        txttwitter = findViewById(R.id.txttwitter)
        txtfb = findViewById(R.id.txtfb)
        txtmedia = findViewById(R.id.txtmedia)
        val queue3 = Volley.newRequestQueue(this)
        val url = "https://api.rootnet.in/covid19-in/contacts"
        if(ConnectionManager().checkConnectivity(this)){
            val jsonre = JsonObjectRequest(Request.Method.GET,url,null,
                    Response.Listener {
                        try{
                            val success = it.getBoolean("success")
                            if(success){
                                val jsonobj = it.getJSONObject("data")
                                val newobj = jsonobj.getJSONObject("contacts")
                                val data = newobj.getJSONObject("primary")
                                txtnumber.text = data.getString("number")
                                txttollfree.text = data.getString("number-tollfree")
                                txtadd.text = data.getString("email")
                                txttwitter.text = data.getString("twitter")
                                txtfb.text  = data.getString("facebook")
                                txtmedia.text = "https://pib.gov.in/newsite/pmreleases.aspx?mincode=31"
                            }else{
                                Toast.makeText(this,"Fail to Request!!",Toast.LENGTH_SHORT).show()
                            }

                        }catch(e:Exception){
                            Toast.makeText(this,"Error in Context!!",Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener {
                Toast.makeText(this,"Enable to Fetch!!",Toast.LENGTH_SHORT).show()
            } )
            queue3.add(jsonre)
        }else{
            Toast.makeText(this,"No Internet Connection!!",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}