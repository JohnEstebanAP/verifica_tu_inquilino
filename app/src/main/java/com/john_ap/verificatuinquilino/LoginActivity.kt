package com.john_ap.verificatuinquilino

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Se creo una instancia o un objeto, de la autenticacion de firebase
        var mAuth = FirebaseAuth.getInstance()

        //funcion para  logiar al usuario
        //login por medio del correo y la contraseña
        login()

        //login por medio del correo de google
        btn_google.setOnClickListener {
            loginGoogle()
        }
    }

    private fun login(){

        btn_entrar.setOnClickListener {
            if (TextUtils.isEmpty(txtUser.text)) {
                Toast.makeText(this, R.string.inputCorreo, Toast.LENGTH_SHORT).show()
                txtUser.requestFocus()
            }else if (TextUtils.isEmpty(txtPassword.text)) {
                Toast.makeText(this, R.string.input_contraseña, Toast.LENGTH_SHORT).show()
                txtPassword.requestFocus()
            }else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(txtUser.text.toString(), txtPassword.text.toString()).addOnCompleteListener{
                    //Comprobamos si la operacion se completo Correctamente
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }

    }

    private fun loginGoogle(){
        // Configuracion
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleClient = GoogleSignIn.getClient(this,googleConf)

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> {
                if(result.resultCode == Activity.RESULT_OK){
                    var data = result.data

                }
            }
        }

        resultLauncher.launch(googleClient.getSignInIntent())
    }

    private fun addUser(){

        btn_entrar.setOnClickListener {
            if (TextUtils.isEmpty(txtUser.text)) {
                Toast.makeText(this, R.string.inputCorreo, Toast.LENGTH_SHORT).show()
                txtUser.requestFocus()
            }else if (TextUtils.isEmpty(txtPassword.text)) {
                Toast.makeText(this, R.string.input_contraseña, Toast.LENGTH_SHORT).show()
                txtPassword.requestFocus()
            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtUser.text.toString(), txtPassword.text.toString()).addOnCompleteListener{
                    //Comprobamos si la operacion se completo Correctamente
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }


    private fun showHome(email: String, provider: ProviderType){

        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.putExtra("email", email)
        homeIntent.putExtra("provider", provider.name)
        startActivity(homeIntent)
        finishAffinity()
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}