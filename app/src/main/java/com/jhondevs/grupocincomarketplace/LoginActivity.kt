package com.jhondevs.grupocincomarketplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        title="Login"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUser=findViewById(R.id.txtUser)
        txtPassword=findViewById(R.id.txtPassword)
        progressBar=findViewById(R.id.progressBar)
        auth= FirebaseAuth.getInstance()

    }
    fun forgotPassword(view: View){
        //startActivity(Intent(this,ForgotPasswordActivity::class.java))
        startActivity(Intent(this,ListaProductosActivity::class.java))

    }

    fun register(view: View){
        //startActivity(Intent(this,RegisterActivity::class.java))
        startActivity(Intent(this,MainActivity::class.java))

    }
    fun login(view: View){
        loginUser()

    }
    private fun loginUser(){
        val user:String=txtUser.text.toString()
        val password:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(this){
                task ->

                if(task.isSuccessful){
                    action()

                }
                else
                {
                    Toast.makeText(this, "Error en la Autenticación", Toast.LENGTH_LONG).show()
                }
            }

        }


    }
    private fun action(){
        startActivity(Intent(this,MainActivity::class.java))

    }
}