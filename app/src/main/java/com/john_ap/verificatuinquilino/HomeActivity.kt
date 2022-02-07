package com.john_ap.verificatuinquilino

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

        //val email= intent.extras?.getString("email")
        //val provider = intent.extras?.getString("provider")

    }

    private fun logOut(){
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }
}