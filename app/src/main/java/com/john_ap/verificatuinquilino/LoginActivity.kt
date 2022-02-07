package com.john_ap.verificatuinquilino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //funcion para  lugiar al usuario

        login()
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