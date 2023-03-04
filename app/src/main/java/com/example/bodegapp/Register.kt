package com.example.bodegapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bodegapp.databinding.ActivityMainBinding
import com.example.bodegapp.databinding.ActivityRegisterBinding
import com.example.bodegapp.databinding.FragmentLoginBinding
import org.json.JSONObject

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var datos: JSONObject = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)


        binding.registerAction.setOnClickListener(){
            val prueba = binding.nameR.editText?.text.toString()
            println("$prueba es menor o igual que 10")
        }

    }

}