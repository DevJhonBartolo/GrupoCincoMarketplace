package com.jhondevs.grupocincomarketplace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
//import com.facebook.login.LoginManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jhondevs.grupocincomarketplace.databinding.ActivityHomeBinding

enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}



class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth= FirebaseAuth.getInstance()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_content_home)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_car,R.id.nav_compras,R.id.nav_products, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.grupoayuda,R.id.nav_preguntas,R.id.nav_contacto,R.id.nav_terminos
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val bundle = intent.extras
        val email =bundle?.getString("email")
        val provider =bundle?.getString("provider")
        setup()

        navView.getHeaderView(0).findViewById<TextView>(R.id.emailm).text = email
        navView.getHeaderView(0).findViewById<TextView>(R.id.providerm).text=provider

        //Guardar Datos
        val prefs=getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()
    }

    private fun setup(){

        title="Inicio Productos"

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_singoff-> {
                onCerrarSesion();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun onCerrarSesion() {

        //Borrar Datos
        val prefs=getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        //val navView: NavigationView = binding.navView
        //if(navView.findViewById<TextView>(R.id.providerm).text == ProviderType.FACEBOOK.name){
        //    LoginManager.getInstance().logOut()
        //}

        auth.signOut()
        //onBackPressed()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

}