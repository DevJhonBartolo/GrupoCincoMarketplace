package com.jhondevs.grupocincomarketplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
//import com.facebook.login.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.jhondevs.grupocincomarketplace.databinding.ActivityForgotPasswordBinding
import com.jhondevs.grupocincomarketplace.databinding.ActivityHomeBinding

class ForgotPasswordActivity : AppCompatActivity() {



    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth= FirebaseAuth.getInstance()

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //setContentView(R.layout.activity_forgot_password)

        binding.btnRecovery.setOnClickListener {

            val emailAddress = binding.editTextTextEmailAddress.text.toString()

            auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener{ task ->
                if (task.isSuccessful)
                {
                    val intent = Intent(this, LoginActivity::class.java)
                    this.startActivity(intent)
                    Toast.makeText(this,"Aca entro",Toast.LENGTH_SHORT).show()

                }
                else
                {
                    Toast.makeText(this,"Ingrese un email de una cuenta válida",Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}