package com.john_ap.verificatuinquilino

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref: SharedPreferences = getSharedPreferences(getString(R.string.PREFERENS), Context.MODE_PRIVATE)
        val userName = sharedPref.getString("NAME_USER", null)

        //El metodo Thread nos permite utilizar ilos este lo nos permite con el metodo sleep detenernos o detener nuestro porgrama en un punto espesifico por milisegundos.
        //Este puede generar error por lo que lo adecuado es colocarlo dentro de un try catch
        try {
            Thread.sleep(3000) //Para deternerlo durante 3 segundos
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        if (userName == null) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            super.finish()//para finalizar la actividad y no quede en segundo plano avierta por detras luego de abrir la otra actividad.
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            super.finish()//para finalizar la actividad y no quede en segundo plano avierta por detras luego de abrir la otra actividad.
        }
    }
}