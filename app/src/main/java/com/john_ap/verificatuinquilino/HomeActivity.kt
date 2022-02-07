package com.john_ap.verificatuinquilino

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val email= intent.extras?.getString("email")
        val provider = intent.extras?.getString("provider")

        //Guardado de datos
        val prefs : SharedPreferences.Editor = getSharedPreferences(getString(R.string.PREFERENS),Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    private fun logOut(){
        //serrar secion
        FirebaseAuth.getInstance().signOut()

        //Borrar datos del Usuario
        val prefs : SharedPreferences.Editor = getSharedPreferences(getString(R.string.PREFERENS),Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        //automaticamente presionamos el boton de regresar
        onBackPressed()


    }
}