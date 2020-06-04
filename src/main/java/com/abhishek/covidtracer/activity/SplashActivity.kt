package com.abhishek.covidtracer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.abhishek.covidtracer.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashpage)
        Handler().postDelayed({
            var intent = Intent(this@SplashActivity, TrackerActivity::class.java)
            startActivity(intent)
        },1500)

    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}