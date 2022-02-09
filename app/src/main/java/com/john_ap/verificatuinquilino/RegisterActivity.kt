package com.john_ap.verificatuinquilino

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        finalizar.setOnClickListener {
            createUser()
        }
    }


    private fun createUser() {
        if (TextUtils.isEmpty(editTextNombreUsuario.text)) {
            Toast.makeText(
                this,
                "Por favor completar el campo del correo electronico",
                Toast.LENGTH_SHORT
            ).show()
            editTextNombreUsuario.requestFocus()
        } else if (TextUtils.isEmpty(editContraseña.text)) {
            Toast.makeText(
                this,
                "Por favor completar el campo de la contraseña",
                Toast.LENGTH_SHORT
            ).show()
            editContraseña.requestFocus()
        } else if (editContraseña.text.length < 8) {
            Toast.makeText(
                this,
                "La contraseña debe tener minimo 8 caracteres",
                Toast.LENGTH_SHORT
            ).show()
            editContraseña.requestFocus()
        } else if (TextUtils.isEmpty(editTextNombre.text)) {
            Toast.makeText(this, "Por favor completar el campo del nombre ", Toast.LENGTH_SHORT)
                .show()
            editTextNombre.requestFocus()
        } else if (TextUtils.isEmpty(editTextApellido.text)) {
            Toast.makeText(
                this,
                "por favor completar el campo del apellido",
                Toast.LENGTH_SHORT
            ).show()
            editTextApellido.requestFocus()

        } else if (!checkBox.isChecked()) {
            Toast.makeText(
                this,
                "Aceptar la opcion de términos y condiciones :)",
                Toast.LENGTH_SHORT
            ).show()
            checkBox.requestFocus()
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                editTextNombreUsuario.text.toString(),
                editContraseña.text.toString()
            ).addOnCompleteListener {
                //Comprobamos si la operacion se completo Correctamente
                if (it.isSuccessful) {
                    showAlertExito()
                    showHome(it.result?.user?.email ?: "")
                } else {
                    showAlert()
                }
            }
        }

    }

    private fun showHome(email: String) {

        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.putExtra("email", email)
        homeIntent.putExtra("provider", ProviderType.BASIC.name)
        startActivity(homeIntent)
        finishAffinity()
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.showalert_title))
        builder.setMessage(getString(R.string.showalert_message))
        builder.setPositiveButton(getString(R.string.showalert_positivebutton), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertExito() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("¡Felicidades!")
        alertDialog.setMessage("Registro exitoso")
        alertDialog.setPositiveButton("Aceptar", null)
        alertDialog.create().show()
    }
}