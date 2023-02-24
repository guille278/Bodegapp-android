package com.example.bodegapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.bodegapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.myToolbar.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.tb_profile -> {
                    Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        binding.myNav.setOnItemSelectedListener {
            val navController = findNavController(R.id.cv_main)
            when (it.itemId) {
                R.id.menu_explorer -> {
                    navController.navigate(R.id.explorer)
                    true
                }
                R.id.menu_warehouse -> {
                    navController.navigate(R.id.warehouses)
                    true
                }
                else -> false
            }
        }
        val view = binding.root
        setContentView(view)
    }
}