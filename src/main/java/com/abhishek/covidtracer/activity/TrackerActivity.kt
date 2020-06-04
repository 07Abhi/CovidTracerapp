package com.abhishek.covidtracer.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.abhishek.covidtracer.R
import com.abhishek.covidtracer.fragments.*
import com.google.android.material.navigation.NavigationView

class TrackerActivity : AppCompatActivity() {
    lateinit var drawerlatyout: DrawerLayout
    lateinit var coordinatorlayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var framelayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigationlayout)
        drawerlatyout = findViewById(R.id.drawerlatyout)
        coordinatorlayout = findViewById(R.id.coordinatorlayout)
        toolbar = findViewById(R.id.toolbar)
        framelayout = findViewById(R.id.framelayout)
        navigationView = findViewById(R.id.navigationView)
        setupToolbar()
        openGlobalfrag()
        var actionbar = ActionBarDrawerToggle(
            this@TrackerActivity, drawerlatyout
            , R.string.opendrawer, R.string.closedrawer
        )
        drawerlatyout.addDrawerListener(actionbar)
        actionbar.syncState()
        navigationView.setNavigationItemSelectedListener {
            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            when (it.itemId) {
                R.id.global -> {
                    openGlobalfrag()
                    drawerlatyout.closeDrawers()
                }
                R.id.India -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.framelayout, IndiaFragment()).commit()
                    drawerlatyout.closeDrawers()
                    supportActionBar?.title = "India Index"
                }
                R.id.Others -> {
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout, SearchFragment())
                            .commit()
                    drawerlatyout.closeDrawers()
                    supportActionBar?.title = "Other Countries"
                }
                R.id.hospitals->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout,HospitalnBedFragment()).commit()
                    drawerlatyout.closeDrawers()
                    supportActionBar?.title = "Hospitals & Beds"
                }
                R.id.states->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout,StateFragment()).commit()
                    drawerlatyout.closeDrawers()
                    supportActionBar?.title="State Wise Tally"
                }
                R.id.helplines->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout,HelplinesFragments()).commit()
                    drawerlatyout.closeDrawers()
                    supportActionBar?.title= "Helplines"
                }
                R.id.exit -> {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Covid-19 Tracer")
                    dialog.setMessage("Want To Exit?")
                    dialog.setPositiveButton("Exit") { text, listener ->
                        finish()
                    }
                    dialog.setNegativeButton("No") { text, listener ->
                        supportFragmentManager.beginTransaction().replace(R.id.framelayout, GlobalFragment()).commit()
                    }
                    dialog.create()
                    dialog.show()
                }
                else->{

                }

            }
                return@setNavigationItemSelectedListener true
        }
    }
    fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tracer"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openGlobalfrag() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout, GlobalFragment()).commit()
        supportActionBar?.title = "Global Index"
        navigationView.setCheckedItem(R.id.global)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.framelayout)
        when (frag) {
            //DashboardFragment class
            !is GlobalFragment -> openGlobalfrag()
            else -> super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == android.R.id.home) {
            drawerlatyout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}