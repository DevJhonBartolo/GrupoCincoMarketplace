package com.jhondevs.grupocincomarketplace

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {

    private lateinit var emailTextView: TextView
    private lateinit var providerTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_home)

        //setup
        val bundle = intent.extras
        val  email=bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "",provider ?: "")

        //guardado de datos
        var prefs=getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()
    }

    private fun setup(email: String,provider: String){
        title="Inicio"
        emailTextView.text =email
        providerTextView.text = provider

        val logOutButton: Button = findViewById(R.id.logOutButton)
        logOutButton.setOnClickListener{

            //borrado de datos
            var prefs=getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}