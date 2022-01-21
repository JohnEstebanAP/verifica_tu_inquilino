package com.john_ap.verificatuinquilino
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlinx.android.synthetic.main.activity_welcome.*

//activity de Bienvenidad
class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        //se recupera el nombre del usuario previamente logiado con sharedPreferences
        val sharedPref: SharedPreferences = getSharedPreferences(getString(R.string.PREFERENS), Context.MODE_PRIVATE)
        val userName = sharedPref.getString("NAME_USER", null)
        if(userName != null){
            //se le asigna el nombre del usario al textview de UserName
            txtusername.text = (getString(R.string.bienvenido2_welcome)+ userName)
        }

        //se Creo un hilo para que luego de 4 segundos se ejecute  el metodo o la funcion deopen home
          Handler(Looper.getMainLooper()).postDelayed({ openHome() }, 4000)
        }

    fun openHome(){
        //ejecta la siguiente activity
        startActivity(Intent(this, HomeActivity::class.java))
    }
}