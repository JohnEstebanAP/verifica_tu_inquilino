package com.john_ap.verificatuinquilino

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val sharedPref: SharedPreferences = getSharedPreferences(getString(R.string.PREFERENS), Context.MODE_PRIVATE)
        val userName = sharedPref.getString("NAME_USER", null)
        val txtUserName: TextView = findViewById<TextView>(R.id.txtusername)

        if(userName != null){
            txtUserName.setText("Bienvenido: "+userName)
        }

    }
}