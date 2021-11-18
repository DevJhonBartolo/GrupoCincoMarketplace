package com.jhondevs.grupocincomarketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        title="BioSun"
        super.onCreate(savedInstanceState)
        //this.supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))


        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //val adapter = CustomAdapter()

        //recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = adapter
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