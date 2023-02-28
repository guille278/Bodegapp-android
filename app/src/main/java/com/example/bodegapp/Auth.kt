package com.example.bodegapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Auth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val intent = Intent(this, MainActivity::class.java)
        val preferences : SharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        if (preferences.contains("auth")){
            startActivity(intent)
            finish()
        }else{
        }

    }
}