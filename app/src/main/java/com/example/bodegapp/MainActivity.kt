package com.example.bodegapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.findNavController
import com.example.bodegapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val intent = Intent(this, Auth::class.java)
        val preferences: SharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        if (!preferences.contains("auth")) {
            startActivity(intent)
            finish()
        }
        token = preferences.getString("auth", "")!!

        setSupportActionBar(binding.mainToolbar)

        binding.myNav.setOnItemSelectedListener {
            val navController = findNavController(R.id.cv_main)
            when (it.itemId) {
                R.id.menu_explorer -> {
                    navController.navigate(R.id.explorer)
                    true
                }
                R.id.menu_warehouse -> {
                    navController.navigate(R.id.warehouses2)
                    true
                }
                R.id.menu_Payments -> {
                    navController.navigate(R.id.billing)
                    true
                }
                else -> false
            }
        }


        val view = binding.root
        setContentView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.tb_profile -> {
                val intent = Intent(applicationContext, Profile::class.java)
                intent.putExtra("token", token)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}