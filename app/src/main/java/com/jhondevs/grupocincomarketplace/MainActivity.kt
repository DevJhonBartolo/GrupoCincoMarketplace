package com.jhondevs.grupocincomarketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        title="BioSun"
        super.onCreate(savedInstanceState)
        //this.supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //val adapter = CustomAdapter()

        //recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_desengrasante ->Toast.makeText(applicationContext,"Clicked Me",Toast.LENGTH_LONG).show()
                R.id.nav_desinfectante ->Toast.makeText(applicationContext,"Clicked Me",Toast.LENGTH_LONG).show()
                R.id.nav_detergente ->Toast.makeText(applicationContext,"Clicked Me",Toast.LENGTH_LONG).show()
                R.id.nav_suavizante ->Toast.makeText(applicationContext,"Clicked Me",Toast.LENGTH_LONG).show()
                R.id.nav_gel ->Toast.makeText(applicationContext,"Clicked Me",Toast.LENGTH_LONG).show()
                R.id.nav_jabon ->Toast.makeText(applicationContext,"Clicked Me",Toast.LENGTH_LONG).show()
            }

            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =when(item.itemId) {
        R.id.action_search -> {
            Toast.makeText(this, R.string.txt_action_search,Toast.LENGTH_LONG).show()
            true
        }
        R.id.action_settings -> {
            Toast.makeText(this, R.string.txt_action_settings,Toast.LENGTH_LONG).show()
            true
        }
        R.id.action_logout -> {
            Toast.makeText(this, R.string.txt_action_logout,Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


}