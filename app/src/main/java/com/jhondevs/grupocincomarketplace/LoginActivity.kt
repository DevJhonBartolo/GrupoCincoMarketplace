package com.jhondevs.grupocincomarketplace


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

//enum class ProviderType{
//   BASIC
//}

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

        //guardado de datos
        //var prefs=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        //prefs.putString("email",email)
        //prefs.putString("provider",provider)
        //prefs.apply()

        //setup()

    }
    fun forgotPassword(view: View){
        //startActivity(Intent(this,ForgotPasswordActivity::class.java))
        //startActivity(Intent(this,ListaProductosActivity::class.java))
        startActivity(Intent(this,HomeActivity::class.java))

    }

    fun register(view: View){
        startActivity(Intent(this,RegisterActivity::class.java))


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
       //startActivity(Intent(this,MainActivity::class.java))
        startActivity(Intent(this,ListaProductosActivity::class.java))
        //startActivity(Intent(this,HomeActivity::class.java))

    }
    /*private fun setup(){
        title="Login"

        tvRegister.setOnClickListener{
            if (txtUser.text.isNotEmpty() && txtPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(txtUser.text.toString(),
                    txtPassword.text.toString()).addOnCompleteListener{

                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?: "",ProviderType.BASIC)

                        }
                        else{
                            showAlert()

                        }
                    }

            }


        }

        btnLogin.setOnClickListener {
            if (txtUser.text.isNotEmpty() && txtPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        txtUser.text.toString(),
                        txtPassword.text.toString()
                    ).addOnCompleteListener {

                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)

                        } else {
                            showAlert()

                        }
                    }

            }

        }

    }*/
    //private fun showAlert(){
   //     val builder = AlertDialog.Builder(this)
   //     builder.setTitle("Error")
    //    builder.setMessage("Se ha presentado un error al Autenticarse")
    //    builder.setPositiveButton("Aceptar",null)
   //     val dialog: AlertDialog = builder.create()
   //     dialog.show()
   // }
   // private fun showHome(email: String, provider: ProviderType){
   //     val homeIntent = Intent(this,MainActivity::class.java)
   //     startActivity(homeIntent)
  //  }
}