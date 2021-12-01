package com.jhondevs.grupocincomarketplace


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {

    private val GOOGLE_SING_IN = 100
    private val callbackManager = CallbackManager.Factory.create()

    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var progressBar: ProgressBar

    private lateinit var auth:FirebaseAuth

    private lateinit var tvRegister: TextView
    private lateinit var btnLogin: Button
    private lateinit var googleButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {

        title="Login"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edEmail=findViewById(R.id.edEmail)
        edPassword=findViewById(R.id.edPassword)

        progressBar=findViewById(R.id.progressBar)
        auth= FirebaseAuth.getInstance()
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
        googleButton = findViewById(R.id.googleButton)

        setup()
        session()

    }

    override fun onStart() {
        super.onStart()

        val prefs = getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email == null && provider == null) {
            var loginLayout = findViewById<LinearLayout>(R.id.loginLayout);
            loginLayout.visibility = View.VISIBLE
        }


    }
    
    private fun session()
    {
        //Guardar Datos
        val prefs=getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email= prefs.getString("email",null)
        val provider =prefs.getString("provider",null)
        
        if (email != null && provider != null){

            var loginLayout = findViewById<LinearLayout>(R.id.loginLayout);
            loginLayout.visibility = View.INVISIBLE

            showHome(email, ProviderType.valueOf(provider))

        }
    }

    private fun setup(){
        title="Login"

        btnLogin.setOnClickListener{
            if (edEmail.text.isNotEmpty() && edPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(edEmail.text.toString(),
                        edPassword.text.toString()).addOnCompleteListener{

                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?: "",ProviderType.BASIC)
                        }
                        else{
                            showAlert()
                        }
                    }
            }
        }

        tvRegister.setOnClickListener {
            if (edEmail.text.isNotEmpty() && edPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        edEmail.text.toString(),
                        edPassword.text.toString()
                    ).addOnCompleteListener {

                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)

                        } else {
                            showAlert()
                        }
                    }
            }
        }
        //Registro con google
        googleButton.setOnClickListener {

            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent,GOOGLE_SING_IN)
        }
    }

    private fun getToast(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_LONG
        ).show();
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha presentado un error al Autenticarse")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SING_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credencial = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credencial)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome(account.email ?: "", ProviderType.GOOGLE)
                            } else {
                                getToast("Erroe");
                            }
                        }
                }
            } catch (e: ApiException) {
                getToast("error");
            }


        }
    }

    fun forgotPassword(view: View){
        //startActivity(Intent(this,ForgotPasswordActivity::class.java))
        //startActivity(Intent(this,ListaProductosActivity::class.java))
        startActivity(Intent(this,HomeActivity::class.java))
    }

   /* fun register(view: View){
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

    }*/





}