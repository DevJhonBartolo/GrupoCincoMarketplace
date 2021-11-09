package com.jhondevs.grupocincomarketplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNombres:EditText
    private lateinit var txtApellidos:EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtPassword:EditText
    private lateinit var txtTelefono:EditText
    private lateinit var dbReference: DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtNombres=findViewById(R.id.txtNombres)
        txtApellidos=findViewById(R.id.txtApellidos)
        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPassword)
        txtTelefono=findViewById(R.id.txtTelefono)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference =database.reference.child("User")
    }


    fun register(view: View) {
        createNewAccount()

    }

    private fun createNewAccount(){
        val nombres:String=txtNombres.text.toString()
        val apellidos:String=txtApellidos.text.toString()
        val email:String=txtEmail.text.toString()
        val contrasena:String=txtPassword.text.toString()
        val telefono:String=txtTelefono.text.toString()

        if(!TextUtils.isEmpty(nombres) && !TextUtils.isEmpty(apellidos) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(contrasena) && !TextUtils.isEmpty(telefono)){

            auth.createUserWithEmailAndPassword(email,contrasena).addOnCompleteListener(this){
                task->

                if(task.isSuccessful) {
                    val user:FirebaseUser?=auth.currentUser
                    verifyEmail(user)

                    val userBD=dbReference.child(user?.uid.toString())

                    userBD.child("nombres").setValue(nombres)
                    userBD.child("apellidos").setValue(apellidos)
                    userBD.child("contrasena").setValue(contrasena)
                    userBD.child("telefono").setValue(telefono)
                    action()
                }
            }
        }
    }
    private fun action(){
        startActivity(Intent(this,LoginActivity::class.java))

    }
    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
            task ->

            if(task.isComplete)
            {
                Toast.makeText(this, "Email Enviado", Toast.LENGTH_LONG).show()

            }
            else
            {
                Toast.makeText(this, "Error al enviar el email", Toast.LENGTH_LONG).show()
            }

        }

    }
}